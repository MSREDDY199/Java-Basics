package TemporaryClasses;

public class Employee {
    String name;
    double salary;
    String department; // optional, useful for grouping examples
    int age;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public Employee(String name, double salary, String department, int age) {
        this.name = name;
        this.salary = salary;
        this.department = department;
        this.age = age;
    }

    // Getters (optional, useful for method references)
    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return name + " (" + salary + ")";
    }
}

