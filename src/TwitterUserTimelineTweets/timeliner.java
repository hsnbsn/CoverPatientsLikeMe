package TwitterUserTimelineTweets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import org.json.simple.parser.ParseException;
import twitter4j.*;

/**
 *
 * @author volka_000
 */
public class timeliner {
    public static String[] sonuc;

    public static void main (String[] args) throws JSONException, ParseException, InterruptedException{
        
        Postgresql.DBBaglan();
        Postgresql.DBSelect();
        Twitter twitter = new TwitterFactory().getInstance();    
        int pageno = 1;
        //String user = "gasanyasan";
        List statuses = new ArrayList();

      for (int i=0; i<5;i++){ 
          System.out.println("ARANAN KULLANICI  :  "+ sonuc[i]);
while (true) {
  try {
    int size = statuses.size(); 
    Paging page = new Paging(pageno++, 100);
    statuses.addAll(twitter.getUserTimeline(sonuc[i], page));
    if (statuses.size() == size)
       
    break;
    } 
  catch(TwitterException e) {
      
      if (e.getErrorCode()==88){System.out.println("SORGU LİMİTİ AŞILDI.....UYKUYA GİRİYOR...");
      Thread.sleep(900000);
      
      
      
      }

    //e.printStackTrace();
  }
}}

        for (Object statuse : statuses) {
            Status a = (Status) statuse;
            System.out.println(a.getText());
            System.out.println(a.getCreatedAt());
            System.out.println(a.getUser().getScreenName());
            System.out.println(a.getId());
        }

System.out.println("Total: "+statuses.size());
  // System.out.println(stats.get(0).getText());     
        
    }
    
}
