package com.cxl.identity_service.validator;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD}) // chỉ valid biến
@Retention(RetentionPolicy.RUNTIME) // ano được xử lý lúc nào
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
    String message() default "{valid date of birth}";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
