package userService.test.mapper;

import org.mapstruct.Mapper;
import userService.test.dto.request.DepartmentsAddUser;
import userService.test.dto.request.DepartmentsCreate;
import userService.test.entity.Departments;

@Mapper(componentModel = "spring")
public interface DepartmentsMapper {
    Departments toDepartments(DepartmentsCreate departmentsCreate);
    Departments toDepartmentsAddUser(DepartmentsAddUser departmentsAddUser);
}
