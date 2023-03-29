package ru.job4j.tracker.io;

/**
 * Класс реализация заглушки вывода данных
 * @see ru.job4j.tracker.io.Output
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class StubOutput implements Output {

    /**
     * Строковый буффеп
     */
    private final StringBuilder buffer = new StringBuilder();

    /**
     * Получает и добавляет объект в буффер.
     *
     * @param obj объект для вывода
     */
    @Override
    public void println(Object obj) {
        if (obj != null) {
            buffer.append(obj.toString());
        } else {
            buffer.append("null");
        }
        buffer.append(System.lineSeparator());
    }

    /**
     * Возвращает строковое представление буффера.
     *
     * @return строковое представление буффера
     */
    @Override
    public String toString() {
        return buffer.toString();
    }
}