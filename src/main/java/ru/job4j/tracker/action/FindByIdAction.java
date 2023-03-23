package ru.job4j.tracker.action;

import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

public class FindByIdAction implements UserAction {
    private final Output out;

    public FindByIdAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Find item by Id";
    }

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
