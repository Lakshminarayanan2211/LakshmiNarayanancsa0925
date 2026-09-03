package smartcampus;
import java.sql.*;
public class DB {
    private static final String URL="jdbc:mysql://localhost:3306/smart_campus_parking?useSSL=false&serverTimezone=Asia/Kolkata";
    private static final String USER="root";
    private static final String PASSWORD="root"; // CHANGE THIS
    static { try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch(Exception e) { throw new RuntimeException("MySQL Connector/J missing",e); } }
    public static Connection get() throws SQLException { return DriverManager.getConnection(URL,USER,PASSWORD); }
}
