package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection=null;
    private Connection con;
    private DbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con= DriverManager.getConnection("jdbc:mysql://localhost:3306/vehicle_parts","root","ijse");
    }

    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {
        return dbConnection==null? dbConnection=new DbConnection() : dbConnection;
    }

    public Connection getConnection(){
        return con;
    }
}
/*SELECT column1, column2, ...
FROM table_name
WHERE columnN LIKE pattern;*/