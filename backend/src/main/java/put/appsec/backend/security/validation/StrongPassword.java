package put.appsec.backend.security.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {

    String message() default "Password is too weak (Minimum strength: Medium)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}