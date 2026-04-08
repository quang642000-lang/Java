package lab8;

import java.util.ArrayList;
import java.util.List;

record Student(String id, String name, double gpa) {
    public Student {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA khong hop le");
        }
    }

    public boolean isScholarshipEligible() {
        return gpa >= 3.2;
    }
}

public class Bai2 {
    public static void main(String[] args) {
        Student s = new Student("PH123", "Nguyen Van A", 3.5);
        System.out.println(s);

        List<Student> list = new ArrayList<>();
        list.add(new Student("PH111", "Tran B", 3.8));
        list.add(new Student("PH222", "Le C", 2.5));
        list.add(new Student("PH333", "Pham D", 3.2));

        for (Student st : list) {
            if (st.isScholarshipEligible()) {
                System.out.println(st);
            }
        }
    }
}