package HomeWork.FourthTask.StreamAPI;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Author autor0 = new Author("First",
                LocalDate.of(1964, 12, 30),
                null,
                true);
        Author autor1 = new Author("Second",
                LocalDate.of(1935, 3, 2),
                null,
                false);
        Author autor2 = new Author("Third",
                LocalDate.of(1983, 4, 24),
                null,
                true);
        Author autor3 = new Author("Forth",
                LocalDate.of(1921, 7, 15),
                LocalDate.of(1986, 12, 21),
                false);
        Author autor4 = new Author("Fifth",
                LocalDate.of(1901, 11, 5),
                LocalDate.of(1952, 2, 15),
                false);
        Author autor5 = new Author("Sixth",
                LocalDate.of(1865, 1, 12),
                LocalDate.of(1941, 9, 25),
                true);
        Author autor6 = new Author("Sevens",
                LocalDate.of(1855, 9, 26),
                LocalDate.of(1926, 4, 12),
                true);
        Author autor7 = new Author("Eights",
                LocalDate.of(1961, 3, 31),
                null,
                false);

        List<Author> authors = new ArrayList<>();
        authors.add(autor0);
        authors.add(autor1);
        authors.add(autor2);
        authors.add(autor3);
        authors.add(autor4);
        authors.add(autor5);
        authors.add(autor6);
        authors.add(autor7);

        List<Author> authorsForBookOne = new ArrayList<>();
        authorsForBookOne.add(autor0);
        authorsForBookOne.add(autor1);
        authorsForBookOne.add(autor5);
        Book book0 = new Book("Book One", 1990, authorsForBookOne);

        List<Author> authorsForBookTwo = new ArrayList<>();
        authorsForBookTwo.add(autor2);
        Book book1 = new Book("Book Two", 2010, authorsForBookTwo);

        List<Author> authorsForBookThree = new ArrayList<>();
        authorsForBookThree.add(autor0);
        Book book2 = new Book("Book Three", 1995, authorsForBookThree);

        List<Author> authorsForBookFour = new ArrayList<>();
        authorsForBookFour.add(autor7);
        authorsForBookFour.add(autor1);
        Book book3 = new Book("Book Four", 1992, authorsForBookFour);

        Book book4 = new Book("Book Five", 2012, authorsForBookTwo);

        List<Author> authorsForBookSix = new ArrayList<>();
        authorsForBookSix.add(autor4);
        Book book5 = new Book("Book Six", 1945, authorsForBookSix);

        List<Author> authorsForBookSeven = new ArrayList<>();
        authorsForBookSeven.add(autor3);
        Book book6 = new Book("Book Seven", 1940, authorsForBookSeven);

        List<Author> authorsForBookEight = new ArrayList<>();
        authorsForBookEight.add(autor7);
        Book book7 = new Book("Book Eight", 1987, authorsForBookEight);

        List<Author> authorsForBookNine = new ArrayList<>();
        authorsForBookNine.add(autor5);
        authorsForBookNine.add(autor6);
        Book book8 = new Book("Book Nine", 1893, authorsForBookNine);

        List<Author> authorsForBookTen = new ArrayList<>();
        authorsForBookTen.add(autor4);
        authorsForBookTen.add(autor5);
        Book book9 = new Book("Book Ten", 1922, authorsForBookTen);

        List<Book> books = new ArrayList<>();
        books.add(book0);
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);
        books.add(book7);
        books.add(book8);
        books.add(book9);

        //посчитать средний возраст живых авторов
        double averageAge1 = authors.stream()
                .filter(x -> x.getDateOfDeath() == null)
                .mapToInt(x -> Author.getFullYears(LocalDate.now(), x.getDateOfBirth()))
                .average()
                .getAsDouble();

        //посчитать средний возраст уже умерших авторов на момент смерти
        double averageAge2 = authors.stream()
                .filter(x -> x.getDateOfDeath() != null)
                .mapToInt(x -> Author.getFullYears(x.getDateOfDeath(), x.getDateOfBirth()))
                .average()
                .getAsDouble();

        //посчитать средний возраст всех авторов(возраст мертвых берется на момент смерти)
        double averageAge3 = authors.stream()
                .mapToInt(x -> (Author.getFullYears(x.getDateOfDeath() == null ?
                        LocalDate.now() : x.getDateOfDeath(), x.getDateOfBirth())))
                .average()
                .getAsDouble();

        System.out.println("\n" + "Средний возраст живых авторов: \n" + averageAge1 + "\n");
        System.out.println("Средний возраст уже умерших авторов на момент смерти: \n" + averageAge2 + "\n");
        System.out.println("Средний возраст всех авторов(возраст мертвых берется на момент смерти): \n"
                + averageAge3 + "\n");

        //отсортировать авторов по возрасту по возрастанию
        List<Author> sortedAuthors = authors.stream()
                .sorted(Comparator.comparingInt(x -> (Author.getFullYears(x.getDateOfDeath() == null ?
                        LocalDate.now() : x.getDateOfDeath(), x.getDateOfBirth()))))
                .collect(Collectors.toList());

        System.out.println("Сортировка авторов по возрасту: ");

        for(Author elem: sortedAuthors){
            System.out.println(elem);
        }

        //вывести список авторов пенсионеров на 2017 год (считаем по ТК РФ, >65 лет для мужчин, >63 лет для женщин)
        LocalDate twentySeventeen = LocalDate.of(2017, 1, 1);

        List<Author> oldAuthors = authors.stream()
                .filter(x -> x.getDateOfDeath() == null)
                .filter(x -> x.isMale() ?
                        (Author.getFullYears(twentySeventeen, x.getDateOfBirth()) >= 65) : (Author.getFullYears(twentySeventeen, x.getDateOfBirth()) >= 63))
                .collect(Collectors.toList());

        System.out.println("\nАвторы пенсионеры на 2017 год: ");

        for(Author elem: oldAuthors){
            System.out.println(elem);
        }

        //для всех книг вывести пары - (название книги, сколько ей лет на текущий момент)
        List<String> bookPlusAge = books.stream()
                .map(x -> "Title: " + x.getTitle() + ". Age: " + (LocalDate.now().getYear() - x.getPublicationYear()))
                .collect(Collectors.toList());

        System.out.println("\nНазвание книги, сколько ей лет на текущий момент: ");

        for(String elem: bookPlusAge){
            System.out.println(elem);
        }

        //получить список всех авторов, писавших книгу в соавторстве с другим автором
        List<Author> coAutorsList = books.stream()
                .filter(y -> y.getAuthors().size() > 1)
                .flatMap(x -> x.getAuthors().stream())
                .distinct()
                .collect(Collectors.toList());

        System.out.println("\nСписок всех авторов, писавших книгу в соавторстве с другим автором: ");

        for (Author elem: coAutorsList) {
            System.out.println(elem);
        }

        //собрать пары (имя автора, названия книг, которые он написал)
        List<String> authorsAndBooks = authors.stream()
                .map(x -> (x.getName() + ": " + books.stream()
                        .filter(y -> y.getAuthors().contains(x))
                        .map(y -> y.getTitle())
                        .reduce((t, r) -> t + ", " + r)
                        .get()))
                .collect(Collectors.toList());

        System.out.println("\nАвтор и написанные им книги: ");

        for(String elem: authorsAndBooks){
            System.out.println(elem);
        }

        //собственный анализ, например с применением Stream Statistics
        IntStream intStream = authors.stream()
                .mapToInt(x -> (Author.getFullYears(x.getDateOfDeath() == null ?
                        LocalDate.now() : x.getDateOfDeath(), x.getDateOfBirth())));
        IntSummaryStatistics intSumSt = intStream.collect(IntSummaryStatistics::new,
                IntSummaryStatistics::accept,
                IntSummaryStatistics::combine);

        System.out.println("\nСобственный анализ: ");
        System.out.printf("%-28s: %.1f\n", "Средний возраст всех авторов", intSumSt.getAverage());
        System.out.printf("%-28s: %d\n", "Колличество авторов", intSumSt.getCount());
        System.out.printf("%-28s: %d\n", "Максимальный возраст автора", intSumSt.getMax());
        System.out.printf("%-28s: %d\n", "Минимальный возраст автора", intSumSt.getMin());
        System.out.printf("%-28s: %d\n", "Общий возраст авторов", intSumSt.getSum());

        DoubleStream doubleStream = books.stream()
                .mapToDouble(x -> x.getAuthors().size());
        DoubleSummaryStatistics doubleSumSt = doubleStream.collect(DoubleSummaryStatistics::new,
                DoubleSummaryStatistics::accept,
                DoubleSummaryStatistics::combine);

        System.out.println("\nСобственный анализ: ");
        System.out.printf("%-40s: %.2f\n", "Среднее колличество авторов на книгу",doubleSumSt.getAverage());
        System.out.printf("%-40s: %d\n", "Количество книг", doubleSumSt.getCount());
        System.out.printf("%-40s: %.2f\n", "Максимальное количество авторов на книгу", doubleSumSt.getMax());
        System.out.printf("%-40s: %.2f\n", "Минимальное количество авторов на книгу", doubleSumSt.getMin());
    }
}
