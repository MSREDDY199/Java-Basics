package Java_8_features;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.*;

/*
    An interface that contains only one abstract method is known as functional interface. It can have any number of default, static methods but can contain only one abstract method.
    The @FunctionalInterface annotation is optional but recommended (it enforces the single abstract method rule at compile time).

    1. Predicate<T>, A boolean-valued function of one argument. Used for Filtering, conditions.
    2. Function<T, R>, A function that takes input T and returns result R. Used for Mapping, transformations.
    3. Consumer<T>, Performs an action on a given input but returns nothing. Used for Printing, logging, updating.
    4. Supplier<T>, A supplier of results (no input, only output). Used for Lazy initialization, generating values.
    5. BiFunction<T, U, R>, A function takes two input and produces a result.

 */
public class FunctionalInterfaces {
    public static void main(String[] args) {

        // Predicate
        Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println(isEven.test(4)); // true
        System.out.println(isEven.test(5)); // false
        // Filtering a list
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        numbers.stream().filter(isEven).forEach(System.out::println); // prints 2,4,6

        // Function
        Function<String, Integer> lengthFunc = str -> str.length();
        System.out.println(lengthFunc.apply("Shanmukh")); // 8
        System.out.println(lengthFunc.apply("Java"));

        // Consumer
        Consumer<String> printUpper = s -> System.out.println(s.toUpperCase());
        printUpper.accept("java");   // JAVA
        printUpper.accept("lambda"); // LAMBDA

        // Supplier
        Supplier<Integer> randomSupplier = () -> new Random().nextInt(100);
        System.out.println(randomSupplier.get());
        System.out.println(randomSupplier.get());

        // Bi Function
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println(add.apply(10, 20)); // 30
    }
}
