package ru.job4j.tracker;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.MemoryStore;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Тест класс реализации хранилища заявок с использованием MemoryStore
 * @see ru.job4j.tracker.model.Item
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class MemoryStoreTest {

    /**
     * Объект доступа к хранилищу
     */
    private MemoryStore tracker;

    /**
     * Заявка
     */
    private Item item;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @Before
    public void setUp() {
        tracker = MemoryStore.getInstance();
        item = new Item("name", "description");
    }

    /**
     * Очищает хранилище данных после каждого теста.
     */
    @After
    public void wipeStore() {
        tracker.clear();
    }

    /**
     * Выполняем проверку добавление заявки в хранилище.
     * Через вызов метода {@link MemoryStore#findById(int)}
     * получаем объект item из базы данных.
     * Далее сравниваем сохраненный объект с полученным из хранилища
     * по name.
     */
    @Test
    public void whenAddSuccess() {
        int id = tracker.add(item).getId();

        Item expected = tracker.findById(id);

        assertEquals(expected.getName(), item.getName());
    }

    /**
     * Выполняем проверку обновления заявки в хранилище.
     * Через вызов метода {@link MemoryStore#findById(int)}
     * получаем объект item из базы данных.
     * Далее сравниваем обновленный объект с полученным из хранилища.
     */
    @Test
    public void whenReplaceSuccess() {
        item = tracker.add(new Item("name"));
        int id = item.getId();
        item.setName("newName");

        boolean expectedBoolean = tracker.replace(id, item);
        Item expected = tracker.findById(id);

        assertTrue(expectedBoolean);
        assertEquals(expected.getName(), item.getName());
    }

    /**
     * Выполняем проверку удаления заявки из хранилища.
     * Через вызов метода {@link MemoryStore#findById(int)}
     * получаем объект item из базы данных после его удаления.
     * Далее проверяем полученный объект на эквивалентность null.
     */
    @Test
    public void whenDeleteSuccess() {
        int id = tracker.add(item).getId();

        assertTrue(tracker.delete(id));
        assertNull(tracker.findById(id));
    }

    /**
     * Выполняем проверку метода выгрузки всех заявок из хранилища.
     * Добавляем заявку в хранилище методом {@link MemoryStore#add(Item)}
     * Через вызов метода {@link MemoryStore#findAll()} получаем список
     * объектов items из базы данных. Далее сравниваем сохраненный объект
     * с полученным из хранилища по name и размер списка с кол-вом сохраненных объектов.
     */
    @Test
    public void whenFindAllSuccess() {
        tracker.add(item);

        List<Item> expectedList = tracker.findAll();
        Item expected = expectedList.get(0);

        assertEquals(expected.getName(), item.getName());
        assertEquals(expectedList.size(), 1);
    }

    /**
     * Выполняем проверку поиска заявки в хранилище по name.
     * Через вызов метода {@link MemoryStore#findByName(String)}
     * получаем заявку из хранилища, далее сравниваем полученный объект
     * с изначально сохраненным.
     */
    @Test
    public void whenFindByNameSuccess() {
        item = tracker.add(item);
        String name = item.getName();

        List<Item> expectedList = tracker.findByName(name);
        Item expected = expectedList.get(0);

        assertEquals(item.getName(), expected.getName());
        assertEquals(expectedList.size(), 1);
    }

    /**
     * Выполняем проверку поиска заявки в хранилище по id.
     * Через вызов метода {@link MemoryStore#findById(int)}
     * получаем заявку из хранилища, далее сравниваем полученный объект
     * с изначально сохраненным.
     */
    @Test
    public void whenFindByIdSuccess() {
        item = tracker.add(item);
        int id = item.getId();

        Item expected = tracker.findById(id);

        assertEquals(expected.getName(), item.getName());
    }

    /**
     * Выполняем проверку неудачного обновления заявки в хранилище,
     * если заявка изменилась. Через вызов метода {@link MemoryStore#replace(int, Item)}
     * получаем boolean результат, который проверяем на равенство false.
     */
    @Test
    public void whenReplaceFail() {
        tracker.add(this.item);
        int id = item.getId();
        Item newItem = new Item("name2", "description2");
        int newId = id + 1;

        boolean expected = tracker.replace(newId, newItem);

        assertThat(expected, Is.is(false));
    }

    /**
     * Выполняем проверку неудачного удаления заявки из хранилища,
     * если заявка изменилась. Через вызов метода {@link MemoryStore#delete(int)}
     * получаем boolean результат, который проверяем на равенство false.
     */
    @Test
    public void whenDeleteFail() {
        tracker.add(item);
        int id = item.getId();
        int newId = id + 1;

        boolean expected = tracker.delete(newId);

        assertThat(expected, Is.is(false));
    }

    /**
     * Выполняем проверку неудачного поиска по id заявки в хранилище,
     * если заявка изменилась. Через вызов метода {@link MemoryStore#findById(int)}
     * получаем результат, который проверяем на равенство null.
     */
    @Test
    public void whenFindByIdFail() {
        tracker.add(item);
        int id = item.getId();
        int newId = id + 1;

        Item expected = tracker.findById(newId);

        assertNull(expected);
    }

    /**
     * Выполняем проверку неудачного поиска по name заявки в хранилище,
     * если заявка изменилась. Через вызов метода {@link MemoryStore#findByName(String)}
     * получаем результат, который проверяем на равенство null.
     */
    @Test
    public void whenFindByNameFail() {
        item = tracker.add(this.item);
        String name = item.getName() + "2";

        List<Item> expected = tracker.findByName(name);

        assertThat(expected.size(), Is.is(0));
    }
}