package HomeWork.FirstTask;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        /**
         * Tests for first homework
         */
        Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 3}; //массив элементов
        Integer[] array = {1, 2, 3}; //массив предикатов
        Predicate predicate = new Predicate(array);
        FilterList<Integer> filterList = new FilterList<>(intArray, predicate);

        Iterator<Integer> iter = filterList.iterator();

        System.out.println("Вывод элементов массива итератором\n");

        while(iter.hasNext()){
            Integer a = iter.next();
            System.out.print(a + " ");
        }
        System.out.print("\nsize: " + filterList.size());

        System.out.println("\n\nПробуем добавить 5 элементов, 3 из которых находятся в списке предикатов\n");

        filterList.add(1); //не добавится
        filterList.add(2); //не добавится
        filterList.add(3); //не добавится
        filterList.add(4); //добавится
        filterList.add(7); //добавится

        Iterator<Integer> iter2 = filterList.iterator();

        while(iter2.hasNext()){
            Integer a = iter2.next();
            System.out.print(a + " ");
        }
        System.out.print("\nsize: " + filterList.size());

        System.out.println("\n\nПробуем удалить элементы по индексу. Все 3 элемента находятся в списке предикатов\n");

        try {
            filterList.remove(0); //не удалится
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            filterList.remove(1); //не удалится
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            filterList.remove(2); //не удалится
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Iterator<Integer> iter3 = filterList.iterator();

        while(iter3.hasNext()){
            Integer a = iter3.next();
            System.out.print(a + " ");
        }

        System.out.print("\nsize: " + filterList.size());

        System.out.println("\n\nУдаляем 3 элемента с конца. Один из них в списке предикатов\n");

        filterList.remove(9); //удалится
        filterList.remove(8); //удалится
        try {
            filterList.remove(7); //не удалится
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Iterator<Integer> iter4 = filterList.iterator();

        while(iter4.hasNext()){
            Integer a = iter4.next();
            System.out.print(a + " ");
        }

        System.out.print("\nsize: " + filterList.size());

        System.out.println("\n\nУдаляем 3 элемента по значению. Один из них в списке предикатов\n");

        filterList.remove(Integer.valueOf(3)); //не удалится
        filterList.remove(Integer.valueOf(4)); //удалится
        filterList.remove(Integer.valueOf(7)); //удалится

        Iterator<Integer> iter5 = filterList.iterator();
        while(iter5.hasNext()){
            Integer a = iter5.next();
            System.out.print(a + " ");
        }

        System.out.print("\nsize: " + filterList.size()+ "\n");

        try {
            FilterList<Integer> filterList2 = new FilterList<>(null);
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }

        try {
            FilterList<Integer> filterList3 = new FilterList<>(-5);
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }

        try {
            FilterList<Integer> filterList4 = new FilterList<>(null, predicate);
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }

        /**
         * Tests for second homework
         */
        String[] strArray = {"a", "b", "c", "d", "e"};
        String[] emptyStringArray = {};
        Predicate predicate2 = new Predicate(emptyStringArray);
        FilterList<String> filterList01 = new FilterList<>(strArray, predicate2);

        FilterList<?> str = filterList01.map(new Maper<String, String>() {
            @Override
            public String apply(String o) {
                return o + ":_:>>!";
            }
        });

        Iterator<?> strIter = str.iterator();

        while(strIter.hasNext()) {
            Object ob = strIter.next();
            System.out.println(ob);
        }

        Integer[] ints = {1, 2, 3, 4, 5};
        Integer[] emptyIntsArray = {};
        Predicate predicate3 = new Predicate(emptyIntsArray);
        FilterList<Integer> filterList02 = new FilterList<>(ints, predicate3);

        Integer int0 = filterList02.reduce(new Reducer<Integer>() {
            @Override
            public Integer apply(Integer o, Integer y) {
                return o + y;
            }
        });

        System.out.println(int0);

    }
}
