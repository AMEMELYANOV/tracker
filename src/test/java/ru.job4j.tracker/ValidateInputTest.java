package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.io.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Тест класс работы класса ValidateInput
 * @see ru.job4j.tracker.io.ValidateInput
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class ValidateInputTest {

    /**
     * Проверяет валидацию на некорректный ввод значений.
     */
    @Test
    public void whenInvalidInput() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"one", "1"}
        );
        ValidateInput input = new ValidateInput(out, in);
        int selected = input.askInt("Enter menu:");
        assertThat(selected, is(1));
    }

    /**
     * Проверяет валидацию на корректный ввод значений.
     */
    @Test
    public void whenValidInput() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"1"}
        );
        ValidateInput input = new ValidateInput(out, in);
        int selected = input.askInt("Enter menu:");
        assertThat(selected, is(1));
    }

    /**
     * Проверяет валидацию на множественный ввод корректных значений.
     */
    @Test
    public void whenMultipleValidInput() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"1", "2", "3", "4", "5", "6"}
        );
        ValidateInput input = new ValidateInput(out, in);
        int selected1 = input.askInt("Enter menu:");
        int selected2 = input.askInt("Enter menu:");
        int selected3 = input.askInt("Enter menu:");
        int selected4 = input.askInt("Enter menu:");
        int selected5 = input.askInt("Enter menu:");
        int selected6 = input.askInt("Enter menu:");
        assertThat(selected1, is(1));
        assertThat(selected2, is(2));
        assertThat(selected3, is(3));
        assertThat(selected4, is(4));
        assertThat(selected5, is(5));
        assertThat(selected6, is(6));
    }

    /**
     * Проверяет валидацию на отрицательные значения ввода.
     */
    @Test
    public void whenNegativeInput() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"-1"}
        );
        ValidateInput input = new ValidateInput(out, in);
        int selected = input.askInt("Enter menu:");
        assertThat(selected, is(-1));
    }
}