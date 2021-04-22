package scrappers;

import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App{
    public static void main(String[] args) throws Exception {
        
        //Initialization of the base url and args
        String siteUrl = "https://www.basketball-reference.com/players/";
        String name = args[0].toLowerCase();
        String surname = args[1].toLowerCase();

        /**
         * Base url setup
         * Example: 
         *      Parameters: Luka Doncic
         *      Lowercase:  luka doncic
         *      url setup:  [lu]ka [donci]c
         *      url:        doncilu
         */
        String url = siteUrl + surname.substring(0,1) + "/" + surname.substring(0,5) + name.substring(0,2);
        int counter = 1; 
        String curUrl = url + "0" + String.valueOf(counter) + ".html"; // initializes the current url
        boolean existent = false; // sets up the player existence flag

        try{
            while(true){//iterates until an exception breaks the loop
                
                Document playerDoc = Jsoup.connect(curUrl).get(); // requests and parses the current url

                System.out.println(playerDoc.selectFirst("h1[itemprop='name']").text());// prints the current player's name

                Elements seasons = playerDoc.select("#div_per_game #per_game tbody tr[class]");//finds the needed table rows for each season
                
                for(Element season : seasons){//iterates through each season
                    //saves and prints out the sought after data for each season
                    String seasonRange = season.selectFirst("th[data-stat='season']").text();
                    String seasonAverage = season.selectFirst("td[data-stat='fg3a_per_g']").text();
                    
                    System.out.println(seasonRange + " " + seasonAverage);

                    //once even a single season is written it is confirmed that a player with that id exists
                    existent = true;
                    
                }
            
                // add to the counter and try the next page
                counter = counter + 1;
                curUrl= url + "0" + String.valueOf(counter) + ".html";
                // delay in order to not overwhelm the website's servers
                TimeUnit.SECONDS.sleep(5);
            }
        }catch (Exception e){
            //catches the page missing exception coupled with a player having their data already printed
            if(String.valueOf(e).substring(0,66).equals("org.jsoup.HttpStatusException: HTTP error fetching URL. Status=404") && existent){ 
                System.out.println("Print complete");
            // catches the page missing exception coupled with no information having been printed
            // this also doubles as the website doing input validation for us 
            }else if(!existent){
                System.out.println("The player " + name + " " + surname + " doesn't exist.");
            //catches a new exception
            }else{
                System.out.println(e);
            }
        }


    }
}
