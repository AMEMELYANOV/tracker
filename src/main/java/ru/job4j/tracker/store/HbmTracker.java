package ru.job4j.tracker.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.tracker.model.Item;

import java.util.List;

public class HbmTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        }
        return item;
    }

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

    @Override
    public List<Item> findAll() {
        List<Item> items;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            items = session.createQuery("from Item").list();
            session.getTransaction().commit();
        }
        return items;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> items;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Item where name = :paramName");
            query.setParameter("paramName", key);
            items = query.list();
            session.getTransaction().commit();
        }
        return items;
    }

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

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}