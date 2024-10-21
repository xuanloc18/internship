package userService.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import userService.test.dto.request.LeaveRequestCreate;
import userService.test.dto.request.LeaveRequestUpdate;
import userService.test.dto.response.APIResponse;
import userService.test.entity.Attendance;
import userService.test.entity.AttendanceSalary;
import userService.test.entity.LeaveRequest;
import userService.test.service.AttendanceService;
import userService.test.service.LeaveRequestService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/leaverequest")
public class LeaveRequestController {
    @Autowired
    LeaveRequestService leaveRequestService;

    @PostMapping("/create")
    APIResponse<LeaveRequest> create(@RequestBody LeaveRequestCreate leaveRequestCreate){
        return APIResponse.<LeaveRequest>builder()
                .result(leaveRequestService.creLeaveRequest(leaveRequestCreate))
                .build();
    }
    @PutMapping("/update/{id}")
    APIResponse<LeaveRequest> update(@PathVariable Long id,@RequestBody LeaveRequestUpdate leaveRequestUpdate){
        return APIResponse.<LeaveRequest>builder()
                .result(leaveRequestService.updateLeaveRequest(id,leaveRequestUpdate))
                .build();
    }
    @GetMapping("/get")
    APIResponse<List<LeaveRequest>> getAll(){
        return APIResponse.<List<LeaveRequest>>builder()
                .result(leaveRequestService.getAll())
                .build();
    }
    @GetMapping("/getAllofMe")
    APIResponse<List<LeaveRequest>> getAllofMe(){
        return APIResponse.<List<LeaveRequest>>builder()
                .result(leaveRequestService.getAllofMe())
                .build();
    }

}





