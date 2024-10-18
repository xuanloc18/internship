package userService.test.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DepartmentsAddUser {
    String departments_name;
    Set<String> user_id;
}
