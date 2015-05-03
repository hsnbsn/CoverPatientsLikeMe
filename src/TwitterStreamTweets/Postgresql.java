package TwitterStreamTweets;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class Postgresql {
             public   static Connection connection = null;
                static Statement stmt = null;
                static Statement stmt1 = null;
                
	public static void DBBaglan() {
		
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/PLM", "postgres",
					"44894489");

         //connection.setAutoCommit(false);
         System.out.println("Opened database successfully");
         
                 } catch (SQLException e) {
 
			System.out.println("Bağlantı Hatası");
			e.printStackTrace();
			return;
		}}
         
        public static void DBInsert(BigDecimal tweetId,String username,String profileLocation,String content,String geoloc ){
             
          
            try {
                
       // stmt = connection.createStatement();
        PreparedStatement pst = null;
    
          

         String stm = "INSERT INTO twitter_stream(tweetId,username,profileLocation,content,geoloc) VALUES(?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(stm);
            pst.setBigDecimal(1, tweetId);
            pst.setString(2, username);
            pst.setString(3, profileLocation);
            pst.setString(4, content);
            pst.setString(5, geoloc);          
            pst.executeUpdate();
            
          //  pst.close();
              
         //stmt.executeUpdate(sql);
         //stmt.close();
    
         //System.out.println(sql);
         
      } catch (Exception e) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
                
	}//POSTGRESQL'E BAĞLANIR.
        
        public static void DbKapa() throws SQLException{connection.close();}
 
}