package TwitterStreamTweets;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Properties;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

public class TwitterHelloWorld {

	/** The actual Twitter stream. It's set up to collect raw JSON data */
	private TwitterStream twitterStream;
	private String[] keywords;
	Properties prop = new Properties();
	

	public TwitterHelloWorld() {

            twitterStream = new TwitterStreamFactory().getInstance();

	}

	public void startTwitter() {

            Postgresql.DBBaglan();

		String keywordString =
                         "multiple sclerosis,"
                        + "ill,"
                        + "patientslikeme,"
                        + "sick,"
                        + "hospital,"
                        + "fatigue"
                        + "numbness,"
                        + "tingling,"
                        + "spasticity,"
                        + "vision problem,"
                        + "pain,"
                        + "constipation,"
                        + "diognised";
                
		keywords = keywordString.split(",");
		for (int i = 0; i < keywords.length; i++) {
			keywords[i] = keywords[i].trim();
		}

		// Set up the stream's listener (defined above),
		twitterStream.addListener(listener);

		System.out.println("Twitter Dinlemesi"+ keywordString + " parametreleri ile açılıyor.....");

		// Set up a filter to pull out industry-relevant tweets
		FilterQuery query = new FilterQuery().track(keywords);
		twitterStream.filter(query);

	}

	public void stopTwitter() throws SQLException {

		System.out.println("Twitter Dinlemesi Kapanıyor...");
		twitterStream.shutdown();

                Postgresql.DbKapa();
	}

	StatusListener listener = new StatusListener() {

		// The onStatus method is executed every time a new tweet comes in.
		public void onStatus(Status status) {
			
                    
			BigDecimal ID=BigDecimal.valueOf(status.getId());
                        String KULLANICI=status.getUser().getScreenName();
                        String KULLANICI_OZEL=status.getUser().getLocation();
                        String ICERIK=status.getText();
                        String LOKASYON="";
                    
                       
                       if (status.getLang().contains("en")){
                        
                        Postgresql.DBInsert( //veri tabanına insert eder
                                ID, 
                                KULLANICI, 
                                KULLANICI_OZEL,
                                ICERIK, 
                                LOKASYON); 
                       
                       }
                        
                        

		}

		// This listener will ignore everything except for new tweets
                @Override
		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
                @Override
		public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
                @Override
		public void onScrubGeo(long userId, long upToStatusId) {}
                @Override
		public void onException(Exception ex) {}
                @Override
		public void onStallWarning(StallWarning warning) {}
	};

	public static void main(String[] args) throws InterruptedException, SQLException {

		TwitterHelloWorld twitter = new TwitterHelloWorld();
		twitter.startTwitter();
		Thread.sleep(60000);
		twitter.stopTwitter();

	}

}