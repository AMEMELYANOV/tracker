package ru.job4j.tracker.action;

import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.store.Store;

public class Exit implements UserAction {

    @Override
    public String name() {
        return "Exit";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
                System.out.println(System.lineSeparator() + "=== Exit Program ====");
                return false;
    }
}
