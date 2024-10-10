package userService.test.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    ENTITYNOTFOUND(1001,"entity not found", HttpStatus.BAD_REQUEST);




    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

     ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
