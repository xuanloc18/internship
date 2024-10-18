package userService.test.controller;


import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import userService.test.dto.request.AuthenticationRequest;
import userService.test.dto.request.IntrospectRequest;
import userService.test.dto.request.LogoutRequest;
import userService.test.dto.request.RefreshRequest;
import userService.test.dto.response.APIResponse;
import userService.test.dto.response.AuthenticationResponse;
import userService.test.dto.response.IntrospectResponse;
import userService.test.service.AuthenticationService;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    APIResponse<AuthenticationResponse> authenticationResponseAPIResponse(@RequestBody AuthenticationRequest request) {

        var result = authenticationService.authenticate(request);

        return APIResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/introspect")
    APIResponse<IntrospectResponse> authenticationResponseAPIResponse(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {

        var result = authenticationService.introspect(request);

        return APIResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    APIResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {

        authenticationService.logout(request);

        return APIResponse.<Void>builder().build();
    }

    @PostMapping("/refresh")
    APIResponse<AuthenticationResponse> logout(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {

        return APIResponse.<AuthenticationResponse>builder()
                .result(authenticationService.refreshToken(request))
                .build();
    }
}
