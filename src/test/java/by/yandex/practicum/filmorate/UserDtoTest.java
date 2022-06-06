package by.yandex.practicum.filmorate;

import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.rest.converters.UserDtoToUserConverter;
import by.yandex.practicum.filmorate.rest.dto.UserDto;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class UserDtoTest {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUserSuccess() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setEmail("as@as.by");
        userDto.setLogin("asda12231");
        userDto.setName("sdadad");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void incorrectUserEmail() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setEmail("as@as@.by");
        userDto.setLogin("asda12231");
        userDto.setName("sdadad");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        for(ConstraintViolation violation : violations) {
            assertEquals(violation.getMessage(), "Email is not valid.");
            break;
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    public void nullUserEmail() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setEmail("");
        userDto.setLogin("asda12231");
        userDto.setName("sdadad");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        for(ConstraintViolation violation : violations) {
            assertEquals(violation.getMessage(), "Email is not valid.");
            break;
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    public void incorrectUserLogin() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setEmail("as@as.by");
        userDto.setLogin("asda1   2231");
        userDto.setName("sdadad");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        for(ConstraintViolation violation : violations) {
            assertEquals(violation.getMessage(), "Login must contain only characters and numbers.");
            break;
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    public void nullUserLogin() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setEmail("as@as.by");
        userDto.setLogin("");
        userDto.setName("sdadad");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        for(ConstraintViolation violation : violations) {
            assertEquals(violation.getMessage(), "Login must not be blank.");
            break;
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    public void nullUserName() {
        //todo bad test
        UserDto userDto = new UserDto();
        userDto.setLogin("login");
        userDto.setName("");
        User user = new UserDtoToUserConverter().convert(userDto);
        assertEquals(user.getName(), userDto.getLogin());
    }

    @Test
    public void futureBirthDate() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setEmail("as@as.by");
        userDto.setLogin("sdadad");
        userDto.setName("sdadad");
        userDto.setBirthday(LocalDate.of(2049, 1, 1));
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        for(ConstraintViolation violation : violations) {
            assertEquals(violation.getMessage(), "Birthday must be a past date.");
            break;
        }
        assertFalse(violations.isEmpty());
    }

}
