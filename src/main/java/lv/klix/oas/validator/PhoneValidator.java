package lv.klix.oas.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private List<Pattern> acceptedPatterns;

    @Override
    public void initialize(Phone constraintAnnotation) {
        acceptedPatterns = Arrays.stream(constraintAnnotation.value())
                .map(p -> Pattern.compile(p.getRegex()))
                .toList();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.isBlank()) return true;

        return acceptedPatterns.stream()
                .anyMatch(pattern -> pattern.matcher(value).matches());
    }
}
