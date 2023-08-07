package ss13.bt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/demojdbc";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "274996";
    public static Connection openConnection() throws ClassNotFoundException, SQLException {
        Connection con = null;
        Class.forName(DRIVER);
        con = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        return con;
    }
    public static void closeConnection(Connection con) throws SQLException {
        if(con!=null){
            con.close();
        }
    }
}
