package dev.carv.bank.commons.annotation.validator;

import dev.carv.bank.commons.annotation.ValueEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class ValueEnumValidator implements ConstraintValidator<ValueEnum, String> {

    private Set<String> allowedValues;

    @Override
    public void initialize(ValueEnum annotation) {
        allowedValues = Stream.of(annotation.clazz().getEnumConstants())
            .map(Enum::name)
            .collect(toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return allowedValues.contains(value);
    }

}
