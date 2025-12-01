import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
        
        //In our full implementation, we'd actually parse through the entire JSON response properly
        //and it would formatted nicely. For now, we just show the raw JSON response
        System.out.println(jsonResponse.substring(0, Math.min(500, jsonResponse.length())) + "...");
    }
}