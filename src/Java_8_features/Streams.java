package Java_8_features;

import TemporaryClasses.Customer;
import TemporaryClasses.Employee;
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

        // Interview questions
        // filter() + map()
        // 1. Given a list of strings, filter out empty strings.
        List<String> words = Arrays.asList("apple", "", "banana", " ", "cherry", "", "date");
        List<String> result = words.stream().filter(word -> !word.isEmpty()).toList();

        // 2. Convert all strings in a list to uppercase except those starting with "a".
        List<String> uppercase = words.stream().map(word -> word.startsWith("a") ? word : word.toUpperCase()).toList();

        // 3. Find the distinct numbers from a list and sort them in ascending order.
        List<Integer> numbers = Arrays.asList(2, 1, 8, 5, 1);
        List<Integer> distinct = numbers.stream().distinct().sorted().toList();

        // 4. Find the top 3 highest numbers in a list.
        List<Integer> highest = numbers.stream().distinct().sorted(Comparator.reverseOrder()).limit(3).toList();

        // 5. Find the 2nd smallest element in a list.
        Optional<Integer> second = numbers.stream().distinct().sorted().skip(1).findFirst();

        // 6. Find the sum of all integers in a list.
        int sum = numbers.stream().reduce(0, Integer::sum);

        // 7. Find the product of all integers in a list.
        int product = numbers.stream().reduce(1, (a, b) -> a * b);

        // 8. Find the longest string using reduce().
        Optional<String> longest = words.stream().reduce((w1, w2) -> w1.length() >= w2.length() ? w1 : w2);

        // 9. Find the employee with the highest salary using reduce().
        List<Employee> employees = List.of(
                new Employee("Alice", 60000),
                new Employee("Bob", 45000),
                new Employee("Charlie", 75000)
        );
        Optional<Employee> highestSalary = employees.stream().reduce((e1, e2) -> e1.getSalary() > e2.getSalary() ? e1 : e2);

        // 10. Convert a list of strings to a Set.
        Set<String> stringList = words.stream().collect(Collectors.toSet());

        // 11. Convert a list of employees into a map of id -> name.
        Map<String, Double> employeeList = employees.stream().collect(Collectors.toMap(Employee::getName, Employee::getSalary));

        // 12. Find the frequency of words in a string.

        // 13. Group employees by department.
