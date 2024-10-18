package userService.test.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) // chỉ valid biến
@Retention(RetentionPolicy.RUNTIME) // ano được xử lý lúc nào
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
    String message() default "{valid date of birth}";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
