package Java_8_features;

import TemporaryClasses.Message;
import TemporaryClasses.Messageable;
import TemporaryClasses.Sayable;

/*
    Method reference is used to refer method of functional interface. It is compact and easy form of lambda expression.
    Each time when you are using lambda expression to just referring a method, you can replace your lambda expression with method reference.

    There are following types of method references in java:
        1. Reference to a static method.
        2. Reference to an instance method.
        3. Reference to a constructor.


 */
public class MethodReferences {

    // 1. static method
    public static void saySomething(){
        System.out.println("Hello, this is static method.");
    }

    // 2. non-static method
    public void saySomethingElse(){
        System.out.println("Hello, this is non-static method.");
    }

    public static void main(String[] args) {
        // Referring static method
        Sayable sayable = MethodReferences::saySomething;
        // Calling interface method
        sayable.say();

        MethodReferences methodReference = new MethodReferences(); // Creating object
        // Referring non-static method using reference
        Sayable instanceMethodRef = methodReference::saySomethingElse;
        // Calling interface method
        instanceMethodRef.say();

        // Reference to a Constructor, works only with functional interfaces
        Messageable hello = Message::new;
        hello.getMessage("Hello");
    }


}
