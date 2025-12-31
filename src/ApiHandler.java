import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import com.google.gson.*;
import java.net.URL;

public class ApiHandler {
    //A search method that finds manga by title
    public static String mangaSearch(String title) {
        try {
            String apiURL = "https://api.jikan.moe/v4/manga?q=" + title + "&limit=5";
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            } 
            
            reader.close();
            return response.toString();
        } catch (Exception e) {
            System.out.println("Error in connecting to the Jikan API: " + e.getMessage());
            return null;
        }
    }

    //A method that displays the search results
    public static void displaySearchResults(String jsonResponse) {
        if (jsonResponse == null) {
            System.out.println("API error or no results were found");
            return;
        }

        //Parse the JSON string to get the pagination and data keys
        JsonObject parsedString = JsonParser.parseString(jsonResponse).getAsJsonObject();
        
        //Now extract the data array by looking up the "data" key
        JsonArray data = parsedString.getAsJsonArray("data");
        if (data.size() == 0) {
            System.out.println("Manga was not found");
            return;
        }

        System.out.println("\n=== Search Results ===");
        
        //Now go through search results in the data array
        for (int m = 0; m < data.size(); m++) {
            JsonObject manga = data.get(m).getAsJsonObject(); //grab a manga object
            String mangaTitle = manga.get("title").getAsString(); //grabs the manga title
            int malID = manga.get("mal_id").getAsInt(); //grabs the manga's MyAnimeList id

            String score; //to hold the manga's score
            if (manga.has("score") && !manga.get("score").isJsonNull()) { //make sure the score exists
                score = manga.get("score").getAsString(); 
            } else {
                score = "N/A";
            }

            String synopsis; //to hold the manga's synopsis
            if (manga.has("synopsis") && !manga.get("synopsis").isJsonNull()) { //make sure the score exists
                synopsis = manga.get("synopsis").getAsString(); 
            } else {
                synopsis = "Synopsis isn't available";
            }

            //Truncate the synopsis of the manga if it's too long
            if (synopsis.length() > 150) synopsis = synopsis.substring(0, 200) + "...";

            System.out.println((m + 1) + ". " + mangaTitle);
            System.out.println("    MAL ID: " + malID);
            System.out.println("    Score: " + score);
            System.out.println("    " + synopsis);
            System.out.println();

        }
        
        //In our full implementation, we'd actually parse through the entire JSON response properly
        //and it would formatted nicely. For now, we just show the raw JSON response
        System.out.println(jsonResponse.substring(0, Math.min(500, jsonResponse.length())) + "...");
    }
}