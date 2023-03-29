package ru.job4j.tracker.action;

import lombok.AllArgsConstructor;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

/**
 * Класс реализация действия по нахождению заявки по идентификатору
 * @see ru.job4j.tracker.action.UserAction
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
public class FindByIdAction implements UserAction {

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
        return "Find item by Id";
    }

    /**
     * Выполняет действие класса,
     * нахождение заявки по идентификатору.
     *
     * @param input объект ввода
     * @param tracker объект работы с хранилищем данных
     * @return true
     */
    @Override
    public boolean execute(Input input, Store tracker) {
        out.println(System.lineSeparator() + "=== Find Item by id ====");
        int id = input.askInt("Enter id: ");
        Item findItem = tracker.findById(id);
        if (findItem != null) {
            out.println(findItem);
        }  else {
            out.println("Item with this id not found");
        }
        return true;
    }
}
