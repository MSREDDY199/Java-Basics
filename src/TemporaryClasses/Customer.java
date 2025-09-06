package TemporaryClasses;

import java.util.List;

public class Customer {
    String name;
    List<String> phoneNumbers;

    public Customer(String name, List<String> phoneNumbers) {
        this.name = name;
        this.phoneNumbers = phoneNumbers;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }
}