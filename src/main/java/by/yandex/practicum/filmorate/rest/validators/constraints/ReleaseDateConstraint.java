package by.yandex.practicum.filmorate.rest.validators.constraints;

import by.yandex.practicum.filmorate.rest.validators.ReleaseDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ReleaseDateValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ReleaseDateConstraint {
    String message() default "Release date must be after 1895-12-28.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
