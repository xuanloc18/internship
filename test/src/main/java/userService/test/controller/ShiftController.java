package userService.test.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import userService.test.dto.request.ShiftCreateRequest;
import userService.test.dto.request.ShiftUpdateRequest;
import userService.test.dto.request.UserShiftCreateRequest;
import userService.test.dto.response.APIResponse;
import userService.test.entity.Shift;
import userService.test.entity.UserShift;
import userService.test.service.ShiftService;

import java.util.List;

@RestController
@RequestMapping("/shift")
public class ShiftController {
    @Autowired
    ShiftService shiftService;
    @PostMapping("/create-shift")
    public APIResponse<Shift> createShift(@RequestBody ShiftCreateRequest shiftCreateRequest){
        return APIResponse.<Shift>builder()
                .result(shiftService.createShift(shiftCreateRequest))
                .build();
    }
    @PostMapping("/update-shift")
    public APIResponse<Shift> createShift(@RequestBody ShiftUpdateRequest shiftUpdateRequest){
        return APIResponse.<Shift>builder()
                .result(shiftService.updaShift(shiftUpdateRequest))
                .build();
    }
    @GetMapping
    public APIResponse<List<Shift>> getShift(){
        return APIResponse.<List<Shift>>builder()
                .result(shiftService.getAllShift())
                .build();
    }
    @PostMapping("/create-usershift")
    public APIResponse<UserShift> createusershift(@RequestBody UserShiftCreateRequest request){
        return APIResponse.<UserShift>builder()
                .result(shiftService.creUserShift(request))
                .build();
    }
    @GetMapping("/all")
    public APIResponse<List<UserShift>> getUserShift(){
        return APIResponse.<List<UserShift>>builder()
                .result(shiftService.list())
                .build();
    }

}
