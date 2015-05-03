package Crawler;


import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



 
public class Controller {
 
   
    static final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    public static int[] sonuc;
   
    public static void main(String[] args) throws Exception {
        
 
                
                
        String crawlStorageFolder = "D:/crawler";
        int numberOfCrawlers = 4;
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder); 
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        config.setPolitenessDelay(1000);
        config.setMaxDepthOfCrawling(1);
        config.setProxyHost("localhost");
        config.setProxyPort(9050);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        
        Postgresql.DBBaglan();
        
      for (int i=1; i<1191; i++){

        System.out.println(i+" nci seed oluşturuldu ");
        controller.addSeed("https://www.patientslikeme.com/patients?patient_page="+i);
      }
      
        controller.start(Crawler.class, numberOfCrawlers);
        
       
       
       
             Postgresql.connection.commit();
             Postgresql.connection.close();
    }
}