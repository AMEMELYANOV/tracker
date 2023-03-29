package ru.job4j.tracker;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.HibernateStore;
import ru.job4j.tracker.store.Store;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тест класс реализации хранилища заявок с использованием Hibernate
 * @see ru.job4j.tracker.model.Item
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class HibernateStoreTest {

    /**
     * Объект доступа к хранилищу
     */
    private Store tracker;

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
        tracker = new HibernateStore();
        item = new Item("name1", "description1");
    }

    /**
     * Выполняем проверку добавление заявки в хранилище.
     * Через вызов метода {@link HibernateStore#findAll()}
     * получаем список объектов items из базы данных.
     * Далее сравниваем сохраненный объект с полученным из хранилища.
     */
    @Test
    public void whenAddSuccess() {
        tracker.add(item);

        List<Item> items = tracker.findAll();
        Item result = items.get(0);

        assertThat(items.size(), is(1));
        assertThat(result.getId(), is(item.getId()));
        assertThat(result.getName(), is(item.getName()));
        assertThat(result.getDescription(), is(item.getDescription()));
        assertThat(result.getCreated(), is(item.getCreated()));
    }

    /**
     * Выполняем проверку обновления заявки в хранилище.
     * Через вызов метода {@link HibernateStore#findAll()}
     * получаем список объектов items из базы данных.
     * Далее сравниваем обновленный объект с полученным из хранилища.
     */
    @Test
    public void whenReplaceSuccess() {
        item = tracker.add(item);
        int id = item.getId();
        Item expect = new Item("name2", "description2");
        tracker.replace(id, expect);

        List<Item> items = tracker.findAll();
        Item result = items.get(0);

        assertThat(items.size(), is(1));
        assertThat(result.getId(), is(expect.getId()));
        assertThat(result.getName(), is(expect.getName()));
        assertThat(result.getDescription(), is(expect.getDescription()));
        assertThat(result.getCreated(), is(expect.getCreated()));
    }

    /**
     * Выполняем проверку удаления заявки из хранилища.
     * Через вызов метода {@link HibernateStore#findAll()}
     * получаем список объектов items из базы данных.
     * Далее проверяем полученный список заявок на эквивалентность пустому списку.
     */
    @Test
    public void whenDeleteSuccess() {
        item = tracker.add(item);
        int id = item.getId();
        tracker.delete(id);

        List<Item> items = tracker.findAll();

        assertThat(items.size(), is(0));
    }

    /**
     * Выполняем проверку метода выгрузки всех заявок из хранилища.
     * Добавляем пару заявок в хранилище методом {@link HibernateStore#add(Item)}
     * Через вызов метода {@link HibernateStore#findAll()}
     * получаем список объектов items из базы данных.
     * Далее сравниваем ожидаемый объект с полученным из хранилища по составу
     * объектов и размеру.
     */
    @Test
    public void whenFindAll() {
        List<Item> expect = new ArrayList<>();
        expect.add(tracker.add(new Item("name1", "description1")));
        expect.add(tracker.add(new Item("name2", "description2")));

        List<Item> result = tracker.findAll();

        assertThat(result.size(), is(2));
        assertThat(result, is(expect));
    }


    /**
     * Выполняем проверку поиска заявки в хранилище по id.
     * Через вызов метода {@link HibernateStore#findById(int)}
     * получаем заявку из хранилища, далее сравниваем полученный объект
     * с изначально сохраненным.
     */
    @Test
    public void whenFindByIdSuccess() {
        item = tracker.add(item);
        int id = item.getId();
        Item result = tracker.findById(id);

        assertThat(result.getId(), is(item.getId()));
        assertThat(result.getName(), is(item.getName()));
        assertThat(result.getDescription(), is(item.getDescription()));
        assertThat(result.getCreated(), is(item.getCreated()));
    }

    /**
     * Выполняем проверку поиска заявки в хранилище по name.
     * Через вызов метода {@link HibernateStore#findByName(String)}
     * получаем заявку из хранилища, далее сравниваем полученный объект
     * с изначально сохраненным.
     */
    @Test
    public void whenFindByNameSuccess() {
        item = tracker.add(item);
        String name = item.getName();
        List<Item> items = tracker.findByName(name);
        Item result = items.get(0);

        assertThat(items.size(), is(1));
        assertThat(result.getId(), is(item.getId()));
        assertThat(result.getName(), is(item.getName()));
        assertThat(result.getDescription(), is(item.getDescription()));
        assertThat(result.getCreated(), is(item.getCreated()));
    }

    /**
     * Выполняем проверку неудачного обновления заявки в хранилище,
     * если заявка изменилась. Через вызов метода {@link HibernateStore#replace(int, Item)}
     * получаем boolean результат, который проверяем на равенство false.
     */
    @Test
    public void whenReplaceFail() {
        tracker.add(item);
        int id = 2;
        Item newItem = new Item("name2", "description2");
        boolean result = tracker.replace(id, newItem);

        assertThat(result, is(false));
    }

    /**
     * Выполняем проверку неудачного удаления заявки из хранилища,
     * если заявка изменилась. Через вызов метода {@link HibernateStore#delete(int)}
     * получаем boolean результат, который проверяем на равенство false.
     */
    @Test
    public void whenDeleteFail() {
        tracker.add(item);
        int id = 2;
        boolean result = tracker.delete(id);

        assertThat(result, is(false));
    }

    /**
     * Выполняем проверку неудачного поиска по id заявки в хранилище,
     * если заявка изменилась. Через вызов метода {@link HibernateStore#findById(int)}
     * получаем результат, который проверяем на равенство null.
     */
    @Test
    public void whenFindByIdFail() {
        tracker.add(item);
        int id = 2;
        Item result = tracker.findById(id);

        assertNull(result);
    }

    /**
     * Выполняем проверку неудачного поиска по name заявки в хранилище,
     * если заявка изменилась. Через вызов метода {@link HibernateStore#findByName(String)}
     * получаем результат, который проверяем на равенство null.
     */
    @Test
    public void whenFindByNameFail() {
        tracker.add(item);
        String name = "name2";
        List<Item> result = tracker.findByName(name);
        System.out.println(result);

        assertThat(result.size(), is(0));
    }
}