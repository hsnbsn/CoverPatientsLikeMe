package TwitterUserTimelineTweets;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class Postgresql {
                static Connection connection = null;
                static Statement stmt = null;
                static Statement stmt1 = null;
                
	public static void DBBaglan() {
		
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/PLM", "postgres",
					"44894489");

         connection.setAutoCommit(false);
         System.out.println("Opened database successfully");
         
                 } catch (SQLException e) {
 
			System.out.println("Bağlantı Hatası");
			e.printStackTrace();
			return;
		}}
        public static void DBSelect(){
             
         try {
         stmt = connection.createStatement();
         
         
         
         String sql = "SELECT * FROM MAIN order by username;";
         //      + "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
         ResultSet usernames=stmt.executeQuery(sql);
         
         
         
         timeliner.sonuc = new String[2302];
         int i=0;
         
         while ( usernames.next() ){
         timeliner.sonuc[i]=usernames.getString("username");
         i++;
                }


         stmt.close();
    
         connection.commit();
         connection.close();       
             }
              catch (SQLException e) {
 
			System.out.println("Bağlantı Hatası");
			e.printStackTrace();
			return;
		} 
                
	}//POSTGRESQL'E BAĞLANIR.
 
}