package userService.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import userService.test.dto.request.LeaveRequestCreate;
import userService.test.dto.request.LeaveRequestUpdate;
import userService.test.entity.LeaveRequest;
import userService.test.entity.User;
import userService.test.exception.AppException;
import userService.test.exception.ErrorCode;
import userService.test.mapper.LeaveRequestMapper;
import userService.test.respository.LeaveRequestRepository;
import userService.test.respository.UserRespository;

import java.util.List;

@Service
public class LeaveRequestService {
    @Autowired
    LeaveRequestMapper leaveRequestMapper;
    @Autowired
    LeaveRequestRepository leaveRequestRepository;
    @Autowired
    UserRespository userRespository;

    public LeaveRequest creLeaveRequest(LeaveRequestCreate leaveRequestCreate){
        var content= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRespository.findByUserName(content).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));

        LeaveRequest leaveRequest=leaveRequestMapper.toLeaveRequest(leaveRequestCreate);
        leaveRequest.setUser(user);

        return leaveRequestRepository.save(leaveRequest);
    }
    public  LeaveRequest updateLeaveRequest(Long id,LeaveRequestUpdate leaveRequestUpdate){
        LeaveRequest leaveRequest=leaveRequestRepository.findById(id).orElseThrow(()->new RuntimeException("LeaveRequest not exits"));
        leaveRequest=leaveRequestMapper.updateLeaveRequest(leaveRequest,leaveRequestUpdate);
        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getAll(){
        return leaveRequestRepository.findAll();
    }
    public List<LeaveRequest> getAllofMe(){
        var name=SecurityContextHolder.getContext().getAuthentication().getName();
        return leaveRequestRepository.finAllOfMy(name);
    }


}
