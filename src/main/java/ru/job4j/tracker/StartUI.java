package ru.job4j.tracker;

import lombok.AllArgsConstructor;
import ru.job4j.tracker.action.*;
import ru.job4j.tracker.io.*;
import ru.job4j.tracker.store.HibernateStore;
import ru.job4j.tracker.store.Store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Основной класс для запуска приложения
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
public class StartUI {
    private final Output out;

    /**
     * Запускает главный цикл приложения,
     * работает пока не будет выбран пункт выход.
     *
     * @param input объект ввода
     * @param tracker объект работы с хранилищем данных
     * @param actions список действий приложения
     */
    public void init(Input input, Store tracker, List<UserAction> actions) {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Select: ");
            if (select < 0 || select >= actions.size()) {
                out.println("Wrong input, you can select: 0 .. " + (actions.size() - 1));
                continue;
            }
            UserAction action = actions.get(select);
            run = action.execute(input, tracker);
        }
    }

    /**
     * Вывод с систему вывода списка действий.
     *
     * @param actions список действий приложения
     */
    private void showMenu(List<UserAction> actions) {
        out.println("");
        out.println("Menu.");
        for (int index = 0; index < actions.size(); index++) {
            out.println(index + ". " + actions.get(index).name());
        }
    }

    /**
     * Выполняет запуск приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Output output = new ConsoleOutput();
        Input input = new ValidateInput(output, new ConsoleInput());
        try (Store tracker = new HibernateStore()) {
            tracker.init();
            List<UserAction> actions = new ArrayList<>(Arrays.asList(
                    new CreateAction(output),
                    new ShowAction(output),
                    new ReplaceAction(output),
                    new DeleteAction(output),
                    new FindByIdAction(output),
                    new FindByNameAction(output),
                    new Exit()
            ));
            new StartUI(output).init(input, tracker, actions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}