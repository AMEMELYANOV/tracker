package ru.job4j.tracker.store;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Класс реализации хранилища заявок
 * хранение осуществляется в базе данных,
 * для работы используется JDBC
 * @see ru.job4j.tracker.store.Store
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
public class JDBCStore implements Store {

    /**
     * Соединение с базой данных
     */
    private Connection cn;

    /**
     * Выполняет инициализацию соединения с базой данных
     * для работы с хранилищем.
     */
    public void init() {
        try (InputStream in = JDBCStore.class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Выполняет добавление заявки в хранилище
     * и ее возврат.
     *
     * @param item заявка
     * @return заявка
     */
    @Override
    public Item add(Item item) {
        try (PreparedStatement ps = cn.prepareStatement("insert into items(name) values (?)",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getName());
            ps.execute();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    /**
     * Выполняет замену заявки в хранилище.
     * Возвращает true, если заявка с искомым идентификатором
     * есть в хранилище.
     *
     * @param item заявка
     * @return true, если замена выполнена, иначе false
     */
    @Override
    public boolean replace(int id, Item item) {
        boolean result = false;
        try (PreparedStatement ps = cn.prepareStatement("update items set name = ? where id = ?")) {
            ps.setString(1, item.getName());
            ps.setInt(2, id);
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Выполняет удаление заявки из хранилища.
     *
     * @param id идентификатор заявки
     * @return true, если удаление выполнено, иначе false
     */
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (PreparedStatement ps = cn.prepareStatement("delete from items where id = ?")) {
            ps.setInt(1, id);
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Выполняет возврат из хранилища списка всех заявок.
     * Если заявки отсутствуют, вернется пустой список.
     *
     * @return список заявок
     */
    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement ps = cn.prepareStatement("select * from items")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    items.add(new Item(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Выполняет поиск по наименованию и возврат из хранилища списка
     * найденных заявок. Если заявки не найдены, вернется пустой список.
     *
     * @return список заявок
     */
    @Override
    public List<Item> findByName(String key) {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement ps = cn.prepareStatement("select * from items where name = ?")) {
            ps.setString(1, key);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    items.add(new Item(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Выполняет поиск по идентификатору и возврат из хранилища заявки.
     * Если заявка не найдена, будет возвращен null.
     *
     * @param id идентификатор заявки
     * @return заявка
     */
    @Override
    public Item findById(int id) {
        Item item = null;
        try (PreparedStatement ps = cn.prepareStatement("select * from items where id = ?")) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    item = (new Item(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    /**
     * Закрывает соединение после окончания работы хранилища.
     */
    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }
}