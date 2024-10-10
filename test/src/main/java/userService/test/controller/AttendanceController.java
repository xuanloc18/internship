package userService.test.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import userService.test.dto.response.APIResponse;
import userService.test.entity.Attendance;
import userService.test.entity.AttendanceSalary;
import userService.test.respository.UserRepository;
import userService.test.service.AttendanceService;
import userService.test.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    AttendanceService attendance;

    @PostMapping("/create/{id}")
    APIResponse<Attendance> create(@PathVariable Long id){
        return APIResponse.<Attendance>builder()
                .result(attendance.createattendance(id))
                .build();
    }
    @PostMapping("/update/{id}")
    APIResponse<Attendance> update(@PathVariable Long id){
        return APIResponse.<Attendance>builder()
                .result(attendance.update(id))
                .build();
    }
    @PostMapping("/get/{id}")
    APIResponse<List<Attendance>> getByUserID(@PathVariable Long id){
        return APIResponse.<List<Attendance>>builder()
                .result(attendance.getbyuserID(id))
                .build();
    }
    @GetMapping("/get")
    APIResponse<List<Attendance>> getAll(){
        return APIResponse.<List<Attendance>>builder()
                .result(attendance.getall())
                .build();
    }
    @PostMapping("/salaryofmonth")
        APIResponse<AttendanceSalary> salaryofmonth(@RequestParam("id") Long id,@RequestParam("month") int month,@RequestParam("years") int years,@RequestParam("valueSalary") Long valueSalary ){
            return APIResponse.<AttendanceSalary>builder()
                    .result(attendance.creAttendanceSalary(id,month,years,valueSalary))
                    .build();
        }
    }





