package ru.job4j.tracker.action;

import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

public class ReplaceAction implements UserAction {
    private final Output out;

    public ReplaceAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Edit item";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        out.println(System.lineSeparator() + "=== Edit Item ====");
        int id = input.askInt("Enter id: ");
        String name = input.askStr("Enter name: ");
        Item editItem = new Item(name);
        if (tracker.replace(id, editItem)) {
            out.println("Edit completed");
        } else {
            out.println("Edit failed, id does not exist");
        }
        return true;
    }
}
