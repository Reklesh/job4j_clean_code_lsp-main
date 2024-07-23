package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

class PrinterTest {
    private static final ActionDelegate STUB_ACTION = System.out::println;
    private final Menu menu = new SimpleMenu();

    @Test
    void whenMenuItemsAddedThenMenuItemsDisplayed() {
        menu.add(menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        String expect = """
                1.Сходить в магазин
                ----1.1.Купить продукты
                ------1.1.1.Купить хлеб
                ------1.1.2.Купить молоко
                2.Покормить собаку
                """;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        new Printer().print(menu);
        assertThat(outContent.toString().trim()).isEqualToIgnoringWhitespace(expect);
    }

    @Test
    void whenNoMenuItemsAddedThenDefaultMessageDisplayed() {
        String expect = "Меню еще не содержит элементов. Сначала добавьте элементы.";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        new Printer().print(menu);
        assertThat(outContent.toString().trim()).isEqualTo(expect);
    }
}