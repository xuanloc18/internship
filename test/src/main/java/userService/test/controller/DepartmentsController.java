package userService.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import userService.test.dto.request.DepartmentsAddUser;
import userService.test.dto.request.DepartmentsCreate;
import userService.test.dto.response.APIResponse;
import userService.test.entity.Departments;
import userService.test.service.Departments_Service;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentsController {
    @Autowired
    Departments_Service departmentsService;


    @GetMapping
    public APIResponse<List<Departments>> getAll(){
        return APIResponse.<List<Departments>>builder()
                .result(departmentsService.departmentsGetAll())
                .build();
    }

    @PostMapping ("/create")
    APIResponse<Departments> createDepartments(@RequestBody DepartmentsCreate departments){
        return APIResponse.<Departments>builder()
                .result(departmentsService.departmentsCreate(departments))
                .build();
    }

    @PostMapping ("/adduser")
    APIResponse<Departments> adduser(@RequestBody DepartmentsAddUser addUser){
        return APIResponse.<Departments>builder()
                .result(departmentsService.departmentsAddUser(addUser))
                .build();
    }


}
