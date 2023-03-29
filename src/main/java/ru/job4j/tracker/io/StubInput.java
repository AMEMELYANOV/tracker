package ru.job4j.tracker.io;

/**
 * Класс реализация заглушки ввода
 * @see ru.job4j.tracker.io.Input
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class StubInput implements Input {

    /**
     * Массив ответов
     */
    private String[] answers;

    /**
     * Позиция
     */
    private int position = 0;

    /**
     * Конструктор
     *
     * @param answers массив ответов
     */
    public StubInput(String[] answers) {
        this.answers = answers;
    }

    /**
     * Выполняет вывод вопроса выбора действия и
     * возвращает ответ из списка ответов, увеличивая
     * величину позиции.
     *
     * @param question вопрос
     * @return ответ
     */
    @Override
    public String askStr(String question) {
        return answers[position++];
    }

    /**
     * Считывает числовой ответ,
     * содержащий выбор действия.
     *
     * @param question вопрос с выбором действия
     * @return числовое представление действия
     */
    @Override
    public int askInt(String question) {
        return Integer.parseInt(askStr(question));
    }
}