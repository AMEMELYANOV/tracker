package ru.job4j.tracker.action;

import lombok.AllArgsConstructor;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

/**
 * Класс реализация действия по созданию заявки
 * @see ru.job4j.tracker.action.UserAction
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
public class CreateAction implements UserAction {

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
        return "Create";
    }

    /**
     * Выполняет действие класса,
     * создание заявки.
     *
     * @param input объект ввода
     * @param tracker объект работы с хранилищем данных
     * @return true
     */
    @Override
    public boolean execute(Input input, Store tracker) {
        out.println(System.lineSeparator() + "=== Create a new Item ====");
        String name = input.askStr("Enter name: ");
        String description = input.askStr("Enter description: ");
        Item item = new Item(name, description);
        tracker.add(item);
        return true;
    }
}