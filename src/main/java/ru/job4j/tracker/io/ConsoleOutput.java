package ru.job4j.tracker.io;

/**
 * Класс реализация вывода данных в консоль
 * @see ru.job4j.tracker.io.Output
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class ConsoleOutput implements Output {

    /**
     * Получает и выводит объект в консоль.
     *
     * @param obj объект для вывода
     */
    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }
}