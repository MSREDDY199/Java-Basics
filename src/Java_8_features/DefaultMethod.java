package Java_8_features;

/*
    -> In Java, default methods were introduced in Java 8. Default methods are the methods that are defined in an interface with concrete implementation.
    -> It allows us to define method bodies directly inside interfaces. These methods are non-abstract methods.
    -> Prior to Java 8, only abstract methods were allowed in the interface. It means If you add a new method to an interface, all implementing classes break (must implement it) and
        Default methods let you add new methods with a default implementation without breaking existing code.

    eg 1:
    In the below example when C implements A, B there will be an ambiguity. To resolve this ambiguity, C overrides the show() method and explicitly calls one of the
    super-interface versions using A.super.show() or B.super.show().

    Difference b/w Default vs Static Methods in Interfaces:
        Default methods are accessed through instance of implementing class.
        Static methods are access by interface name, not inherited by classes

    interface A {
        default void show() {
            System.out.println("A's default");
        }
    }
    interface B {
        default void show() {
            System.out.println("B's default");
        }
    }
    class C implements A, B {
        public void show() {
            A.super.show(); // or B.super.show()
        }
    }

    Interview questions:
    1. What if a class implements an interface with a default method and also extends a superclass with the same method?
       -> Class method always wins over interface default method.
    2. Can a default method override Object methods (equals, hashCode, toString)?
       -> Interfaces with default methods are meant to add new behavior without breaking existing code.
          But methods from Object are fundamental to all classes, so allowing interfaces to redefine them would cause ambiguity.


 */
public class DefaultMethod {
}
