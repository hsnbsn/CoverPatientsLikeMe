package PatientsLikeMeStatusUpdate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Crawler extends WebCrawler {
public int sayac=0;    
@Override
public boolean shouldVisit(Page page, WebURL url) {
    
    String href = url.getURL().toLowerCase();
   // System.out.println("GİRİLECEK HREF "+href);
    
    String allowedPrefixes[] = {"https://www.patientslikeme.com/members/"};//Hiç bir yere girmesin.
    
    for (String allowedPrefix : allowedPrefixes) {
       
        if (href.startsWith(allowedPrefix) && href.contains("source=stream")) 
        {
            return true;
        }
     }

    return false;
}
@Override
public void visit(Page page) {
    sayac++;
    
    String url = page.getWebURL().getURL();
    //System.out.println("__________GİRİLDİ__________:"+url);
    try {   
    
	Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; MASMJS; rv:11.0) like Gecko")
                .get();

        
       Elements users = document.select(".user");
       //System.out.println(users.size());
       
       for (int j=0; j<users.size(); j++) 
       
            {
                int olay_id=Integer.valueOf(users.get(j).id().toString().substring(6)); 
       
                String ham_tarih=users.get(j).select(".secondary-link time").attr("datetime");
               // String gun=ham_tarih.substring(0, ham_tarih.indexOf("T"));
               // String saat=ham_tarih.substring(ham_tarih.indexOf("T")+1, ham_tarih.indexOf("+"));
                
                
                String olay_cinsi=users.get(j).getElementsByClass("stream-title").text();
                String olay_ayrintisi=users.get(j).getElementsByClass("list-inline").text();
                String dosage_veya_symptom_update=users.get(j).getElementsByClass("small-12").text();
                String duzyazi=users.get(j).getElementsByClass("long").text();
                String paylasim=users.get(j).getElementsByClass("text").text();
                int user_id=Integer.valueOf(users.get(j).getElementsByClass("has-is-you-text").attr("data-user_id"));
       
              
             
                
                
                //System.out.println("_____");
                //System.out.println(olay_id); //olay_id
                //System.out.println(ham_tarih);
                //System.out.println(olay_cinsi); // olay
                //System.out.println(olay_ayrintisi); // olay ayrintisi
                //System.out.println(dosage_veya_symptom_update); // dosage_veya_symptom_update
                //System.out.println(duzyazi); // yazı
                //System.out.println(paylasim); // paylaşım
                //System.out.println(user_id); //user_id
                
                
              Postgresql.DBInsert(olay_id, ham_tarih, olay_cinsi, olay_ayrintisi, dosage_veya_symptom_update, duzyazi, paylasim, user_id);
            }

        
        //
       // System.out.println(sayac+ " User yazdırıldı..");
            
         
     }
     catch (IOException e) {
    } catch (SQLException ex) {
        Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
    } 
}
}
