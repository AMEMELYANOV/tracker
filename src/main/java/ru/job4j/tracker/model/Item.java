package ru.job4j.tracker.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Модель данных заявка
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
public class Item {

    /**
     * Идентификатор заявки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Наименование заявки
     */
    private String name;

    /**
     * Время создание заявки
     */
    private Timestamp created = new Timestamp(System.currentTimeMillis());

    /**
     * Описание заявки
     */
    private String description;

    /**
     * Конструктор.
     *
     * @param id идентификатор заявки
     */
    public Item(int id) {
        this.id = id;
    }

    /**
     * Конструктор.
     *
     * @param name наименование заявки
     */
    public Item(String name) {
        this.name = name;
    }

    /**
     * Конструктор.
     *
     * @param id идентификатор заявки
     * @param name наименование заявки
     */
    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Конструктор.
     *
     * @param name наименование заявки
     * @param description описание заявки
     */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
}