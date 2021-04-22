package scrappers;

import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App{
    public static void main(String[] args) throws Exception {
        
        String siteUrl = "https://www.basketball-reference.com/players/";
        String name = args[0].toLowerCase();
        String surname = args[1].toLowerCase();
        String url = siteUrl + surname.substring(0,1) + "/" + surname.substring(0,5) + name.substring(0,2);
        int counter = 1;
        String curUrl = url + "0" + String.valueOf(counter) + ".html";
        boolean existent = false;
        try{
        while((Jsoup.connect(url + "0" + String.valueOf(counter) + ".html").response().statusCode()) != 404){        
            
            Document playerDoc = Jsoup.connect(curUrl).get();

            Elements seasons = playerDoc.select("#div_per_game #per_game tbody tr[class]");
            System.out.println(playerDoc.selectFirst("h1[itemprop='name']").text());
            for(Element season : seasons){
                String seasonRange = season.selectFirst("th[data-stat='season']").text();
                String seasonAverage = season.selectFirst("td[data-stat='fg3a_per_g']").text();
                System.out.println(seasonRange + " " + seasonAverage);
                existent = true;
                
            }
        

            counter = counter + 1;
            curUrl= url + "0" + String.valueOf(counter) + ".html";
            TimeUnit.SECONDS.sleep(5);
        }
        }catch (Exception e){
            if(String.valueOf(e).substring(0,66).equals("org.jsoup.HttpStatusException: HTTP error fetching URL. Status=404") && existent){
            System.out.println("Print complete");
            }else if(!existent){
                System.out.println("The player " + name + " " + surname + " doesn't exist.");
            }else{
                System.out.println(e);
            }
        }


    }
}
