package PatientsLikeMeStatusUpdate;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Postgresql {
    
    public static Connection connection = null;
    static Statement stmt = null;
    static Statement select = null;   
    public static void DBBaglan() throws ClassNotFoundException {
	try 
        {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/PLM", "postgres",
					"44894489");
            connection.setAutoCommit(false);
            System.out.println("Veritabanına Başarı İle Bağlanıldı");
        } 
        
        catch (SQLException e) 
        {
            System.out.println("Bağlantı Hatası");
            e.printStackTrace();
            return;
        }
    }    
    public static void DBInsert(int olay_id, String olay_tarihi_date, String olay_cinsi, String olay_ayrintisi, String dosage_veya_symptom_update, String duzyazi, String paylasim, int user_id) throws SQLException
    {
        PreparedStatement pst = null;
       try {
                
       // stmt = connection.createStatement();
        
    
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                long qu =sdf.parse(olay_tarihi_date).getTime();
                Timestamp olay_tarihi_date1 = new Timestamp(qu);

         String stm = "INSERT INTO patienslikeme (olayid, olaytarih, olaycinsi, olayayrintisi, dosage, duzyazi, paylasim, userid) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(stm);
            pst.setInt(1, olay_id);
            pst.setTimestamp(2, olay_tarihi_date1);
            pst.setString(3, olay_cinsi);
            pst.setString(4, olay_ayrintisi);
            pst.setString(5, dosage_veya_symptom_update);  
            pst.setString(6, duzyazi);  
            pst.setString(7, paylasim);  
            pst.setInt(8, user_id);  
            pst.executeUpdate();
            connection.commit();
            pst.close();
              
 
         
      } catch (Exception e) {
        // System.err.println("*");
         connection.commit();
          pst.close();
         
      }
      
   }
    public static void DBSelect(){ //veritabanından kullanıcıları alır ve sonuc 'a atar.
    
        try 
        {
            select = connection.createStatement();
            String sql = "SELECT userid FROM patientslikeme_userlist as userid;";
            ResultSet userids=select.executeQuery(sql);
            Controller.sonuc = new int[8384];
            int i=0;
            
            while ( userids.next() )
                {
                    int kullanici_id=userids.getInt("userid");
                    Controller.sonuc[i]=kullanici_id;
                    i++;
                }
            
            select.close();     
        }
    
        catch (SQLException e) 
    
        {
        System.out.println("Bağlantı Hatası");
        e.printStackTrace();
        return;
        } 

}
}

            
            
        
 
