package put.appsec.backend.security.validation;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordStrengthValidator implements ConstraintValidator<StrongPassword, String> {
    private static final int MIN_SCORE = 2;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isEmpty()) {
            return true;
        }

        Zxcvbn zxcvbn = new Zxcvbn();
        Strength strength = zxcvbn.measure(password);

        if (strength.getScore() < MIN_SCORE) {

            context.disableDefaultConstraintViolation();
            String feedback = strength.getFeedback().getWarning();
            if (feedback == null || feedback.isEmpty()) {
                feedback = "Password is too weak.";
            }

            context.buildConstraintViolationWithTemplate(feedback)
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}