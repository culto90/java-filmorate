package by.yandex.practicum.filmorate;

import by.yandex.practicum.filmorate.rest.dto.FilmDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FilmDtoTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testFilmSuccess() {
        FilmDto filmDto = new FilmDto();
        filmDto.setId(1);
        filmDto.setName("name");
        filmDto.setDescription("desc");
        filmDto.setRate(4);
        filmDto.setDuration(100);
        filmDto.setReleaseDate(LocalDate.of(2000,1, 1));
        Set<ConstraintViolation<FilmDto>> violations = validator.validate(filmDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void nameIsNotNull() {
        FilmDto filmDto = new FilmDto();
        filmDto.setId(1);
        filmDto.setName("");
        filmDto.setDuration(100);
        filmDto.setReleaseDate(LocalDate.of(2000,1, 1));
        Set<ConstraintViolation<FilmDto>> violations = validator.validate(filmDto);
        for(ConstraintViolation violation : violations) {
            assertEquals(violation.getMessage(), "Name cannot be empty.");
            break;
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    public void descMoreThan200() {
        FilmDto filmDto = new FilmDto();
        filmDto.setId(1);
        filmDto.setName("Name");
        filmDto.setDescription("hdsgadhgfashdgfahsgdfahgsfdashgdasdasdadasdsadasdasdasdasdasdasdasdasd" +
                "asdasdasdashdfgaghsdhjagsdkhjadkhafghjsfjdshgfgahsdjahgsdjhasdasdsasdasda" +
                "dasdasdasdasdasddasdsadasdhgfdhsdggfsjdhfsdkjfhshjfvgjhasvdhjaksfcjdsjfhvgvcsjh,kc");
        filmDto.setDuration(100);
        filmDto.setReleaseDate(LocalDate.of(2000,1, 1));
        Set<ConstraintViolation<FilmDto>> violations = validator.validate(filmDto);
        for(ConstraintViolation violation : violations) {
            assertEquals(violation.getMessage(), "Description cannot be greater than 200 symbols.");
            break;
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    public void releaseDateIsAfter1895_12_28() {
        FilmDto filmDto = new FilmDto();
        filmDto.setId(1);
        filmDto.setName("Name");
        filmDto.setDuration(100);
        filmDto.setReleaseDate(LocalDate.of(1800,1, 1));
        Set<ConstraintViolation<FilmDto>> violations = validator.validate(filmDto);
        for(ConstraintViolation violation : violations) {
            assertEquals(violation.getMessage(), "Release date must be after 1895-12-28.");
            break;
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    public void isPositiveDuration() {
        FilmDto filmDto = new FilmDto();
        filmDto.setId(1);
        filmDto.setName("Name");
        filmDto.setDuration(-100);
        filmDto.setReleaseDate(LocalDate.of(2800,1, 1));
        Set<ConstraintViolation<FilmDto>> violations = validator.validate(filmDto);
        for(ConstraintViolation violation : violations) {
            assertEquals(violation.getMessage(), "Duration must be positive.");
            break;
        }
        assertFalse(violations.isEmpty());
    }

}
