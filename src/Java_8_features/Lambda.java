package Java_8_features;

import TemporaryClasses.Product;
import TemporaryClasses.Sayable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/*
    Lambda function is introduced in Java 8. It provides more concise way to implement interfaces that are functional interfaces. It saves a lot of code.
    Advantages:
    1. It's concise syntax gives more readability and maintainability.
    2. It supports the usability of functional interfaces and provides tools like map, filter and reduce.
    3. They make it simple to do concurrent operations on collections using the Streams API.

    Disadvantages:
    1. Only functional interfaces, which contain a single abstract method, are compatible with lambda expressions.
    2. Lambda expressions are useful for short and straightforward processes, but when applied to more complicated reasoning, they may become less readable.
    3. Lambda expressions can be difficult to debug, particularly if they are intricate or include several levels of abstraction.
 */

public class Lambda {
    public static void main(String[] args) {
        int width = 10;

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // for each loop
        numbers.forEach(System.out::println);

        // calculating sum of squares
        int sumOfSquares = numbers.stream().map(num -> num * num).reduce(0, Integer::sum);
        System.out.println("sum of all squares: " + sumOfSquares);

        // using filter to find all even numbers
        List<Integer> evenNumbers = numbers.stream().filter(num -> num % 2 == 0).toList();
        System.out.println("even numbers: " + evenNumbers);

        // Lambda expression with no parameters
//        Sayable s = () -> {
//            return "I have nothing to say.";
//        };
//        System.out.println(s.say());

        // Lambda expression with single parameter
//        Sayable single = (name) -> {
//            return "name: "+name;
//        };
//        System.out.println(single.say("Shanmukh"));

        // Lambda expression with multiple parameters
//        Sayable multiple = ((firstname, lastname) -> {
//            return "full name: " + firstname + " " + lastname;
//        });
//        System.out.println(multiple.say("Shanmukh", "Reddy"));

        // Lambda expression for creating threads
        Runnable runnable = () -> {
            System.out.println("Thread is running: " + Thread.currentThread().getName());
        };
        Thread t2 = new Thread(runnable);
        t2.start();

        // Lambda expression comparator
        List<Product> list = new ArrayList<>();
        list.add(new Product(1, "HP Laptop", 25000f));
        list.add(new Product(3, "Keyboard", 300f));
        list.add(new Product(2, "Dell Mouse", 150f));

        System.out.println("Sorting on the basis of name...");

        list.sort((p1, p2) -> {
            return p1.name.compareTo(p2.name);
        });
        list.forEach(product -> System.out.println(product.name));

        // Filter Collection Data
        List<Product> list2 = new ArrayList<>();
        list2.add(new Product(1, "Samsung A5", 17000f));
        list2.add(new Product(3, "Iphone 6S", 65000f));
        list2.add(new Product(2, "Sony Xperia", 25000f));
        list2.add(new Product(4, "Nokia Lumia", 15000f));
        list2.add(new Product(5, "Redmi4 ", 26000f));
        list2.add(new Product(6, "Lenevo Vibe", 19000f));

        // filtering data based on price
        // we're using Stream class instead of List, we can also use List and at the end use toList() method
        Stream<Product> newList = list2.stream().filter(product -> product.price > 26000f);



    }
}
