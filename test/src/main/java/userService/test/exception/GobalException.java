package userService.test.exception;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import userService.test.dto.response.APIResponse;

@Slf4j
@ControllerAdvice
public class GobalException {
    @ExceptionHandler(value= GoogleJsonResponseException.class)
    ResponseEntity<APIResponse> handlingGoogleJsonResponseException(GoogleJsonResponseException e){
    APIResponse<String> apiResponse=new APIResponse<>();
    apiResponse.setCode(ErrorCode.ENTITYNOTFOUND.getCode());
    apiResponse.setMessage(ErrorCode.ENTITYNOTFOUND.getMessage());
    return ResponseEntity.badRequest().body(apiResponse);

    }
    @ExceptionHandler(value=RuntimeException.class)
    ResponseEntity<APIResponse> handlingRuntimeExcepion(RuntimeException exception){
        String message=exception.getMessage().toString();
        APIResponse<String> apiResponse=new APIResponse<>();
        apiResponse.setMessage(message);
        return ResponseEntity.badRequest().body(apiResponse);


    }

}
