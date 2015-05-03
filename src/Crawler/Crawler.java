package Crawler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler extends WebCrawler {
    
    public int sayac=0;
    
@Override
public boolean shouldVisit(Page page, WebURL url) {
    
    String href = url.getURL().toLowerCase();
    String allowedPrefixes[] = {"https://www.patientslikeme.com/patients/view"};

    for (String allowedPrefix : allowedPrefixes) {
       
        if (href.startsWith(allowedPrefix)) {
             System.out.println(href);
            return true;
        }
     }

    return false;
}

@Override
public void visit(Page page) {
    sayac++;
    String url = page.getWebURL().getURL();
    try {     
        
    
	Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; MASMJS; rv:11.0) like Gecko")
               .get();
        
        
        Elements users = document.select(".viewer");
        
        
        String username = users.select(".media-content.align-left a").text();// kullanıcı adı
        String sexageloc = users.select(".align-left p").text();//cinsiyet yaş şehir --> 2        
        String condition = users.select(".condition-links a:nth-child(1)").text(); // primary condition
        String firstsymptom = users.select(".large-12 time").first().attr("datetime"); // primary condition firstsymptom
        String diognosis = users.select(".large-12 time").last().attr("datetime"); // primary condition diognosis
        String aboutpage = users.select("div.spaced-bottom:nth-child(1)").text();//about user page
        String loginStat = users.select(".helptext").first().text();//about user page
        String avatarLink = users.select(".lightview").attr("href");//cinsiyet yaş şehir --> 2 
        String UserID=url.substring(url.indexOf("view/")+5,url.indexOf("?")); //user id
        
        
        
        
        String sal=sexageloc;
        int genderIndex=sal.indexOf(",", 0);
        String Gender=sal.substring(0,genderIndex);
        String Age=sal.substring(genderIndex+2,sal.indexOf("years")-1);
        String Location=sal.substring(sal.indexOf("years")+6,sal.length());
       
        String mal=loginStat;
        int ikinoktaindex=mal.indexOf(":",0);
        String firstLogin=mal.substring(ikinoktaindex+2, mal.indexOf("Last")-1);
        
        int loginindex=(mal.indexOf("Login")+6);
        String lastLogin =mal.substring(loginindex, mal.length());
        
        
        int ageim=Integer.parseInt(Age);
        int idim=Integer.parseInt(UserID);
         
    
       
        
       Postgresql.DBInsert(idim, username, Gender, ageim, Location, condition, 
                            firstsymptom, diognosis, aboutpage, avatarLink,firstLogin,lastLogin  );
       
        System.out.println(sayac+ " User yazdırıldı..");
 
         
     }
     catch (IOException e) {
    }
}
}
