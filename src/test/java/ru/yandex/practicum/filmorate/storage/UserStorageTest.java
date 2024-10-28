package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.FilmDbStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmDbStorage.class})
class UserStorageTest {
    private final UserStorage userStorage;

    /**
     * Создание пользователя с пустым email - произойдет исключение
     */
    @Test
    void createEmptyEmail() {
        User user = createValidUser();
        user.setEmail(null);

        assertThrows(ValidationException.class, () -> userStorage.add(user));
    }

    /**
     * Создание пользователя с некорректным email - произойдет исключение
     */
    @Test
    void createInvalidEmail() {
        User user = createValidUser();
        user.setEmail("user.email");

        assertThrows(ValidationException.class, () -> userStorage.add(user));
    }

    /**
     * Создание пользователя с пустым логином - произойдет исключение
     */
    @Test
    void createEmptyLogin() {
        User user = createValidUser();
        user.setLogin(null);

        assertThrows(ValidationException.class, () -> userStorage.add(user));
    }

    /**
     * Создание пользователя с некорректным логином - произойдет исключение
     */
    @Test
    void createInvalidLogin() {
        User user = new User();
        // Логин не может содержать пробелы
        user.setLogin("test Login");

        assertThrows(ValidationException.class, () -> userStorage.add(user));
    }

    /**
     * Создание пользователя с датой рождения в будущем - произойдет исключение
     */
    @Test
    void createFutureBirthday() {
        User user = createValidUser();
        user.setBirthday(LocalDate.now().plusDays(1));

        assertThrows(ValidationException.class, () -> userStorage.add(user));
    }

    /**
     * Обновление пользователя - возвращает обновленного пользователя
     */
    @Test
    void updateUser() {
        User user = createValidUser();
        userStorage.add(user);

        String newName = user.getName() + "new";
        user.setName(newName);

        User updatedUser = userStorage.update(user);

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
        assertThrows(ValidationException.class, () -> userStorage.update(user));
    }

    /**
     * Обновление пользователя с некорректным email - произойдет исключение
     */
    @Test
    void updateInvalidEmail() {
        User user = createValidUser();
        user.setId(1);
        user.setEmail("user.email");
        assertThrows(ValidationException.class, () -> userStorage.update(user));
    }

    /**
     * Обновление пользователя с пустым логином - произойдет исключение
     */
    @Test
    void updateEmptyLogin() {
        User user = createValidUser();
        user.setId(1);
        user.setLogin(null);
        assertThrows(ValidationException.class, () -> userStorage.update(user));
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
        assertThrows(ValidationException.class, () -> userStorage.update(user));
    }

    /**
     * Обновление пользователя с датой рождения в будущем - произойдет исключение
     */
    @Test
    void updateFutureBirthday() {
        User user = createValidUser();
        user.setId(1);

        user.setBirthday(LocalDate.now().plusDays(1));

        assertThrows(ValidationException.class, () -> userStorage.update(user));
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