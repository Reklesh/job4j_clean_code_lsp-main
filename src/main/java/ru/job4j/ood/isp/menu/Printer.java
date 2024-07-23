package ru.job4j.ood.isp.menu;

public class Printer implements MenuPrinter {

    @Override
    public void print(Menu menu) {
        if (menu.iterator().hasNext()) {
            menu.forEach(i -> {
                var array = i.getNumber().split("\\.");
                if (array.length > 1) {
                    System.out.print("-".repeat(array.length * 2));
                }
                System.out.println(i.getNumber() + i.getName());
            });
        } else {
            System.out.println("Меню еще не содержит элементов. Сначала добавьте элементы.");
        }
    }
}
