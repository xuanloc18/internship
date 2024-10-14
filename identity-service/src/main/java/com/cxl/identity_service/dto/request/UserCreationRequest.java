package com.cxl.identity_service.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import com.cxl.identity_service.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    String userName;

    @Size(min = 8, message = "PASSWORD_EXCEPION")
    String passWord;

    String firstName;
    String lastName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dbo;
}
