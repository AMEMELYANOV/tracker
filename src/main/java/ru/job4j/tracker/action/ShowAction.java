package ru.job4j.tracker.action;

import lombok.AllArgsConstructor;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

import java.util.List;

/**
 * Класс реализация действия по отображению заявки
 * @see ru.job4j.tracker.action.UserAction
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
public class ShowAction implements UserAction {

    /**
     * Объект вывода данных
     */
    private final Output out;

    /**
     * Возвращает наименование действия.
     *
     * @return наименование действия
     */
    @Override
    public String name() {
        return "Show all items";
    }

    /**
     * Выполняет действие класса,
     * отображению заявки.
     *
     * @param input объект ввода
     * @param tracker объект работы с хранилищем данных
     * @return true
     */
    @Override
    public boolean execute(Input input, Store tracker) {
        out.println(System.lineSeparator() + "=== Show Items ====");
        List<Item> currentItems = tracker.findAll();
        currentItems.forEach(System.out::println);
        return true;
    }
}
