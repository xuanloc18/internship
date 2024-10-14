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

import java.math.BigDecimal;
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
    @PutMapping("/update/{id}")
    APIResponse<Attendance> update(@PathVariable Long id){
        return APIResponse.<Attendance>builder()
                .result(attendance.update(id))
                .build();
    }
    @GetMapping("/get/{id}")
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
    @GetMapping("/salaryofmonth")
        APIResponse<AttendanceSalary> salaryofmonth(@RequestParam("id") Long id,@RequestParam("month") int month,@RequestParam("years") int years,@RequestParam("valueSalary") Long valueSalary ){
            return APIResponse.<AttendanceSalary>builder()
                    .result(attendance.creAttendanceSalary(id,month,years,valueSalary))
                    .build();
        }
        @GetMapping("/get-salaryofmonth")
    APIResponse<List<AttendanceSalary>> getAllByMonth(@RequestParam("month") int month,@RequestParam("year") int year){
        return APIResponse.<List<AttendanceSalary>>builder()
                .result(attendance.salaryList(month, year))
                .build();
    }
    @GetMapping("/get-sumsalaryofmonth")
    APIResponse<BigDecimal> sumsalaryofmonth(@RequestParam("month") int month, @RequestParam("year") int year){
        return APIResponse.<BigDecimal>builder()
                .result(attendance.sumsalary(month, year))
                .build();
    }
    }





