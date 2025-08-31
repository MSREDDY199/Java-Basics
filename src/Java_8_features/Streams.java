package Java_8_features;

import TemporaryClasses.Product;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
    Streams are introduces in Java 8.
    Streams don't store the elements, it simply conveys elements from source to a pipeline of computational operations.
    Operations performed on streams doesn't affect the original source.
    Stream is lazy and evaluates code only when required i.e. after terminal operations only.
    The elements of a stream are only visited once during the life of a stream.

    1. Intermediate operations
        a. map(Function<T, R>), transforms from one form to another form.
        b. filter(Predicate<T>), filters the elements based on specific condition.
        c. flatmap(Function<T, Stream<R>>), Transforms each element into zero or more elements by applying a function that returns a stream for each element.
        d. distinct(), removes the duplicates.
        e. sorted(), sorts all the elements of the stream.
        f. limit(long n), truncates the stream to specified size.
        g. skip(long n), skips the first n elements of the stream.
        h. peek(), Performs a specified action on each element of the stream without consuming the elements.

    2. Terminal operations
       Terminal operations are those operations that consume the Stream and produce a result, such as a value, a collection, or even a side effect.
        a. forEach(), acts as each element of the stream.
        b. collect(), reduces each element of the stream into a mutable result container such as list or map.
        c. reduce(), reduces the elements of the stream to a single value.
        d. count(),returns the count of elements of the stream.
        e. anyMatch(Predicate<T>): Returns true if any element of the Stream matches the given predicate.
        f. allMatch(Predicate<T>): Returns true if all elements of the Stream match the given predicate.
        g. noneMatch(Predicate<T>): Returns true if no elements of the Stream match the given predicate.
        h. findFirst(): Returns an Optional describing the first element of the Stream, or an empty Optional if the Stream is empty(sequential).
            And in parallel streams, it might examine other chunks of data to return the correct one.
        j. findAny(): Returns an Optional describing some element of the Stream, or an empty Optional if the Stream is empty.
            behaves like findFisrt() and returns first element in sequential. Returns any random element in parallel streams and better performance.

    3. Short-Circuit Operations
       These operations doesn't process the entire stream once the condition is met saving computational time.
        a. anyMatch(Predicate<T>): Stops processing and returns true if any element matches the given predicate.
        b. allMatch(Predicate<T>): Stops processing and returns false if any element does not match the given predicate.
        c. noneMatch(Predicate<T>): Stops processing and returns true if no elements match the given predicate.
        d. findFirst(): Returns the first element encountered in the Stream and then stops processing.
        e. findAny(): Returns any element encountered in the Stream and then stops processing.

   ref: https://www.tpointtech.com/java-8-stream
 */
