package userService.test.dto.request;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import userService.test.entity.User;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DepartmentsCreate {
    String departments_name;
    String departments_description;

}
