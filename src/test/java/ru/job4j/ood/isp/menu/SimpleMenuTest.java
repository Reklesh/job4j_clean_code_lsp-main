package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class SimpleMenuTest {
    private static final ActionDelegate STUB_ACTION = System.out::println;
    private Menu menu;

    @BeforeEach
    void initData() {
        menu = new SimpleMenu();
        menu.add(menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
    }

    @Test
    public void whenAddThenReturnSame() {
        assertThat(new Menu.MenuItemInfo("Сходить в магазин",
                List.of("Купить продукты"), STUB_ACTION, "1."))
                .isEqualTo(menu.select("Сходить в магазин").get());
        assertThat(new Menu.MenuItemInfo(
                "Купить продукты",
                List.of("Купить хлеб", "Купить молоко"), STUB_ACTION, "1.1."))
                .isEqualTo(menu.select("Купить продукты").get());
        assertThat(new Menu.MenuItemInfo(
                "Покормить собаку", List.of(), STUB_ACTION, "2."))
                .isEqualTo(menu.select("Покормить собаку").get());
        menu.forEach(i -> System.out.println(i.getNumber() + i.getName()));
    }

    @Test
    public void checkSelect() {
        assertThat(menu.select("Съездить в магазин")).isNotPresent();
        assertThatThrownBy(() -> menu.select("Съездить в деревню")
                .orElseThrow(() -> new RuntimeException("Элемент с таким именем в меню не найден.")))
                .isInstanceOf(RuntimeException.class)
                .message()
                .isNotEmpty();
    }
}