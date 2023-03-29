package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.tracker.action.*;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;
import ru.job4j.tracker.io.StubInput;
import ru.job4j.tracker.io.StubOutput;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.MemoryStore;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест класс работы класса StartUI
 * @see ru.job4j.tracker.StartUI
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class StartUITest {

    /**
     * Объект доступа к хранилищу
     */
    private MemoryStore tracker;

    /**
     * Заявка
     */
    private Item item;

    /**
     * Заглушка для объекта вывода
     */
    private Output out;

    /**
     * Список действий
     */
    private List<UserAction> actions;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @Before
    public void setUp() {
        tracker = new MemoryStore();
        item = new Item("name", "description");
        out = new StubOutput();
        actions = new ArrayList<>();
    }

    /**
     * Очищает хранилище данных после каждого теста.
     */
    @After
    public void wipeStore() {
        tracker.clear();
    }

    /**
     * Выполняем проверку выполнения действия - Create.
     */
    @Test
    public void addActionSuccess() {
        Input in = new StubInput(
                new String[] {"0", "name", "description", "1"}
        );
        actions.add(new CreateAction(out));
        actions.add(new Exit());
        new StartUI(out).init(in, tracker, actions);

        assertThat(out.toString(), is(
                "Menu." + System.lineSeparator()
                        + "0. Create" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
                        + System.lineSeparator() + "=== Create a new Item ===="
                        + System.lineSeparator()
                        + "Menu." + System.lineSeparator()
                        + "0. Create" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
        ));
    }

    /**
     * Выполняем проверку выполнения действия - Show all items.
     */
    @Test
    public void findAllActionSuccess() {
        Input in = new StubInput(
                new String[] {"0", "1"}
        );
        actions.add(new ShowAction(out));
        actions.add(new Exit());
        new StartUI(out).init(in, tracker, actions);

        assertThat(out.toString(), is(
                "Menu." + System.lineSeparator()
                        + "0. Show all items" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
                        + System.lineSeparator() + "=== Show Items ====" + System.lineSeparator()
                        + "Menu." + System.lineSeparator()
                        + "0. Show all items" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
        ));
    }

    /**
     * Выполняем проверку выполнения действия - Edit item,
     * если выполнение успешно.
     */
    @Test
    public void editActionSuccess() {
        item = tracker.add(item);
        String id = String.valueOf(item.getId());
        Input in = new StubInput(
                new String[] {"0", id, "name1", "description1", "1"}
        );
        actions.add(new ReplaceAction(out));
        actions.add(new Exit());
        new StartUI(out).init(in, tracker, actions);

        assertThat(out.toString(), is(
                "Menu." + System.lineSeparator()
                        + "0. Edit item" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
                        + System.lineSeparator() + "=== Edit Item ====" + System.lineSeparator()
                        + "Edit completed" + System.lineSeparator()
                        + "Menu." + System.lineSeparator()
                        + "0. Edit item" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
        ));
    }

    /**
     * Выполняем проверку выполнения действия - Edit item,
     * если выполнение неуспешно.
     */
    @Test
    public void editActionFail() {
        item = tracker.add(item);
        String id = String.valueOf(item.getId());
        Input in = new StubInput(
                new String[] {"0", id + 1, "name1", "description1", "1"}
        );
        actions.add(new ReplaceAction(out));
        actions.add(new Exit());
        new StartUI(out).init(in, tracker, actions);

        assertThat(out.toString(), is(
                "Menu." + System.lineSeparator()
                        + "0. Edit item" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
                        + System.lineSeparator() + "=== Edit Item ====" + System.lineSeparator()
                        + "Edit failed, id does not exist" + System.lineSeparator()
                        + "Menu." + System.lineSeparator()
                        + "0. Edit item" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
        ));
    }

    /**
     * Выполняем проверку выполнения действия - Delete item,
     * если выполнение успешно.
     */
    @Test
    public void deleteActionSuccess() {
        item = tracker.add(item);
        String id = String.valueOf(item.getId());
        Input in = new StubInput(
                new String[] {"0", id, "1"}
        );
        actions.add(new DeleteAction(out));
        actions.add(new Exit());
        new StartUI(out).init(in, tracker, actions);

        assertThat(out.toString(), is(
                "Menu." + System.lineSeparator()
                        + "0. Delete item" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
                        + System.lineSeparator() + "=== Delete Item ====" + System.lineSeparator()
                        + "Item deleted" + System.lineSeparator()
                        + "Menu." + System.lineSeparator()
                        + "0. Delete item" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
        ));
    }

    /**
     * Выполняем проверку выполнения действия - Delete item,
     * если выполнение неуспешно.
     */
    @Test
    public void deleteActionFail() {
        item = tracker.add(item);
        String id = String.valueOf(item.getId());
        Input in = new StubInput(
                new String[] {"0", id + 1, "1"}
        );
        actions.add(new DeleteAction(out));
        actions.add(new Exit());
        new StartUI(out).init(in, tracker, actions);

        assertThat(out.toString(), is(
                "Menu." + System.lineSeparator()
                        + "0. Delete item" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
                        + System.lineSeparator() + "=== Delete Item ====" + System.lineSeparator()
                        + "Delete failed, id does not exist" + System.lineSeparator()
                        + "Menu." + System.lineSeparator()
                        + "0. Delete item" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
        ));
    }

    /**
     * Выполняем проверку выполнения действия - Find item by Id,
     * если выполнение успешно.
     */
    @Test
    public void findByIdActionSuccess() {
        int id = tracker.add(item).getId();
        item.setId(id);
        Input in = new StubInput(
                new String[] {"0", String.valueOf(id), "1"}
        );
        actions.add(new FindByIdAction(out));
        actions.add(new Exit());
        new StartUI(out).init(in, tracker, actions);

        assertThat(out.toString(), is(
                "Menu." + System.lineSeparator()
                        + "0. Find item by Id" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
                        + System.lineSeparator() + "=== Find Item by id ===="
                        + System.lineSeparator()
                        + item + System.lineSeparator()
                        + "Menu." + System.lineSeparator()
                        + "0. Find item by Id" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
        ));
    }

    /**
     * Выполняем проверку выполнения действия - Find item by Id,
     * если выполнение неуспешно.
     */
    @Test
    public void findByIdActionFail() {
        item = tracker.add(item);
        int id = item.getId();
        Input in = new StubInput(
                new String[] {"0", String.valueOf(id + 1), "1"}
        );
        actions.add(new FindByIdAction(out));
        actions.add(new Exit());
        new StartUI(out).init(in, tracker, actions);

        assertThat(out.toString(), is(
                "Menu." + System.lineSeparator()
                        + "0. Find item by Id" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
                        + System.lineSeparator() + "=== Find Item by id ===="
                        + System.lineSeparator()
                        + "Item with this id not found" + System.lineSeparator()
                        + "Menu." + System.lineSeparator()
                        + "0. Find item by Id" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
        ));
    }

    /**
     * Выполняем проверку выполнения действия - Find items by name,
     * если выполнение успешно.
     */
    @Test
    public void findByNameActionSuccess() {
        tracker.add(item);
        String name = item.getName();
        Input in = new StubInput(
                new String[] {"0", name, "1"}
        );
        actions.add(new FindByNameAction(out));
        actions.add(new Exit());
        new StartUI(out).init(in, tracker, actions);

        assertThat(out.toString(), is(
                "Menu." + System.lineSeparator()
                        + "0. Find items by name" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
                        + System.lineSeparator() + "=== Find Item by name ===="
                        + System.lineSeparator()
                        + item + System.lineSeparator()
                        + "Menu." + System.lineSeparator()
                        + "0. Find items by name" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
         ));
    }

    /**
     * Выполняем проверку выполнения действия - Find items by name,
     * если выполнение неуспешно.
     */
    @Test
    public void findByNameActionFail() {
        tracker.add(item);
        String name = item.getName();
        Input in = new StubInput(
                new String[] {"0", name + 1, "1"}
        );
        actions.add(new FindByNameAction(out));
        actions.add(new Exit());
        new StartUI(out).init(in, tracker, actions);

        assertThat(out.toString(), is(
                "Menu." + System.lineSeparator()
                        + "0. Find items by name" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
                        + System.lineSeparator() + "=== Find Item by name ===="
                        + System.lineSeparator()
                        + "No items with this name were found" + System.lineSeparator()
                        + "Menu." + System.lineSeparator()
                        + "0. Find items by name" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
        ));
    }

    /**
     * Выполняем проверку выполнения действия - Exit,
     * если выполнение успешно.
     */
    @Test
    public void whenExitSuccess() {
        Input in = new StubInput(
                new String[] {"0"}
        );
        actions.add(new Exit());
        new StartUI(out).init(in, tracker, actions);

        assertThat(out.toString(), is(
                "Menu." + System.lineSeparator()
                        + "0. Exit" + System.lineSeparator()
        ));
    }

    /**
     * Выполняем проверку выполнения некорректного ввода данных
     * при выборе пунктов меню.
     */
    @Test
    public void whenFailInput() {
        Input in = new StubInput(
                new String[] {"1", "0"}
        );
        actions.add(new Exit());
        new StartUI(out).init(in, tracker, actions);

        assertThat(out.toString(), is(
                        "Menu." + System.lineSeparator()
                                + "0. Exit"  + System.lineSeparator()
                                + "Wrong input, you can select: 0 .. 0"  + System.lineSeparator()
                                + "Menu." + System.lineSeparator()
                                + "0. Exit" + System.lineSeparator()
        ));
    }
}
