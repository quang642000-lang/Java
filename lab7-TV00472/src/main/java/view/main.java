package view;
import entity.Employee;
import java.util.List;
import repository.EmployeeRepository;   

public class main {
    public static void main(String[] args) {

        EmployeeRepository repo = new EmployeeRepository();

        System.out.println("Thêm nhân viên");
        Employee e1 = new Employee(0, "Nguyen Van A", 5000);
        repo.insert(e1);

        System.out.println("\n Xem danh sách nhân viên");
        List<Employee> list = repo.getAll();
        for (Employee e : list) {
            System.out.println(e);
        }

        System.out.println("\n Cập nhật");
        Employee e2 = new Employee(1, "Phạm Thiện Quang", 8000);
        repo.update(e2);

        System.out.println("\n Xóa");
        repo.delete(2);

        System.out.println("\n Tìm kiếm");
        Employee e3 = repo.findById(1);
        if (e3 != null) {
            System.out.println(e3);
        }
    }
}
