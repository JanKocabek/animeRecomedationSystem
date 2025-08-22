package cz.kocabek.animerecomedationsystem.security.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    int min;
    int max;
    boolean specialChars;
    boolean numbers;
    boolean upperCase;
    boolean lowerCase;
    private final String specialCharsString = "!@#$%*?_+,.";
    private final String NUMBER_MSG = "Password must contain digits";
    private final String LOWER_MSG = "Password must contain lower case letters";
    private final String UPPER_MSG = "Password must contain upper case letters";
    private final String SPECIAL_MSG = "Password must contain at least one of these special characters \" %s \"".formatted(specialCharsString);
    private String LENGTH_MSG;

    @Override
    public void initialize(Password password) {
        this.min = password.min();
        this.max = password.max();
        this.specialChars = password.mustHaveSpecialChar();
        this.lowerCase = password.mustHaveLowerCase();
        this.upperCase = password.mustHaveUpperCase();
        this.numbers = password.mustHaveDigit();
        LENGTH_MSG = "Password length must be between %d and %d characters".formatted(min, max);
    }


    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        var valid = true;
        context.disableDefaultConstraintViolation();
        if (s == null || s.isBlank()) {
            addViolation(context, LENGTH_MSG);
            if (lowerCase) addViolation(context, LOWER_MSG);
            if (upperCase) addViolation(context, UPPER_MSG);
            if (numbers) addViolation(context, NUMBER_MSG);
            if (specialChars) addViolation(context, SPECIAL_MSG);
            return false;
        }
        final var hasLower = s.matches(".*[a-z].*");
        final var hasUpper = s.matches(".*[A-Z].*");
        final var hasDigit = s.matches(".*[0-9].*");
        final var hasSpecial = s.matches(".*[" + specialCharsString + "].*");

        if (s.length() < min || s.length() > max) {
            addViolation(context, LENGTH_MSG);
            valid = false;
        }
        if (lowerCase && !hasLower) {
            addViolation(context, LOWER_MSG);
            valid = false;
        }
        if (upperCase && !hasUpper) {
            addViolation(context, UPPER_MSG);
            valid = false;
        }
        if (numbers && !hasDigit) {
            addViolation(context, NUMBER_MSG);
            valid = false;
        }
        if (specialChars && !hasSpecial) {
            addViolation(context, SPECIAL_MSG);
            valid = false;
        }
        return valid;
    }

    private void addViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
