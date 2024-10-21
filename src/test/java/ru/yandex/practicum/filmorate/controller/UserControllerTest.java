package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
        userController.createUser(createValidUser());
    }

    /**
     * Изначальное количество пользователей = 1
     */
    @Test
    void getAllUsers() {
        assertEquals(1, userController.getAllUsers().size());
    }

    /**
     * Изначально должен быть один пользователь с идентификатором 1
     */
    @Test
    void oneUserHasIdOne() {
        assertEquals(1, userController.getAllUsers().iterator().next().getId());
    }

    /**
     * Создание пользователя с пустым email - произойдет исключение
     */
    @Test
    void createEmptyEmail() {
        User user = createValidUser();
        user.setEmail(null);

        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    /**
     * Создание пользователя с некорректным email - произойдет исключение
     */
    @Test
    void createInvalidEmail() {
        User user = createValidUser();
        user.setEmail("user.email");

        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    /**
     * Создание пользователя с повторяющимся логином - произойдет исключение
     */
    @Test
    void createDuplicateLogin() {
        User user = createValidUser();
        // чтобы не пересекся email с существующим пользователем
        user.setEmail(user.getEmail() + "2");
        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    /**
     * Создание пользователя с повторяющимся email - произойдет исключение
     */
    @Test
    void createDuplicateEmail() {
        User user = createValidUser();
        // чтобы не пересекся логин с существующим пользователем
        user.setLogin(user.getLogin() + "2");
        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    /**
     * Создание пользователя с пустым логином - произойдет исключение
     */
    @Test
    void testCreateUserWithEmptyLogin() {
        User user = createValidUser();
        user.setLogin(null);

        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    /**
     * Создание пользователя с некорректным логином - произойдет исключение
     */
    @Test
    void createInvalidLogin() {
        User user = new User();
        // Логин не может содержать пробелы
        user.setLogin("test Login");

        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    /**
     * Создание пользователя с датой рождения в будущем - произойдет исключение
     */
    @Test
    void createFutureBirthday() {
        User user = createValidUser();
        user.setBirthday(LocalDate.now().plusDays(1));

        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    /**
     * Обновление пользователя - возвращает обновленного пользователя
     */
    @Test
    void updateUser() {
        User user = createValidUser();
        user.setId(1);
        String newName = user.getName() + "new";
        user.setName(newName);

        User updatedUser = userController.updateUser(user);

        assertEquals(newName, updatedUser.getName());
    }

    /**
     * Обновление пользователя с пустым email - произойдет исключение
     */
    @Test
    void updateEmptyEmail() {
        User user = createValidUser();
        user.setId(1);
        user.setEmail(null);
        assertThrows(ValidationException.class, () -> userController.updateUser(user));
    }

    /**
     * Обновление пользователя с некорректным email - произойдет исключение
     */
    @Test
    void updateInvalidEmail() {
        User user = createValidUser();
        user.setId(1);
        user.setEmail("user.email");
        assertThrows(ValidationException.class, () -> userController.updateUser(user));
    }

    /**
     * Обновление пользователя с пустым логином - произойдет исключение
     */
    @Test
    void updateEmptyLogin() {
        User user = createValidUser();
        user.setId(1);
        user.setLogin(null);
        assertThrows(ValidationException.class, () -> userController.updateUser(user));
    }

    /**
     * Обновление пользователя с некорректным логином - произойдет исключение
     */
    @Test
    void updateInvalidLogin() {
        User user = createValidUser();
        user.setId(1);
        // Логин не может содержать пробелы
        user.setLogin("user login");
        assertThrows(ValidationException.class, () -> userController.updateUser(user));
    }

    /**
     * Обновление пользователя с датой рождения в будущем - произойдет исключение
     */
    @Test
    void updateFutureBirthday() {
        User user = createValidUser();
        user.setId(1);

        user.setBirthday(LocalDate.now().plusDays(1));

        assertThrows(ValidationException.class, () -> userController.updateUser(user));
    }

    /**
     * Вспомогательный метод для создания валидного пользователя
     */
    private static User createValidUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("testLogin");
        user.setName("testName");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        return user;
    }
}