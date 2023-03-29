package ru.job4j.tracker.io;

/**
 * Интерфейс ввода данных по работе с заявками
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface Input {

    /**
     * Выполняет вывод вопроса выбора действия.
     *
     * @param question вопрос
     * @return ответ
     */
    String askStr(String question);

    /**
     * Считывает числовой ответ пользователя,
     * содержащий выбор действия.
     *
     * @param question вопрос с выбором действия
     * @return числовое представление действия
     */
    int askInt(String question);
}