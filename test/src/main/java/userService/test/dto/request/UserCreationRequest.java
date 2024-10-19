package userService.test.dto.request;


import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import userService.test.validator.DobConstraint;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    String userName;
    String userPhone;
    String userMail;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dbo;
    @Size(min = 8, message = "PASSWORD_EXCEPION")
    String passWord;



}
