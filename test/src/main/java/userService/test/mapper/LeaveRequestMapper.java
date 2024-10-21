package userService.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import userService.test.dto.request.LeaveRequestCreate;
import userService.test.dto.request.LeaveRequestUpdate;
import userService.test.entity.LeaveRequest;

@Mapper(componentModel = "spring")
public interface LeaveRequestMapper {
    LeaveRequest toLeaveRequest(LeaveRequestCreate leaveRequestCreate);
    LeaveRequest updateLeaveRequest(@MappingTarget LeaveRequest leaveRequest, LeaveRequestUpdate leaveRequestUpdate);
}