//        Map<String, List<Employee>> dept = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));

        // 14. Partition numbers into odd and even.

        // 15. Join a list of strings into a single comma-separated string.

        // 16. Find the minimum and maximum element in a list.
        Optional<Integer> maximum = numbers.stream().max(Comparator.naturalOrder());
        Optional<Integer> min = numbers.stream().min(Comparator.naturalOrder());

        // 17. Find any element from a parallel stream.
        Optional<Integer> randomNumber = numbers.parallelStream().findAny();

        // 18. Count the number of strings longer than 5 characters.
        long numStrings = words.stream().filter(word -> word.length() > 5).count();

        // 19. Flatten a list of lists of integers into a single list.
        List<List<Integer>> listOfLists = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5), Arrays.asList(6, 7, 8, 9));
        List<Integer> intList = listOfLists.stream().flatMap(list -> list.stream()).toList();

        // 20. From a list of sentences, extract all words into a list.
        List<String> sentences = Arrays.asList("Java is fun", "Streams are powerful", "I love coding");
        List<String> allWords = sentences.stream().flatMap(sentence -> Arrays.stream(sentence.split(" "))).toList();

        // 21. Given a list of customers, each having multiple phone numbers, get all phone numbers.
        List<Customer> customers = List.of(new Customer("Alice", List.of("123", "456")), new Customer("Bob", List.of("789")), new Customer("Charlie", List.of("101", "112")));
        List<String> phoneNums = customers.stream().flatMap(customer -> customer.getPhoneNumbers().stream()).toList();

        // 22. Find duplicate elements in a list.
        Set<Integer> seen  = new HashSet<>();
        Set<Integer> duplicates = numbers.stream().filter(num -> !seen.add(num)).collect(Collectors.toSet());

        // 23. Find common elements between two lists.
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(3, 4, 5, 6, 7);
        Set<Integer> set1 = new HashSet<>(list1);
        Set<Integer> common = list2.stream().filter(set1::contains).collect(Collectors.toSet());

        // 24. Check if all elements in a list are even.
        boolean match = numbers.stream().allMatch(num -> num % 2 == 0);

        // 25. Remove null values from a list.
        List<Integer> nonNull = numbers.stream().filter(Objects::nonNull).toList();

        // 26. FInd the Kth largest element
        int largest = numbers.stream().sorted(Comparator.reverseOrder()).skip(2).findFirst().orElse(-1);

        // 27. Convert a list of objects to a CSV string.
        // Collectors.joining(", ") → combines all elements into a single string with commas
        String csv = employees.stream().map(Employee::getName).collect(Collectors.joining(", "));

        List<Employee> employeesList = List.of(
                new Employee("Alice", 60000, "Physics", 28),
                new Employee("Bob", 45000, "Physics", 28),
                new Employee("Charlie", 75000, "Maths", 29),
                new Employee("David", 75000, "Maths", 31),
                new Employee("Justin", 75000, "English", 30),
                new Employee("Justin", 75000, "English", 30)
        );

        // Employee based questions
        // 1. Sort employees by salary (descending)
        List<Employee> desc_list = employeesList.stream().sorted(Comparator.comparingDouble(Employee::getSalary)).toList();

        // 2. Sort by age, then by name
        List<Employee> sort_age = employeesList.stream().sorted(Comparator.comparing(Employee::getAge).thenComparing(Employee::getName)).toList();

        // Grouping with Streams
        // 3. Group by age
        Map<Integer, List<Employee>> group_age = employeesList.stream().collect(Collectors.groupingBy(Employee::getAge));

        // 4. Group by age, count employees
        Map<Integer, Long> group_age_count = employeesList.stream().collect(Collectors.groupingBy(Employee::getAge, Collectors.counting()));

        // 5. Group by age, find max salary in each group
        Map<Double, Optional<Employee>> group_age_max_salary = employeesList.stream().collect(Collectors.groupingBy(Employee::getSalary, Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))));

        // 6. Find 2nd highest salary
        Optional<Employee> sec_high_salary = employeesList.stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).skip(1).findFirst();

        // 7. Get all names as a comma-separated string
        String comma_sep_names = employeesList.stream().map(Employee::getName).collect(Collectors.joining(", "));

        // 8. Partition employees into two groups: salary > 50k and <= 50k
        Map<Boolean, List<Employee>> salary_employees = employeesList.stream().collect(Collectors.partitioningBy(emp -> emp.getSalary() > 50000));

        // 9. Find the employee with max salary
        Optional<Employee> max_salary_emp = employeesList.stream().max(Comparator.comparingDouble(Employee::getSalary));

        // 10. Group by Age and Get Average Salary in Each Group
        Map<Double, Double> age_avg_salary = employeesList.stream().collect(Collectors.groupingBy(Employee::getSalary, Collectors.averagingDouble(Employee::getSalary)));

        // 11. Group by Age and List Names Only
        Map<Integer, List<String>> name_list = employeesList.stream().collect(Collectors.groupingBy(Employee::getAge, Collectors.mapping(Employee::getName, Collectors.toList())));

        // 12. Check if All Employees Have Salary > 30k
        boolean all_emps_salary = employeesList.stream().allMatch(emp -> emp.getSalary() > 30000);

        // 13. Check if Any Employee’s Name Starts with 'A'
        boolean emp_with_A = employeesList.stream().anyMatch(emp -> emp.getName().startsWith("A"));

        // 14. Find Oldest Employee
        Optional<Employee> old_emp = employeesList.stream().max(Comparator.comparing(Employee::getAge));

        // 15. Create Map of Name → Salary
        Map<String, Double> name_salary_map = employeesList.stream().collect(Collectors.toMap(Employee::getName, Employee::getSalary));

        // 16. Remove Duplicate Employees (based on name)
        Set<String> emps = new HashSet<>(employeesList.stream().map(Employee::getName).toList());
        List<Employee> duplicate_empls = employeesList.stream().filter(emp -> !emps.contains(emp.getName())).toList();
        System.out.println(duplicate_empls);
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
