package Crawler;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
 
public class Postgresql {
          public      static Connection connection = null;
               static Statement stmt = null;
               static Statement select = null;
                
                
	public static void DBBaglan() throws ClassNotFoundException {
		
		try {
                    Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/PLM", "postgres",
					"44894489");

         connection.setAutoCommit(false);
         System.out.println("Veritabanına Başarı İle Bağlanıldı");
         
                 } catch (SQLException e) {
 
			System.out.println("Bağlantı Hatası");
			e.printStackTrace();
			return;
		}}
         
        public static void DBInsert(
                int UserID, 
                String UserNameStr,
                String Gender,
                int Age,
                String Location, 
                String ConditionStr, 
                String FirstSymDate, 
                String DiogDate, 
                String AboutUser,
                String avatarlink,
        String firstLogin,
        String lastLogin){
          
          
            try {
                
                
                
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
               long qu =sdf.parse(FirstSymDate).getTime();
               Timestamp FirstSymDate_date = new Timestamp(qu);
               qu =sdf.parse(DiogDate).getTime();
               Timestamp DiogDate_date = new Timestamp(qu);
               
               
               SimpleDateFormat kdf = new SimpleDateFormat("MMM dd, yyyy",Locale.ENGLISH);
               long qu1 =kdf.parse(firstLogin).getTime();
               Timestamp firstLogin_date = new Timestamp(qu1);
               
               qu1 =kdf.parse(lastLogin).getTime();
               Timestamp lastLogin_date = new Timestamp(qu1);
              
        
            
            
        stmt = connection.createStatement();
        PreparedStatement pst = null;

          

            String stm = "INSERT INTO patientslikeme_userlist(userid, username, gender, age, loc1, condition, first_sym, diog_date, abou_user, avatarlink, firstlogin, lastlogin) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(stm);
            pst.setInt(1, UserID);
            pst.setString(2, UserNameStr);
            pst.setString(3, Gender);
            pst.setInt(4, Age);
            pst.setString(5, Location);
            pst.setString(6, ConditionStr);
            pst.setTimestamp(7,  FirstSymDate_date);
            pst.setTimestamp(8,  DiogDate_date);
            pst.setString(9, AboutUser);
            pst.setString(10, avatarlink);
            pst.setTimestamp(11,  firstLogin_date);
            pst.setTimestamp(12,  lastLogin_date);
            
            pst.executeUpdate();
            connection.commit();
            pst.close();
              
         //stmt.executeUpdate(sql);
         //stmt.close();
    
         //System.out.println(sql);
         
      } catch (Exception e) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
      
   }


        
        
        
        


}

            
            
        
 
