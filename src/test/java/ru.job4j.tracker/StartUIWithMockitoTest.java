package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.tracker.action.DeleteAction;
import ru.job4j.tracker.action.FindByIdAction;
import ru.job4j.tracker.action.FindByNameAction;
import ru.job4j.tracker.action.ReplaceAction;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;
import ru.job4j.tracker.io.StubOutput;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.MemoryStore;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

/**
 * Тест класс работы класса StartUI с использованием Mockito
 * @see ru.job4j.tracker.StartUI
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class StartUIWithMockitoTest {

    /**
     * Объект доступа к хранилищу
     */
    private MemoryStore tracker;

    /**
     * Заглушка для объекта вывода
     */
    private Output out;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @Before
    public void setUp() {
        tracker = new MemoryStore();
        out = new StubOutput();
    }

    /**
     * Очищает хранилище данных после каждого теста.
     */
    @After
    public void wipeStore() {
        tracker.clear();
    }

    /**
     * Выполняем проверку выполнения действия - Edit item,
     * если выполнение успешно.
     */
    @Test
    public void replaceActionWithMock() {
        tracker.add(new Item("Replaced item"));
        String replacedName = "New item name";
        ReplaceAction rep = new ReplaceAction(out);

        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        when(input.askStr(any(String.class))).thenReturn(replacedName);
        rep.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is(ln + "=== Edit Item ====" + ln + "Edit completed" + ln));
        assertThat(tracker.findAll().get(0).getName(), is(replacedName));
        tracker.clear();
    }

    /**
     * Выполняем проверку выполнения действия - Edit item,
     * если выполнение неуспешно.
     */
    @Test
    public void replaceActionWithMockWhenItemNotFound() {
        String expectedName = "Replaced item";
        tracker.add(new Item(expectedName));
        String replacedName = "New item name";
        ReplaceAction rep = new ReplaceAction(out);

        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(2);
        when(input.askStr(any(String.class))).thenReturn(replacedName);
        rep.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is(ln + "=== Edit Item ====" + ln
                + "Edit failed, id does not exist" + ln));
        assertThat(tracker.findAll().get(0).getName(), is(expectedName));
        tracker.clear();
    }

    /**
     * Выполняем проверку выполнения действия - Delete item,
     * если выполнение успешно.
     */
    @Test
    public void deleteActionWithMockWhenItemExist() {
        tracker.add(new Item("Deleted item"));
        DeleteAction del = new DeleteAction(out);

        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        del.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is(ln + "=== Delete Item ====" + ln + "Item deleted" + ln));
        assertThat(tracker.findAll().size(), is(0));
        tracker.clear();
    }

    /**
     * Выполняем проверку выполнения действия - Delete item,
     * если выполнение неуспешно.
     */
    @Test
    public void deleteActionWithMockWhenItemNonExist() {
        tracker.add(new Item("Deleted item"));
        DeleteAction del = new DeleteAction(out);

        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(2);
        del.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is(ln + "=== Delete Item ===="
                + ln + "Delete failed, id does not exist" + ln));
        assertThat(tracker.findAll().size(), is(1));
        tracker.clear();
    }

    /**
     * Выполняем проверку выполнения действия - Find item by Id,
     * если выполнение успешно.
     */
    @Test
    public void findByIdActionWithMockWhenItemExist() {
        Item item = new Item("Item with id = 1");
        tracker.add(item);

        FindByIdAction findById = new FindByIdAction(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        findById.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is(ln + "=== Find Item by id ====" + ln + item + ln));
        tracker.clear();
    }

    /**
     * Выполняем проверку выполнения действия - Find item by Id,
     * если выполнение неуспешно.
     */
    @Test
    public void findByIdActionWithMockWhenItemNonExist() {
        tracker.add(new Item("Item with id = 1"));

        FindByIdAction findById = new FindByIdAction(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(2);
        findById.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is(ln + "=== Find Item by id ===="
                + ln + "Item with this id not found" + ln));
        tracker.clear();
    }

    /**
     * Выполняем проверку выполнения действия - Find items by name,
     * если выполнение успешно.
     */
    @Test
    public void findByNameActionWithMockWhenItemExistIs1() {
        Item item = new Item("Item with id = 1");
        tracker.add(item);
        FindByNameAction findByName = new FindByNameAction(out);

        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn("Item with id = 1");
        findByName.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is(ln + "=== Find Item by name ===="
                + ln + item + ln));
        tracker.clear();
    }

    /**
     * Выполняем проверку выполнения действия - Find items by name,
     * если выполнение успешно и заявок несколько.
     */
    @Test
    public void findByNameActionWithMockWhenItemExistMoreThan1() {
        Item item1 = new Item("Item with id = 1");
        Item item2 = new Item("Item with id = 1");
        tracker.add(item1);
        tracker.add(item2);
        FindByNameAction findByName = new FindByNameAction(out);

        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn("Item with id = 1");
        findByName.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is(ln + "=== Find Item by name ====" + ln + item1 + ln
                + item2 + ln));
        tracker.clear();
    }

    /**
     * Выполняем проверку выполнения действия - Find items by name,
     * если выполнение неуспешно.
     */
    @Test
    public void findByNameActionWithMockWhenItemNonExist() {
        String itemName = "Item with id = 1";
        Item item = new Item(itemName);
        tracker.add(item);
        FindByNameAction findByName = new FindByNameAction(out);

        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn("Item with id = 2");
        findByName.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is(ln + "=== Find Item by name ===="
                + ln + "No items with this name were found" + ln));
        tracker.clear();
    }
}
