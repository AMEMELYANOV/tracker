package ru.job4j.tracker.io;

import java.util.Scanner;

/**
 * Класс реализация ввода данных с консоли
 * @see ru.job4j.tracker.io.Input
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class ConsoleInput implements Input {

    /**
     * Объект ввода данных
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Выполняет вывод вопроса выбора действия в консоль.
     *
     * @param question вопрос
     * @return ответ
     */
    @Override
    public String askStr(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    /**
     * Считывает из консоли числовой ответ пользователя,
     * содержащий выбор действия.
     *
     * @param question вопрос с выбором действия
     * @return числовое представление действия
     */
    @Override
    public int askInt(String question) {
        return Integer.parseInt(askStr(question));
    }
}