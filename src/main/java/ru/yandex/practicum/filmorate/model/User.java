package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Информация о пользователе
 */
@Slf4j
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    /**
     * Идентификатор
     */
    int id;
    /**
     * Email
     */
    String email;
    /**
     * Логин
     */
    String login;
    /**
     * Имя
     * @Implementation: @Getter(AccessLevel.NONE) для отключения Lombok-генерации getName()
     */
    @Getter(AccessLevel.NONE)
    String name;
    /**
     * Дата рождения
     */
    LocalDate birthday;

    /**
     * @Implementation Согласно ТЗ: Если имя пользователя не указано, то в качестве имени используется логин
     */
    public String getName() {
        return name == null || name.isEmpty() ? login : name;
    }

    /**
     * Валидация данных пользователя
     * @throws ValidationException если данные некорректны
     */
    public void validate() {
        List<String> errorMessages = new ArrayList<>();

        if (email == null || email.isEmpty() || !email.contains("@")) {
            errorMessages.add("Электронная почта не может быть пустой и должна содержать символ @.");
        }

        if (login == null || login.isEmpty() || login.contains(" ")) {
            errorMessages.add("Логин не может быть пустым и содержать пробелы.");
        }

        if (birthday == null || birthday.isAfter(LocalDate.now())) {
            errorMessages.add("Дата рождения не может быть в будущем.");
        }

        if (!errorMessages.isEmpty()) {
            String message = String.join("\n", errorMessages);
            log.error("Ошибки валидации пользователя:\n{}", message);
            throw new ValidationException(message);
        }
    }
}