public class Streams {
    public static void main(String[] args) {
        List<Product> productsList = new ArrayList<>();
        //Adding Products
        productsList.add(new Product(1, "HP Laptop", 25000f));
        productsList.add(new Product(2, "Dell Laptop", 30000f));
        productsList.add(new Product(4, "Sony Laptop", 28000f));
        productsList.add(new Product(3, "Lenovo Laptop", 28000f));
        productsList.add(new Product(5, "Apple Laptop", 90000f));

        // INTERMEDIATE OPERATIONS
        // a. map(Function<T, R>)
        // increase the price of each by 10
        List<Product> updatedProducts = productsList.stream().map(product -> new Product(product.id, product.name, product.price + 10)).toList();
        // increase the price of products by 10 which has price of more than 28000
        List<Product> filterAndUpdate = productsList.stream().filter(product -> product.price > 28000f).map(product -> new Product(product.id, product.name, product.price + 10)).toList();


        // b. filter(Predicate<T>)
        // filtering the products who have price greater than 30000
        List<Product> filteredList = productsList.stream().filter(product -> product.price > 30000).toList();

        // c. flatmap(Function<T, Stream<R>>)
        List<List<String>> namesNested = Arrays.asList(Arrays.asList("A", "B"), Arrays.asList("C", "D"), Arrays.asList("E", "F"));
        // Flatten the nested list, output: [A, B, C, D, E, F]
        List<String> flatList = namesNested.stream().flatMap(list -> list.stream()) // flatten
                .toList();

        // d. distinct()
        // Unique products
        List<String> uniqueItems = productsList.stream().map(product -> product.name).distinct().toList();

        // e. sorted()
        // Sort by Multiple Fields (Price then Name)
        List<Product> sortedProducts = productsList.stream().sorted(Comparator.comparing(Product::getPrice).thenComparing(Product::getName)).toList();

        // f. limit()
        // Java Stream Iterating Example
        Stream.iterate(1, element -> element + 1).filter(element -> element % 5 == 0).limit(5).forEach(System.out::print);
        
        // g. skip()
        Stream.iterate(1, element -> element + 1).filter(element -> element % 5 == 0).limit(5).skip(2).forEach(System.out::print);

        // TERMINAL OPERATIONS
        // a. forEach(Consumer<T>)
        productsList.forEach(System.out::println);

        // b. collect(Collector<T, A, R)
        // collecting as list
        List<Float> prices = productsList.stream().filter(product -> product.price > 28000).map(Product::getPrice).collect(Collectors.toList());
        // collecting as Set(removing duplicates)
        Set<Float> uniquePricesList = productsList.stream().map(Product::getPrice).collect(Collectors.toSet());
        // collecting as Map
        Map<Integer, String> products = productsList.stream().collect(Collectors.toMap(Product::getId, Product::getName));
        System.out.println(products);

        // c. reduce(BinaryOperator<T>)
        // total sum of product prices
        Float totalSum = productsList.stream().map(Product::getPrice).reduce(0.0f,Float::sum);

        // d. count()
        // counts the total product prices greater than 28000
        long count = productsList.stream().filter(product -> product.price > 28000).count();

        // e. anyMatch(Predicate<T>)
        // returns true if any of the products have price greater than 28000
        boolean any_match = productsList.stream().anyMatch(product -> product.price == 28000);

        // f. allMatch(Predicate<T>)
        // returns true if all the products price is greater than 28000
        boolean all_match = productsList.stream().anyMatch(product -> product.price > 28000);

        // g. noneMatch(Predicate<T>)
        // returns true if none of the products have price of 28000
        boolean non_match = productsList.stream().noneMatch(product -> product.price == 28000);

        // h. findFirst()
        // return the first product whose price is 28000
        Optional<Product> reqProduct = productsList.stream().filter(product -> product.price == 28000).findFirst();
        System.out.println(reqProduct.isPresent());

        // i. findAny()
        // returns any product that has price of 28000
        Optional<Product> anyProduct = productsList.stream().filter(product -> product.price == 28000).findAny();
        System.out.println(anyProduct);

        // Flatmap transforms each element into a Stream, then flattens all those small streams into one big stream.
        List<String> characters = productsList.stream().map(product -> product.name.split("")).flatMap(Arrays::stream).distinct().toList();

    }
}

