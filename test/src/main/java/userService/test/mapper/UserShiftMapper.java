package userService.test.mapper;

import org.mapstruct.Mapper;
import userService.test.dto.request.UserShiftCreateRequest;
import userService.test.entity.UserShift;

@Mapper(componentModel = "spring")
public interface UserShiftMapper {
    UserShift toUserShift(UserShiftCreateRequest shiftCreateRequest);
}
