package cz.kocabek.animerecomedationsystem.security.validation;

import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    private int min;
    private int max;
    private boolean specialChars;
    private boolean numbers;
    private boolean upperCase;
    private boolean lowerCase;
    private static final String SPECIAL_CHARS_STRING = "!@#$%*?_+,.";
    private static final String NUMBER_MSG = "Password must contain digits";
    private static final String LOWER_MSG = "Password must contain lower case letters";
    private static final String UPPER_MSG = "Password must contain upper case letters";
    private static final String SPECIAL_MSG = "Password must contain at least one of these special characters \" %s \"".formatted(SPECIAL_CHARS_STRING);
    private static final Pattern P_IS_LOVER = Pattern.compile(".*[a-z].*");
    private static final Pattern PAT_IS_UPPER = Pattern.compile(".*[A-Z].*");
    private static final Pattern PAT_IS_NUMBER = Pattern.compile(".*\\d.*");
    private static final Pattern PAT_IS_SPECIAL = Pattern.compile(".*[" + SPECIAL_CHARS_STRING + "].*");
    private String passLenghtMsg;

    @Override
    public void initialize(@Nonnull Password password) {
        this.min = password.min();
        this.max = password.max();
        this.specialChars = password.mustHaveSpecialChar();
        this.lowerCase = password.mustHaveLowerCase();
        this.upperCase = password.mustHaveUpperCase();
        this.numbers = password.mustHaveDigit();
        passLenghtMsg = "Password length must be between %d and %d characters".formatted(min, max);
    }


    @Override
    public boolean isValid(String input, @Nonnull ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (input == null || input.isBlank()) {
            addViolation(context, passLenghtMsg);
            return false;
        }
        return hasRequirementConstrain(input, context);
    }

    private boolean hasRequirementConstrain(@Nonnull String input, ConstraintValidatorContext context) {
        final boolean isLover = P_IS_LOVER.matcher(input).matches();
        boolean isUpper = PAT_IS_UPPER.matcher(input).matches();
        boolean isNumber = PAT_IS_NUMBER.matcher(input).matches();
        boolean isSpecial = PAT_IS_SPECIAL.matcher(input).matches();
        boolean valid = true;
        if (input.length() < min || input.length() > max) {
            addViolation(context, passLenghtMsg);
            valid = false;
        }
        if (lowerCase && !isLover) {
            addViolation(context, LOWER_MSG);
            valid = false;
        }
        if (upperCase && !isUpper) {
            addViolation(context, UPPER_MSG);
            valid = false;
        }
        if (numbers && !isNumber) {
            addViolation(context, NUMBER_MSG);
            valid = false;
        }
        if (specialChars && !isSpecial) {
            addViolation(context, SPECIAL_MSG);
            valid = false;
        }
        return valid;
    }

    private void addViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
