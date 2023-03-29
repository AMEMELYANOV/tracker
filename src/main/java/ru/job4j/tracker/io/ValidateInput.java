package ru.job4j.tracker.io;

import lombok.AllArgsConstructor;

/**
 * Класс реализация валидации ввода
 * @see ru.job4j.tracker.io.Input
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
public class ValidateInput implements Input {

    /**
     * Объект вывода данных
     */
    private final Output out;

    /**
     * Объект ввода данных
     */
    private final Input in;

    /**
     * Выполняет вывод вопроса выбора действия.
     *
     * @param question вопрос
     * @return ответ
     */
    @Override
    public String askStr(String question) {
        return in.askStr(question);
    }

    /**
     * Получает и валидирует данные ввода.
     *
     * @param question вопрос с выбором действия
     * @return числовое представление действия
     */
    @Override
    public int askInt(String question) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = in.askInt(question);
                invalid = false;
            } catch (NumberFormatException nfe) {
                out.println("Please enter validate data again.");
            }
        } while (invalid);
        return value;
    }
}