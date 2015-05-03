package TwitterLookUpUsers;

/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import twitter4j.*;
public  final class LookupUsers {
    
public static String[] sonuc=null;
public static String[] lokasyon=null;
    public static void main(String[] args) throws InterruptedException {

        Postgresql.DBBaglan();
        Postgresql.DBSelect();
        Twitter twitter = new TwitterFactory().getInstance();
      
           
        for (int j=0;j<sonuc.length;j++){
            
            try {
                User user=twitter.showUser(sonuc[j]);
                if (user.getStatus() != null) 
                {
                    System.out.println((j+1)+". Sorgu___________________________________");
                    System.out.println("@" + user.getScreenName());
                    System.out.println(user.getTimeZone());
                    System.out.println("@" + user.getName());
                    System.out.println("@" + user.getURL());
                    System.out.println("LOKASYONN  : " + user.getLocation());
                    System.out.println("@" + user.getDescription());
                    System.out.println("@" + user.getOriginalProfileImageURL());
                    System.out.println("@" + user.getMiniProfileImageURL());
                    System.out.println("@" + user.getProfileBackgroundImageURL());
                    System.out.println("_______________________________________________");
                    
                    if (lokasyon[j].contains(user.getLocation()))
                    
                    {
                    System.out.println("KULLANICI BU OLABİLİR*************************");
                    
                    }
                } 
                else {
                    
                    
                    // the user is protected
                    System.out.println((j+1)+". Sorgu______________________________________");
                    System.out.println("@" + user.getScreenName()+ "---- KULLANICI PUBLIC DEĞİL");
                    System.out.println("___________________________________________________");
                    }          
            
        }
        
         
        
        
        catch (TwitterException te) 
        
        {
            te.printStackTrace();
           
            if ((te.getErrorCode()==88))
            {
                System.err.println("EXCEED..............");
                Thread.sleep(900000); //wait 15 min
            }
            System.out.println();
         
        }
        
        }
        
       
    
       System.exit(0);
}
    
    
}

