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
        if(shiftRepository.findShiftByName(shiftCreateRequest.getShiftName()).isPresent()){
            throw new RuntimeException("shift was created");
        }
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
        if(userShiftRepository.getUserShiftToCheck(shiftCreateRequest.getUserID(),shiftCreateRequest.getShiftID(),shiftCreateRequest.getShift_date()).isPresent()){
            throw new RuntimeException("user had shift that day");
        }
       return userShiftRepository.save(userShiftMapper.toUserShift(shiftCreateRequest));
    }
    public List<UserShift> list(){
        return userShiftRepository.findAll();
    }
    public List<UserShift> listShiftOfUser(Long userID){
        return userShiftRepository.getUserShiftOfUser(userID);
    }


}