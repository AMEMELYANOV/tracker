package ru.job4j.tracker.action;

import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.store.Store;

/**
 * Интерфейс действия по работе с заявками
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface UserAction {

    /**
     * Возвращает наименование действия.
     *
     * @return наименование действия
     */
    String name();

    /**
     * Выполняет действие класса,
     * создание заявки.
     *
     * @param input объект ввода
     * @param tracker объект работы с хранилищем данных
     * @return true или false  в зависимости от действия
     */
    boolean execute(Input input, Store tracker);
}