/*
Interview questions:
1. What are Java Streams? How are they different from Collections?
-> Streams are used to set the data pipeline, and it doesn't modify the original source of data. Streams can be consumed only once, and they are lazy meaning they run only
    when terminal operations are used.

2. What is the difference between map() and flatMap() in Java Streams?
-> map() takes stream of inputs, applies function on each and produces one output for each, result is still a stream.
   flatMap() is used when the stream has multiple nested streams, it then flattens them to single stream. e.g. List<List<String>>.

3. What is the difference between findFirst(), findAny(), and forEach() in Java Streams?
-> findFirst() returns the first element of the stream (in encounter order if the stream is ordered).
   findAny() returns the any one element from the stream, however it acts like findFirst() in sequential streams whereas in parallel streams it returns a random element.
   forEach(), A terminal operation that applies a given action (Consumer) to each element in the stream.

4. What is the difference between forEach() and forEachOrdered() in Java Streams?
-> forEach() is a terminal operation and consumer that is just used to iterate over the list in ordered way, however in parallel streams order is not maintained.
   forEachOrdered(), order is maintained in parallel streams as well.
   e.g. list.parallelStream().forEachOrdered(System.out::print);

5. What is the difference between reduce() and collect() in Java Streams?
-> reduce() is a terminal operation that combines all elements of a stream into a single value using an associative function.
   collect() is used to convert a stream into a specific data structure like list, set or map etc.

6. How do you group elements of a stream using Collectors.groupingBy()? Can you give an example?
-> Collectors.groupingBy() is a terminal operation used to group elements of a stream based on a classifier function.
   It returns a Map<K, List<V>>, where the key is the result of the classifier function and the value is the list of elements that belong to that key.
   e.g: group the employees by department:
   Map<String, Employee> employees = employeesList.stream().collect(Collectors.groupingBy(Employee::getDepartment), Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))).

7. What is the difference between stream() (sequentialStream) and parallelStream() in Java? When would you use each?
-> Sequential stream is used for smaller datasets, and if order od elements is required. And also it's a thread safe. e.g. list.stream().forEach(System.out::print);
-> Parallel stream is used for larger datasets. It divides the data into smaller multiple chunks and execute them parallel so that the CPU is utilized efficiently.
   And also in parallel streams order is not guaranteed unless forEachOrdered() is used. e.g. list.parallelStream().forEach(System.out::print);

8. Are streams lazy or eager? Give an example.
   -> Streams are lazy, they will get executed only when terminal operations are used.
    List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
    names.stream()
         .filter(n -> {
             System.out.println("Filtering without terminal operations: " + n);
             return n.startsWith("A");
         }); // Nothing prints yet — lazy

    names.stream()
         .filter(n -> {
             System.out.println("Filtering: " + n);
             return n.startsWith("A");
         })
         .forEach(System.out::println);
    // Output:
    // Filtering: Alice
    // Filtering: Bob
    // Filtering: Charlie
    // Alice

9. Can a stream be reused? Why or why not?
    -> No, streams cannot be reused. Once a terminal operation is invoked, the stream is consumed. Trying to reuse it will throw an IllegalStateException.

10. Difference between peek() and map(). When would you use peek()?
    -> map() is used to transform each element into a new element.
    -> peek() doesn't change the original it just consumes. It is mainly used for debugging or observing the elements during the stream pipeline.

11. Explain min(), max(), count() with examples.
-> min(Comparator), returns the minimum element of the stream according to the given comparator. Returns Optional<T> as the stream might be empty.
    e.g. Optional<Integer> min = numbers.stream().min(Integer::compareTo);
-> max(Comparator), returns the larger element of the stream according to the given comparator. Returns Optional<T> as the stream might be empty.
    e.g. Optional<Integer> max = numbers.stream().max(Integer::compareTo);
-> count(), returns the no.of elements in the stream. Returns as long.
    long count = numbers.stream().count();

12. Explain the concept of stateful vs stateless intermediate operations.
->  Stateless Intermediate Operations, Do not depend on any state from previously seen elements. Each element is processed independently. Can be easily parallelized.
    Examples: map(), filter(), peek().
->  Stateful Intermediate Operations, Depends on previously seen elements. Can require storing data internally (memory overhead).
    Examples: sorted(), distinct(), limit(), skip().

13. What happens if you modify a collection while streaming it?
->  Modifying a collection while streaming it is unsafe. The behavior is undefined, which means it may throw an exception or produce inconsistent results.
    To be safe, either use a copy of the collection or use concurrent collections like CopyOnWriteArrayList.
    e.g. list.stream().forEach(n -> {
            list.add(4); // Structural modification during stream
         });

14. How does collect(Collectors.toList()) work internally?
->  Internally, it defines four main functions:
    Step 1: Supplier creates new ArrayList<>()
    Step 2: Accumulator adds each element to the list (add())
    Step 3: Combiner merges lists if the stream is parallel
    Step 4: Returns the final list

15. Is a stream ordered? How to handle unordered data?
->  Streams can be ordered or unordered depending on the source.
->  Ordered streams preserve encounter order.
->  Unordered streams may improve parallel stream performance.
->  Use forEachOrdered() if you need ordered results in parallel streams.
 */
