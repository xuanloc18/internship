package com.cxl.identity_service.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cxl.identity_service.dto.request.AuthenticationRequest;
import com.cxl.identity_service.dto.request.IntrospectRequest;
import com.cxl.identity_service.dto.request.LogoutRequest;
import com.cxl.identity_service.dto.request.RefreshRequest;
import com.cxl.identity_service.dto.response.AuthenticationResponse;
import com.cxl.identity_service.dto.response.IntrospectResponse;
import com.cxl.identity_service.entity.InvalidateToken;
import com.cxl.identity_service.entity.User;
import com.cxl.identity_service.exception.AppException;
import com.cxl.identity_service.exception.ErrorCode;
import com.cxl.identity_service.respository.InvalidateTokenRepository;
import com.cxl.identity_service.respository.RoleRepository;
import com.cxl.identity_service.respository.UserRespository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor // là một annotation trong Lombok tự động tạo constructor cho các trường (fields) có giá trị là
// final hoặc được đánh dấu là @NonNull
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    UserRespository userRespository;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected Long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected Long REFESHABLE_DURATION;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SINGER_KEY;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InvalidateTokenRepository invalidateTokenRepository;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean valid = true;
        try {
            verifyToken(token, false);
        } catch (AppException appException) {
            valid = false;
        }
        return IntrospectResponse.builder().valid(valid).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        User user = userRespository
                .findByUserName(authenticationRequest.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authentication = passwordEncoder.matches(authenticationRequest.getPassWord(), user.getPassWord());
        if (!authentication) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generrateToken(authenticationRequest.getUserName());
        return AuthenticationResponse.builder()
                .token(token)
                .authentication(true)
                .build();
    }

    private String generrateToken(String username) {
        User user = (userRespository.findByUserName(username)).orElseThrow(() -> new RuntimeException());
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScrope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SINGER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("can not create coken", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScrope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" "); // các phần tử cách nhau bới " "
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add(role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                }
            });
        }
        return stringJoiner.toString();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SINGER_KEY.getBytes());
        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFESHABLE_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli()) // thời gian cho phép refresh
                : signedJWT.getJWTClaimsSet().getExpirationTime(); // thời gian valid

        boolean veri = signedJWT.verify(verifier);
        if (!(veri && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(
                    request.getToken(), true); // phải check thời gian refresh bới nếu hết thời gian expirationTime
            // người dùng logout sẽ báo lỗi và token không được lưu còn refresh token lại được
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiry = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidateToken invalidateToken =
                    InvalidateToken.builder().id(jit).expiryTime(expiry).build();
            invalidateTokenRepository.save(invalidateToken);
        } catch (AppException exception) {
            log.warn("token already expired");
        }
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signJWT = verifyToken(request.getToken(), true);
        var jit = signJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();
        InvalidateToken invalidateToken =
                InvalidateToken.builder().id(jit).expiryTime(expiryTime).build();
        invalidateTokenRepository.save(invalidateToken);

        var username = signJWT.getJWTClaimsSet().getSubject();
        var token = generrateToken(username);

        return AuthenticationResponse.builder()
                .token(token)
                .authentication(true)
                .build();
    }
}
