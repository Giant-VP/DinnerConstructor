package ru.practicum.dinner;

import java.util.Scanner;

public class Main {

    static DinnerConstructor dc;
    static Scanner scanner;

    public static void main(String[] args) {
        dc = new DinnerConstructor();
        scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    addNewDish();
                    break;
                case "2":
                    dc.printDish();
                    break;
                case "3":
                    generateDishCombo();
                    break;

                case "4":
                    System.out.println("До скорой встречи!(◕‿◕)");
                    return;
                default:
                    System.out.println("Вы ввели: " + command);
                    System.out.println("Такой команды не существует  ¯¯\\_(ツ)_/¯¯ \n");


            }
        }
    }

    private static void printMenu() {
        System.out.println("Выберите команду:");
        System.out.println("1 - Добавить новое блюдо");
        System.out.println("2 - Отобразить список блюд");  //Для удобства добавил пункт просмотра списка текущих групп и названий блюд
        System.out.println("3 - Сгенерировать комбинации блюд");
        System.out.println("4 - Выход");
        System.out.print("--> ");
    }

    private static void addNewDish() {
        System.out.println("Введите тип блюда:");
        String dishType = scanner.nextLine();
        System.out.println("Введите название блюда:");
        String dishName = scanner.nextLine();

        // добавьте новое блюдо
        dc.addDish(dishType,dishName); //добавляем блюдо с проверкой на повторное добавление.
    }

    private static void generateDishCombo() {
        System.out.println("Начинаем конструировать обед...");

        System.out.println("Введите количество наборов, которые нужно сгенерировать:");
        int numberOfCombos = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Вводите типы блюда, разделяя символом переноса строки (enter). Для завершения ввода введите пустую строку");
        String nextItem = scanner.nextLine();


        //реализуйте ввод типов блюд
        while (!nextItem.isEmpty()) {
             dc.addTypeDish(nextItem); //метод добавляет Тип в список для генерации, с проверкой на наличии Типа в таблице HashMap
             nextItem = scanner.nextLine(); //Повторяем ввод Типов, пока типы есть в списке или не будет ввода пустой строки
        }
        // сгенерируйте комбинации блюд и выведите на экран
        // Используем цикл для отображения рандомного списка блюд по введенному типу, кол-во вариантов указаны пользователем.
        for (int i = 0; i < numberOfCombos; i++) {
            if(dc.typesDishes.isEmpty()){
                break;   //Если не было введено ни одного типа, наш список типов пуст, выходим из цикла.
            }
            System.out.printf("Комбо %d \n", i + 1);
            System.out.println(dc.randomGeneratorDishes()+"\n");
        }

        // Очистим список. Чтобы при следующей генерации блюд, список был пуст, для внесения новых Типов блюд.
        dc.typesDishes.clear();
    }
}
