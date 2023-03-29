package ru.job4j.tracker.action;

import lombok.AllArgsConstructor;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

import java.util.List;

/**
 * Класс реализация действия по нахождению заявки по наименованию
 * @see ru.job4j.tracker.action.UserAction
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
public class FindByNameAction implements UserAction {

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
        return "Find items by name";
    }

    /**
     * Выполняет действие класса,
     * нахождение заявки по наименованию заявки.
     *
     * @param input объект ввода
     * @param tracker объект работы с хранилищем данных
     * @return true
     */
    @Override
    public boolean execute(Input input, Store tracker) {
        out.println(System.lineSeparator() + "=== Find Item by name ====");
        String name = input.askStr("Enter name: ");
        List<Item> arrItems = tracker.findByName(name);
        if (arrItems.size() != 0) {
            for (Item arrItem : arrItems) {
                out.println(arrItem);
            }
        } else {
            out.println("No items with this name were found");
        }
        return true;
    }
}
