package ru.job4j.tracker;

import org.junit.*;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.JDBCStore;
import ru.job4j.tracker.store.Store;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тест класс реализации хранилища заявок с использованием JDBC
 * @see ru.job4j.tracker.model.Item
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class JDBCStoreTest {

    /**
     * Соединение с базой данных
     */
    private static Connection connection;

    /**
     * Объект доступа к хранилищу
     */
    private Store tracker;

    /**
     * Заявка
     */
    private Item item;

    /**
     * Создает необходимый для выполнения тестов объект.
     * Создание выполняется перед всеми тестами.
     */
    @BeforeClass
    public static void initConnection() {
        try (InputStream in = JDBCStore.class.getClassLoader()
                .getResourceAsStream("test.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @Before
    public void setUp() {
        tracker = new JDBCStore(connection);
        item = new Item("name", "description");
    }

    /**
     * Закрывает ресурсы после работы всех тестов.
     */
    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Очищает хранилище данных после каждого теста.
     */
    @After
    public void wipeStore() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    /**
     * Выполняем проверку добавление заявки в хранилище.
     * Через вызов метода {@link JDBCStore#findById(int)}
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
     * Через вызов метода {@link JDBCStore#findById(int)}
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
     * Через вызов метода {@link JDBCStore#findById(int)}
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
     * Добавляем заявку в хранилище методом {@link JDBCStore#add(Item)}
     * Через вызов метода {@link JDBCStore#findAll()} получаем список
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
     * Через вызов метода {@link JDBCStore#findByName(String)}
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
     * Через вызов метода {@link JDBCStore#findById(int)}
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
     * если заявка изменилась. Через вызов метода {@link JDBCStore#replace(int, Item)}
     * получаем boolean результат, который проверяем на равенство false.
     */
    @Test
    public void whenReplaceFail() {
        tracker.add(this.item);
        int id = item.getId();
        Item newItem = new Item("name2", "description2");
        int newId = id + 1;

        boolean expected = tracker.replace(newId, newItem);

        assertThat(expected, is(false));
    }

    /**
     * Выполняем проверку неудачного удаления заявки из хранилища,
     * если заявка изменилась. Через вызов метода {@link JDBCStore#delete(int)}
     * получаем boolean результат, который проверяем на равенство false.
     */
    @Test
    public void whenDeleteFail() {
        tracker.add(item);
        int id = item.getId();
        int newId = id + 1;

        boolean expected = tracker.delete(newId);

        assertThat(expected, is(false));
    }

    /**
     * Выполняем проверку неудачного поиска по id заявки в хранилище,
     * если заявка изменилась. Через вызов метода {@link JDBCStore#findById(int)}
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
     * если заявка изменилась. Через вызов метода {@link JDBCStore#findByName(String)}
     * получаем результат, который проверяем на равенство null.
     */
    @Test
    public void whenFindByNameFail() {
        item = tracker.add(this.item);
        String name = item.getName() + "2";

        List<Item> expected = tracker.findByName(name);

        assertThat(expected.size(), is(0));
    }
}