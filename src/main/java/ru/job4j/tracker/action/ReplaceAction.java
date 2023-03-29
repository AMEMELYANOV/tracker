package ru.job4j.tracker.action;

import lombok.AllArgsConstructor;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

/**
 * Класс реализация действия по редактированию заявки
 * @see ru.job4j.tracker.action.UserAction
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
public class ReplaceAction implements UserAction {

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
        return "Edit item";
    }

    /**
     * Выполняет действие класса,
     * редактирование заявки.
     *
     * @param input объект ввода
     * @param tracker объект работы с хранилищем данных
     * @return true
     */
    @Override
    public boolean execute(Input input, Store tracker) {
        out.println(System.lineSeparator() + "=== Edit Item ====");
        int id = input.askInt("Enter id: ");
        String name = input.askStr("Enter name: ");
        String description = input.askStr("Enter description: ");
        Item editItem = new Item(name, description);
        if (tracker.replace(id, editItem)) {
            out.println("Edit completed");
        } else {
            out.println("Edit failed, id does not exist");
        }
        return true;
    }
}
