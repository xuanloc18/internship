package userService.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import userService.test.dto.request.ShiftCreateRequest;
import userService.test.dto.request.ShiftUpdateRequest;
import userService.test.entity.Shift;

@Mapper(componentModel = "spring")
public interface ShiftMapper {
    Shift toShift(ShiftCreateRequest shiftCreateRequest);
    Shift toShiftUpdate(@MappingTarget Shift shift, ShiftUpdateRequest shiftUpdateRequest);
}
