package userService.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.test.dto.request.ShiftCreateRequest;
import userService.test.dto.request.ShiftUpdateRequest;
import userService.test.dto.request.UserShiftCreateRequest;
import userService.test.entity.Shift;
import userService.test.entity.UserShift;
import userService.test.mapper.ShiftMapper;
import userService.test.mapper.UserShiftMapper;
import userService.test.respository.ShiftRepository;
import userService.test.respository.UserShiftRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class ShiftService {
    @Autowired
    ShiftRepository shiftRepository;
    @Autowired
    ShiftMapper shiftMapper;
    @Autowired
    UserShiftRepository userShiftRepository;
    @Autowired
    UserShiftMapper userShiftMapper;

    public Shift createShift( ShiftCreateRequest shiftCreateRequest){
    Shift shift=shiftMapper.toShift(shiftCreateRequest);
        return shiftRepository.save(shift);
    }
    public List<Shift> getAllShift(){
        return shiftRepository.findAll();
    }
    public Shift updaShift(ShiftUpdateRequest shiftUpdateRequest){
        Shift shift=shiftRepository.findShiftByName(shiftUpdateRequest.getShiftName()).orElseThrow(()->new RuntimeException("shift not exit"));
        return shiftRepository.save(shiftMapper.toShiftUpdate(shift,shiftUpdateRequest));
    }
    public UserShift creUserShift(UserShiftCreateRequest shiftCreateRequest){
       return userShiftRepository.save(userShiftMapper.toUserShift(shiftCreateRequest));
    }
    public List<UserShift> list(){
        return userShiftRepository.findAll();
    }


}