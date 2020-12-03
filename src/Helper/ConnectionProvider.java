package Helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {
    public static Connection con;

    public static Connection getConnection(){
        String DB_URL = "jdbc:mysql://localhost:3306/busticketbooking";
        String DB_USER = "root";
        String DB_PASS= "rony123456";
        String query;
        try{
           if(con==null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            }
            }catch (Exception e){
            System.out.println("Connection providerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
            System.out.println(e);
            e.printStackTrace();
        }
        return con;
    }

}
