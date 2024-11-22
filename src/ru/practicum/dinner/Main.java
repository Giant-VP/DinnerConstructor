package ru.practicum.dinner;

import java.util.ArrayList;
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

        //ИСПРАВЛЕНИЕ ЗАМЕЧАНИЯ: Добавил проверку списка, если он пуст, то сообщаем об этом и выходим и метода.
        //Т.к. если список пуст, не имеет никаого смысла, что-либо конструировать.
        if(dc.typesAndNamesDishes.isEmpty()){
            System.out.println("Ваш список блюд пуст, добавьте в него блюда используя команду - 1 основного меню.");
            return;
        }

        System.out.println("Начинаем конструировать обед...");
        System.out.println("Введите количество наборов, которые нужно сгенерировать:");
        int numberOfCombos = scanner.nextInt();

        //ИСПРАВЛЕНИЕ ЗАМЕЧАНИЯ: Добавил проверку ввод строго больше 0 кол-ва комбинаций блюд введенных пользователем.
        //Теперь программа будет сообщать, что введено отрицательное значение либо 0.
        if(numberOfCombos <= 0) {
            System.out.printf("Введите количество наборов начиная от 1... вы ввели [%d] \n", numberOfCombos);
            scanner.nextLine(); //используем метод, чтобы избежать пропуска последующего ввода команды из основного меню
            return;
        }
        scanner.nextLine(); //костыль, для nextInt(), чтобы избежать пропуска следующего ввода, вызываем метод nextLine()


        System.out.println("Вводите типы блюда, разделяя символом переноса строки (enter). Для завершения ввода введите пустую строку");
        String nextItem = scanner.nextLine();


        //реализуйте ввод типов блюд
        while (!nextItem.isEmpty()) {
             dc.addTypeDish(nextItem); //метод добавляет Тип в список для генерации, с проверкой на наличии Типа в таблице HashMap
             nextItem = scanner.nextLine(); //Повторяем ввод Типов, пока типы есть в списке или не будет ввода пустой строки
        }
        //Выполняем проверку не пуст ли список указанных типов, если пуст, то уведомляем об этом и прерываем метод.
        if(dc.typesDishes.isEmpty()){
            System.out.println("Вы не ввели ни одного типа блюда... выход в главное меню.");
            return;   //Если не было введено ни одного типа, наш список типов пуст, выходим из метода.
        }

         /*
           for (int i = 0; i < numberOfCombos; i++) {
            System.out.printf("Комбо %d \n", i + 1);
             System.out.println(dc.randomGeneratorDishes()+"\n");
          }
        */

        /*ВНЕС ИЗМЕНЕНИЕ: прошлая логика исполнения заключалась в вызове метода dc.randomGeneratorDishes()
        Метод возвращал сгенерированный список блюд, если заказано выдать 10 комбинаций, то через цикл метод запускался 10 раз.
        Новая реализация будет по принципу вызывая метод dc.RandomGenerationDishes(numberOfCombo), отправляя в метод кол-во комбинаций.
        Получаем готовый список комбо-блюд, количество которых указывал пользователь.
        Т.е. Метод не запускается каждый раз, а будет запущен один раз, получая список всех комбинаций блюд.
        На мой взгляд этот подход интереснее, потому что всю логику будет брать на себя метод генерации возвращая готовый результат.
        Останется только вывести на экран каждую комбинацию блюд из полученного списка.
        */
        ArrayList<ArrayList<String>> generatedComboDishes = dc.randomGeneratorDishes(numberOfCombos);

        for (int i = 0; i < numberOfCombos; i++) {
            System.out.printf("Комбо %d \n", i + 1);
            System.out.println(generatedComboDishes.get(i) + "\n");
        }

        // Очистим список. Чтобы при следующей генерации блюд, список был пуст, для внесения новых Типов блюд.
        dc.typesDishes.clear();
    }
}
