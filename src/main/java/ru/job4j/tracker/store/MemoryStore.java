package ru.job4j.tracker.store;

import lombok.NoArgsConstructor;
import ru.job4j.tracker.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализации хранилища заявок
 * хранение осуществляется в памяти
 * @see ru.job4j.tracker.store.Store
 * @author Alexander Emelyanov
 * @version 1.0
 */
@NoArgsConstructor
public final class MemoryStore implements Store {

    /**
     * Экземпляр хранилища
     */
    private static MemoryStore instance = null;

    /**
     * Список заявок
     */
    private final List<Item> items = new ArrayList<>();

    /**
     * Указатель на текущую ячейку
     */
    private int ids = 1;

    /**
     * Выполняет инициализацию и возврат
     * объекта класса MemoryStore.
     *
     * @return объект MemoryStore.
     */
    public static MemoryStore getInstance() {
        if (instance == null) {
            instance = new MemoryStore();
        }
        return instance;
    }

    /**
     * Выполняет добавление заявки в хранилище
     * и ее возврат.
     *
     * @param item заявка
     * @return заявка
     */
    public Item add(Item item) {
        item.setId(ids++);
        items.add(item);
        return item;
    }

    /**
     * Выполняет поиск по идентификатору и возврат из хранилища заявки.
     * Если заявка не найдена, будет возвращен null.
     *
     * @param id идентификатор заявки
     * @return заявка
     */
    public Item findById(int id) {
        int index = indexOf(id);
        return index != -1 ? items.get(index) : null;
    }

    /**
     * Выполняет поиск по наименованию и возврат из хранилища списка
     * найденных заявок. Если заявки не найдены, вернется пустой список.
     *
     * @return список заявок
     */
    public List<Item> findByName(String key) {
        List<Item> rslItems = new ArrayList<>();
        for (Item item: items) {
            if (item.getName().equals(key)) {
                rslItems.add(item);
            }
        }
        return rslItems;
    }

    /**
     * Выполняет возврат из хранилища списка всех заявок.
     * Если заявки отсутствуют, вернется пустой список.
     *
     * @return список заявок
     */
    public List<Item> findAll() {
        return items;
    }

    /**
     * Выполняет поиск идентификатора в хранилище элемента с идентификатором
     * заявки. Если элемент найден, то вернет идентификатор списка, иначе -1.
     *
     * @param id идентификатор заявки
     * @return идентификатор заявки в списке хранилища
     */
    private int indexOf(int id) {
        int rsl = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    /**
     * Выполняет замену заявки в хранилище.
     * Возвращает true, если заявка с искомым идентификатором
     * есть в хранилище. Поиск производится вспомогательным методом
     * {@link MemoryStore#indexOf(int)}.
     *
     * @param item заявка
     * @return true, если замена выполнена, иначе false
     */
    public boolean replace(int id, Item item) {
        int index = indexOf(id);
        boolean rsl = index != -1;
        if (rsl) {
            item.setId(id);
            items.set(index, item);
        }
        return rsl;
    }

    /**
     * Выполняет удаление заявки из хранилища.
     *
     * @param id идентификатор заявки
     * @return true, если удаление выполнено, иначе false
     */
    public boolean delete(int id) {
        int index = indexOf(id);
        boolean rsl = index != -1;
        if (rsl) {
            items.remove(index);
        }
        return rsl;
    }

    /**
     * Закрывает используемые ресурсы после окончания работы хранилища.
     */
    @Override
    public void close() throws Exception {

    }

    /**
     * Выполняет очистку внутреннего списка хранилища и
     * сброс указателя на 1.
     */
    public void clear() {
        ids = 1;
        items.clear();
    }
}