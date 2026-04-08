package lab8;

public class Bai4 {
    public static void main(String[] args) {
        String welcome = """
                Chao mung den voi ung dung quan ly
                Vui long chon chuc nang de tiep tuc
                """;
        System.out.println(welcome);

        String email = """
                Chao %s,
                Tai khoan cua ban da duoc tao thanh cong.
                """.formatted("Nguyen Van A");
        System.out.println(email);

        String html = """
                <html>
                    <body>
                        <h1>Thong tin sinh vien</h1>
                        <p>Ten: Nguyen Van A</p>
                        <p>Diem: 9.0</p>
                    </body>
                </html>
                """;
        System.out.println(html);

        String sql = """
                SELECT * FROM Student
                WHERE gpa > 3.0
                """;
        System.out.println(sql);
    }
}