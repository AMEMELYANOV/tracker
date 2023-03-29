package ru.job4j.tracker.io;

/**
 * Интерфейс вывода данных по работе с заявками
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface Output {

    /**
     * Получает и выводит объект в соответствии
     * с реализацией.
     *
     * @param obj объект для вывода
     */
    void println(Object obj);
}