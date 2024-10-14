package com.cxl.identity_service.dto.response;

import java.time.LocalDate;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String id;
    String userName;
    String firstName;
    String lastName;
    LocalDate dbo;
    Set<RoleResponse> roles;
}
