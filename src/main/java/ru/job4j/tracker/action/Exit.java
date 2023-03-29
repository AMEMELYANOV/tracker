package ru.job4j.tracker.action;

import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.store.Store;

/**
 * Класс реализация действия по выходу из приложения
 * @see ru.job4j.tracker.action.UserAction
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class Exit implements UserAction {

    /**
     * Возвращает наименование действия.
     *
     * @return наименование действия
     */
    @Override
    public String name() {
        return "Exit";
    }

    /**
     * Выполняет действие класса,
     * выход из приложения.
     *
     * @param input объект ввода
     * @param tracker объект работы с хранилищем данных
     * @return false
     */
    @Override
    public boolean execute(Input input, Store tracker) {
                System.out.println(System.lineSeparator() + "=== Exit Program ====");
                return false;
    }
}
