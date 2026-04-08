package lab8;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@interface Developer {
    String name();
    String version();
}

class Employee {
    @Deprecated
    public double getSalary() {
        return 1000;
    }

    public double getNewSalary() {
        return 1000;
    }
}

class Manager extends Employee {
    @Override
    @Deprecated
    public double getSalary() {
        return 2000;
    }

    @Override
    public double getNewSalary() {
        return 2000;
    }
}

@Developer(name = "Nguyen Van A", version = "1.0")
class BusinessLogic {
}

public class Bai3 {
    public static void main(String[] args) {
        Class<BusinessLogic> obj = BusinessLogic.class;
        if (obj.isAnnotationPresent(Developer.class)) {
            Developer dev = obj.getAnnotation(Developer.class);
            System.out.println("Name: " + dev.name());
            System.out.println("Version: " + dev.version());
        }
    }
}