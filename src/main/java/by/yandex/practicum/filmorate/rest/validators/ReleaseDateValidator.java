package by.yandex.practicum.filmorate.rest.validators;

import by.yandex.practicum.filmorate.rest.validators.constraints.ReleaseDateConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateConstraint, LocalDate> {
    private final static LocalDate MIN_LOCAL_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext ctx) {
        return localDate != null && localDate.isAfter(MIN_LOCAL_DATE);
    }
}
