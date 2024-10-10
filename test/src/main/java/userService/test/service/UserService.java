package userService.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import userService.test.dto.request.UserRequestCreate;
import userService.test.dto.request.UserRequestUpdate;
import userService.test.dto.response.UserResponse;
import userService.test.entity.User;
import userService.test.mapper.UserMapper;
import userService.test.respository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository repository;
    public UserResponse CreUser(UserRequestCreate requestCreate){
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        User user=userMapper.toUser(requestCreate);
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        repository.save(user);
        return userMapper.toUserResponse(user);
    }
    public UserResponse findUser(Long id){
        User user=repository.findById(id).orElseThrow(()->new RuntimeException("user not exit"));
        return userMapper.toUserResponse(user);
    }
    public List<UserResponse> getAll(){
        List<User> userList;
        userList=repository.findAll();
        List<UserResponse> responseList=userList.stream().map(user -> {
            return userMapper.toUserResponse(user);
        }).toList();

        return responseList;
    }
    public UserResponse updateUser (Long id, UserRequestUpdate userRequestUpdate){
        User user=repository.findById(id).orElseThrow(()->new RuntimeException("user not exit"));
        userMapper.updateUser(user,userRequestUpdate);
        repository.save(user);
        return userMapper.toUserResponse(user);
    }
    public void  deleteUser(Long id){
        repository.deleteById(id);
    }





}
