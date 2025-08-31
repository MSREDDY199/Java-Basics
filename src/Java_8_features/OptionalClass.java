package Java_8_features;
import java.util.Optional;

/*
    Java introduced a new class Optional in JDK 8 version. It is a public final class and used to deal with NullPointerException in Java application.
    It provides methods that are used to check the presence of value for particular variable.


 */
public class OptionalClass {
    public static void main(String[] args) {
        String strs[] = new String[10];
        Optional<String> checkNull = Optional.ofNullable(strs[5]);
        // check for value is present or not
        System.out.println(checkNull.isPresent() ? strs[1].length(): 0);
        // printing value by using get method
        strs[5] = "JAVA OPTIONAL CLASS EXAMPLE";
        checkNull = Optional.ofNullable(strs[5]);
        System.out.println(checkNull.get());

        // Using Alternate Values if Value is not Present: orElse() and orElseGet()
        String userName = getDefaultUserName();
        Optional<String> optionalName = Optional.of(userName);
        // Using orElse() to provide a default name
        String orElseName = optionalName.orElse("Default Name");
        System.out.println("orElseName: "+orElseName);
        // Using orElseGet() to provide a default name using supplier like method or anthing else
        String orElseGet = optionalName.orElseGet(OptionalClass::getDefaultUserName);
        System.out.println("orElseGet: "+orElseGet);

        // Optional Value Transformation With map() and flatMap()
        Optional<String> nameOptional = Optional.of("john doe");
        Optional<String> upperName = nameOptional.map(String::toUpperCase); // Use map to convert the name to uppercase
        upperName.ifPresent(System.out::println); // Print the transformed name if it is present, Output: JOHN DOE
        // flat map
        Optional<Integer> nameLength = nameOptional.flatMap(name -> Optional.of(name.length()));
        // Print the length if present
        nameLength.ifPresent(length -> System.out.println("Name length: " + length)); // Output: Name length: 8

    }

    private static String getUserNameFromDatabase() {
        // return null to simulate the absence of a user
        return null;
    }
    // A method that provides a default username
    private static String getDefaultUserName() {
        // Simulate some expensive computation or fetching operation
        System.out.println("Computing default username...");
        return "Computed Default User";
    }
}
