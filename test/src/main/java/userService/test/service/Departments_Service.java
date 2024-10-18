package userService.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.test.dto.request.DepartmentsAddUser;
import userService.test.dto.request.DepartmentsCreate;
import userService.test.entity.Departments;
import userService.test.entity.User;
import userService.test.mapper.DepartmentsMapper;
import userService.test.respository.DepartmentsRepository;
import userService.test.respository.UserRespository;

import java.util.HashSet;
import java.util.List;

@Service
public class Departments_Service {
    @Autowired
    DepartmentsRepository departmentsRepository;
    @Autowired
    DepartmentsMapper departmentsMapper;
    @Autowired
    UserRespository userRepository;
    public Departments departmentsCreate(DepartmentsCreate departmentsCreate){
        if(departmentsRepository.findByDepartmentsname(departmentsCreate.getDepartments_name()).isPresent()){
            throw new RuntimeException("departments was created you can create");
        }
        return  departmentsRepository.save(departmentsMapper.toDepartments(departmentsCreate));
    }

    public List<Departments> departmentsGetAll(){
        return departmentsRepository.findAll();
    }

    public Departments departmentsAddUser(DepartmentsAddUser departmentsAddUser){

        if(departmentsRepository.findByDepartmentsname(departmentsAddUser.getDepartments_name()).isEmpty()){
            throw new RuntimeException("departments not exit");
        }
        Departments departments=departmentsRepository.findByDepartmentsname(departmentsAddUser.getDepartments_name()).orElseThrow();
       List<User> users= userRepository.findAllById(departmentsAddUser.getUser_id());

       HashSet<User> hashSet =new HashSet<>(users);
       departments.setUsers(hashSet);
        return  departmentsRepository.save(departments);
    }

}
