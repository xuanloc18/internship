package userService.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import userService.test.dto.request.UserRequestCreate;
import userService.test.dto.request.UserRequestUpdate;
import userService.test.dto.response.APIResponse;
import userService.test.dto.response.UserResponse;
import userService.test.entity.User;
import userService.test.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    APIResponse<List<UserResponse>> getAll(){
        return APIResponse.<List<UserResponse>>builder()
                .result(userService.getAll())
                .build();
    }

    @PostMapping("/create")
    APIResponse<UserResponse> createUser(@RequestBody UserRequestCreate userRequestCreate){
        return APIResponse.<UserResponse>builder()
                .message("create user successfull")
                .result(userService.CreUser(userRequestCreate))
                .build();
    }
    @GetMapping("/getUser")
    APIResponse<UserResponse> getUser(@PathVariable Long userID){
        return  APIResponse.<UserResponse>builder()
                .message("user was find")
                .result(userService.findUser(userID))
                .build();
    }
    @PutMapping("/update/{userID}")
    APIResponse<UserResponse> updateUser(@PathVariable Long userID, @RequestBody UserRequestUpdate userRequestUpdate){
        return  APIResponse.<UserResponse>builder()
                .message("update successfull")
                .result(userService.updateUser(userID,userRequestUpdate))
                .build();
    }
    @DeleteMapping("/delete/{userID}")
    APIResponse<String> dele(@PathVariable Long userID){
        userService.deleteUser(userID);
        return APIResponse.<String>builder()
                .result("delete successfull")
                .build();
    }





}
