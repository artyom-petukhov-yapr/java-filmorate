package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("User name");
        user.setLogin("UserLogin");
        user.setEmail("User@email");
        user.setBirthday(LocalDate.of(2000, 1, 1));
    }

    /**
     * Проверка, что если имя пользователя не указано, то в качестве имени вернется логин
     */
    @Test
    void getName() {
        user.setName(null);
        assertEquals(user.getLogin(), user.getName());
    }

    /**
     * Валидация корректных данных о пользователе не приводит к выбросу исключения
     */
    @Test
    void validate_validUser() {
        assertDoesNotThrow(() -> user.validate());
    }

    /**
     * Электронная почта не может быть пустой
     */
    @Test
    void validate_emptyEmail() {
        user.setEmail("");
        assertThrows(ValidationException.class, () -> user.validate());
    }

    /**
     * Электронная почта должна содержать символ @
     */
    @Test
    void validate_incorrectEmail() {
        user.setEmail("user.email");
        assertThrows(ValidationException.class, () -> user.validate());
    }

    /**
     * Логин не может быть пустым
     */
    @Test
    void validate_emptyLogin() {
        user.setLogin("");
        assertThrows(ValidationException.class, () -> user.validate());
    }

    /**
     * Логин не может содержать пробелы
     */
    @Test
    void validate_incorrectLogin() {
        user.setLogin("user login");
        assertThrows(ValidationException.class, () -> user.validate());
    }

    /**
     * Дата рождения не может быть в будущем
     */
    @Test
    void validate_birthday() {
        user.setBirthday(LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class, () -> user.validate());
    }
}