package ru.practicum.dinner;
//импортируем классы HashMap,ArrayList,Random.
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class DinnerConstructor {
    //Создаем таблицу, typesAndNamesDishes для хранения списка блюд, где ключ будет - Тип блюда, а значение - Название.
    //Создаем список typesDishes для хранения вводимых пользователем Типов блюд, эти типы будут ключами, и использоваться в методе
    // рандомной генерации блюд по этим ключам(типам)
    HashMap<String,ArrayList<String> > typesAndNamesDishes = new HashMap<>();
    ArrayList<String> typesDishes = new ArrayList<>();
    Random rnd = new Random(); //ИСПРАВЛЕНИЕ ЗАМЕЧАНИЯ: Добавим поле rnd как экземпляр класса Random, по ТЗ.

    //Этот метод будет добавлять типы и названия блюд в таблицу. Перед этим будем проверять, есть такое название блюда
    //в таблице. Если такое блюдо уже есть, уведомляем об этом и просим ввести другое.
    //Пример: В группе Мясо уже вносили котлеты, если будет попытка снова внести котлеты, уведомим о повторном блюде.
    void addDish(String typeDish, String nameDish){
        if(checkName(typeDish, nameDish)){ // исп. метод для проверки наличия блюда в этом типе.
            System.out.printf("Такое блюдо %s уже присутствует в списке. Введите другое блюдо. \n", nameDish);
            return;
        }

        if(checkType(typeDish)){
            typesAndNamesDishes.get(typeDish).add(nameDish);
            System.out.printf("Блюдо %s успешно добавлено в группу блюд под названием [%s] \n", nameDish, typeDish);
        }else {
            ArrayList<String> name = new ArrayList<>();
            name.add(nameDish);
            typesAndNamesDishes.put(typeDish, name);
            System.out.printf("Блюдо %s успешно добавлено в группу блюд под названием [%s] \n", nameDish, typeDish);
        }

    }


    //Добавим метод для отображения списка блюд, удобно если не знаешь какие типы и список блюд есть.
    //ДОРАБОТАНО: добавлена проверка списка, если он пуст, то нечего отображать, просто выходим из метода с уведомлением.
    void printDish(){
        if (typesAndNamesDishes.isEmpty()){
            System.out.println("Ваш список блюд пуст, добавьте в него блюда используя команду - 1 основного меню.");
            return;
        }
        for (String typeDish : typesAndNamesDishes.keySet()) { //Перебираем ключи таблицы Типов и Названий блюд.
                System.out.printf("[%s] \n", typeDish); // Выводим тип(группу) блюд.
                System.out.println(typesAndNamesDishes.get(typeDish)+ "\n"); //Выводим ArrayList список названий блюд в группе.

        }
    }


    //Метод для добавления типов блюд в список typesDishes, т.к. Типы введенные пользователем будут добавляться через цикл,
    // каждый раз метод будет добавлять тип в список, при этом предварительно проверяя, есть ли такой тип в таблице HashMap?
    void addTypeDish(String typeDish){
        //Проверяем есть ли такой тип в списке блюд.
        if(checkType(typeDish)){ //Исп. метод checkType мы делаем декомпозицию, разбиваем код на логические блоки.
            typesDishes.add(typeDish); //Такой тип есть, значит добавляем Тип блюда в список Типов, для генерации.
        }else {
            System.out.println("Такой тип блюда отсутствует в списке ¯\\_(ツ)_/¯. Пожалуйста подтворите ввод, " +
                    "или нажмите Enter, для завершения ввода. ");
        }

    }



/*
    //Метод для генерации рандомных списков блюд, используя список Типов typesDishes
    ArrayList<String> randomGeneratorDishes () {
        //Создаем список для хранения блюд
        ArrayList<String> namesDishes = new ArrayList<>();
        for (String typeDish : typesDishes) {
            int size = typesAndNamesDishes.get(typeDish).size(); //Записываем размер полученного списка по Типу(ключу)
            int id = rnd.nextInt(size); //Используем полученный размер, для получения рандомного не выше размера списка
            namesDishes.add(typesAndNamesDishes.get(typeDish).get(id));
            //Записываем в список рандомное блюдо, которое получаем так -> получаем список по ключу(типу).
            // Получаем элемент из списка по рандомному индексу.
            // Повторяем операцию со всеми типами пользователя.
        }
        return namesDishes;
    }
*/

    //Метод для генерации рандомных списков блюд, используя список Типов typesDishes
    /*ВНЕС ИЗМЕНЕНИЕ: метод уже будет возвращать сразу все необходимое кол-во комбинаций блюд.
      Причина изменения описана в методе generateDishCombo() класса Main, перед вызовом данного метода.
    */
    ArrayList<ArrayList<String>> randomGeneratorDishes(int numberOfCombo) {
        ArrayList<ArrayList<String>> comboDishes = new ArrayList<>(); //итоговый список комбинаций блюд.
        ArrayList<String> rndDishes = new ArrayList<>(); //список для хранения блюд каждой комбинаций по кол-ву типов блюд
        for (int i = 0; i < numberOfCombo; i++) {
            for (String typesDish : typesDishes) {
                int size = typesAndNamesDishes.get(typesDish).size();
                int id = rnd.nextInt(size); //ИСПРАВЛЕНИЕ ЗАМЕЧАНИЯ использовал переменную экземпляра класса Random rnd.
                rndDishes.add(typesAndNamesDishes.get(typesDish).get(id)); //достаем рандомное блюдо из таблицы по типу(ключу)
                                                                          //и кладем в список рандомных блюд.
            }
            comboDishes.add(rndDishes); //полученный спиок рандомных блюд кладем в итоговый список.
            rndDishes = new ArrayList<>(); //создадим новый объект списка, чтобы записать в него новую комбинацию.
        }
        return comboDishes; //Возвращаем итоговый список.
    }


    //Метод для проверки введенного пользователем Типа блюда. Проверяем есть ли такой в списке.
    boolean checkType(String typeDish){
        return typesAndNamesDishes.containsKey(typeDish);
    }

    //Метод для проверки введенного пользователем названия блюда в пункте 1 меню.
    //Если блюдо уже имеется в таблице, например сосиски в типе Мясо уже есть, тогда добавлять их уже не будем.
    boolean checkName(String typeDish, String nameDish){
        boolean checkName = false;
        if (checkType(typeDish)) {
            return  typesAndNamesDishes.get(typeDish).contains(nameDish);
        }
        return checkName;
    }



}


