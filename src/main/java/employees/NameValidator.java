package employees;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class NameValidator implements ConstraintValidator<Name, String> {

    private int maxLength;

    @Override
    public void initialize(Name constraintAnnotation) {
        maxLength = constraintAnnotation.maxLength();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null &&
                !value.isBlank() &&
                value.length() > 2 &&
                value.length() <= maxLength &&
                Character.isUpperCase(value.charAt(0));
    }
}
