package lab8;

import java.util.ArrayList;
import java.util.Scanner;

public class Bai1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> scores = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            System.out.print("Nhap diem thu " + (i + 1) + ": ");
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                scores.add(null);
            } else {
                scores.add(Integer.parseInt(input));
            }
        }

        System.out.println(scores);

        int sum = 0;
        int count = 0;

        for (Integer score : scores) {
            if (score != null) {
                sum += score;
                count++;
            }
        }

        if (count > 0) {
            double average = (double) sum / count;
            System.out.println("Diem trung binh: " + average);

            if (average >= 8) {
                System.out.println("Xep loai: Gioi");
            } else if (average >= 6.5) {
                System.out.println("Xep loai: Kha");
            } else {
                System.out.println("Xep loai: Trung binh");
            }
        }

        scanner.close();
    }
}