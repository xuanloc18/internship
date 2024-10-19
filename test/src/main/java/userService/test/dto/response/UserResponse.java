package userService.test.dto.response;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import userService.test.entity.Role;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String userID;
    String userName;
    String userPhone;
    String userMail;
    LocalDate dbo;
    Set<RoleResponse> roles;


}
