package ru.job4j.tracker.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.tracker.model.Item;

import java.util.List;

/**
 * Класс реализации хранилища заявок
 * хранение осуществляется в базе данных,
 * для работы используется Hibernate
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.tracker.store.Store
 */
public class HibernateStore implements Store, AutoCloseable {

    /**
     * HQL запрос по выбору заявок из таблицы items по полю name
     */
    private final static String FIND_ITEMS_BY_NAME = "from Item where name = :paramName";

    /**
     * HQL запрос по выбору всех заявок из таблицы
     */
    private final static String FIND_ALL_ITEMS = "from Item";

    /**
     * Экземпляр StandardServiceRegistry
     */
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    /**
     * Экземпляр SessionFactory
     */
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    /**
     * Выполняет добавление заявки в хранилище
     * и ее возврат.
     *
     * @param item заявка
     * @return заявка
     */
    @Override
    public Item add(Item item) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
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
        if (findById(id) != null) {
            item.setId(id);
            try (Session session = sf.openSession()) {
                session.beginTransaction();
                session.update(item);
                session.getTransaction().commit();
                result = true;
            }
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
        if (findById(id) != null) {
            try (Session session = sf.openSession()) {
                session.beginTransaction();
                session.delete(new Item(id));
                session.getTransaction().commit();
                result = true;
            }
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
        List<Item> items;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            items = session.createQuery(FIND_ALL_ITEMS).list();
            session.getTransaction().commit();
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
        List<Item> items;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(FIND_ITEMS_BY_NAME);
            query.setParameter("paramName", key);
            items = query.list();
            session.getTransaction().commit();
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
        Item result;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = session.get(Item.class, id);
            session.getTransaction().commit();
        }
        return result;
    }

    /**
     * Закрывает соединение после окончания работы хранилища.
     */
    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}