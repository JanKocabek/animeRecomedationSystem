package cz.kocabek.animerecomedationsystem.security.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {PasswordValidator.class
        }
)
public @interface Password {
    String message() default "Invalid password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int min() default 8;
    int max() default 20;
    boolean mustHaveUpperCase() default true;
    boolean mustHaveLowerCase() default true;
    boolean mustHaveDigit() default true;
    boolean mustHaveSpecialChar() default true;
}
