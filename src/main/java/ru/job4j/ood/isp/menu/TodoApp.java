package ru.job4j.ood.isp.menu;

import java.util.Scanner;

public class TodoApp {

    private static final ActionDelegate DEFAULT_ACTION = () -> System.out.println("Keep it simple stupid");
    private static final String MENU = """
                Введите 1 для добавления элемента в корень меню.
                Введите 2 для добавления элемента к родительскому элементу.
                Введите 3 для вызова действия, привязанного к пункту меню.
                Введите 4 чтобы вывести меню в консоль.
                Введите 5 для выхода.
            """;

    private static void start(Scanner scanner, Menu menu, MenuPrinter printer) {
        boolean run = true;
        while (run) {
            System.out.println();
            System.out.println(MENU);
            System.out.println("Выберите меню:");
            int choice = scanner.nextInt();
            System.out.println(choice);
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("Введите имя элемента");
                    String name = scanner.nextLine();
                    System.out.println(menu.add(menu.ROOT, name, DEFAULT_ACTION)
                            ? "Элемент добавлен в корень меню." : "Элемент в корень меню не добавлен.");
                }
                case 2 -> {
                    if (menu.iterator().hasNext()) {
                        System.out.println("Введите имя родительского элемента");
                        String parentName = scanner.nextLine();
                        System.out.println("Введите имя дочернего элемента");
                        String childName = scanner.nextLine();
                        System.out.println(menu.add(parentName, childName, DEFAULT_ACTION)
                                ? "Элемент добавлен к родительскому элементу."
                                : "Элемент к родительскому элементу не добавлен.");
                    } else {
                        System.out.println("Меню еще не содержит элементов. Сначала добавьте элементы.");
                    }
                }
                case 3 -> {
                    if (menu.iterator().hasNext()) {
                        System.out.println("Введите имя элемента");
                        String name = scanner.nextLine();
                        var menuItemInfo = menu.select(name);
                        if (menuItemInfo.isPresent()) {
                            menuItemInfo.get().getActionDelegate().delegate();
                        } else {
                            System.out.printf("По указанному имени %s элемент в меню не найден.%n", name);
                        }
                    } else {
                        System.out.println("Меню еще не содержит элементов. Сначала добавьте элементы.");
                    }
                }
                case 4 -> printer.print(menu);
                case 5 -> {
                    run = false;
                    System.out.println("Конец работы.");
                }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new SimpleMenu();
        MenuPrinter printer = new Printer();
        start(scanner, menu, printer);
    }
}
