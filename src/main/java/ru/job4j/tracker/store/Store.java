package ru.job4j.tracker.store;

import ru.job4j.tracker.model.Item;

import java.util.List;

/**
 * Интерфейс Хранилища заявок
 * @see ru.job4j.tracker.model.Item
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface Store extends AutoCloseable {

    /**
     * Выполняет инициализацию необходимых объектов
     * для работы с хранилищем.
     */
    default void init() { }

    /**
     * Выполняет добавление заявки в хранилище
     * и ее возврат.
     *
     * @param item заявка
     * @return заявка
     */
    Item add(Item item);

    /**
     * Выполняет замену заявки в хранилище.
     *
     * @param item заявка
     * @return true или false в зависимости от результата
     */
    boolean replace(int id, Item item);

    /**
     * Выполняет удаление заявки из хранилища.
     *
     * @param id идентификатор заявки
     * @return true или false в зависимости от результата
     */
    boolean delete(int id);

    /**
     * Выполняет возврат из хранилища списка всех заявок.
     *
     * @return список заявок
     */
    List<Item> findAll();

    /**
     * Выполняет поиск по наименованию и возврат из хранилища списка
     * найденных заявок.
     *
     * @return список заявок
     */
    List<Item> findByName(String key);

    /**
     * Выполняет поиск по идентификатору и возврат из хранилища заявки.
     *
     * @param id идентификатор заявки
     * @return заявка
     */
    Item findById(int id);
}