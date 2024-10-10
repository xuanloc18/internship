package userService.test.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestUpdate {
    String userName;
    String userPhone;
    String userMail;
    String userAuthor;
    String passWord;
}
