package com.bws.userservice.rest.validator.annotations;

import com.bws.userservice.rest.validator.UniqueSellernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueSellernameValidator.class})
public @interface UniqueSeller {
    String message() default "";

    Class<?> [] groups() default {};

    Class<? extends Payload> [] payload() default {};
}
