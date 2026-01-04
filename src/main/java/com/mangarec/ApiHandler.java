package com.mangarec;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import com.google.gson.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

            //get chapters, 0 is missing
            int chapters = manga.has("chapters") && !manga.get("chapters").isJsonNull()
                    ? manga.get("chapters").getAsInt() : 0;
            
            //get volumes, 0 if missing
            int volumes = manga.has("volumes") && !manga.get("volumes").isJsonNull()
                    ? manga.get("volumes").getAsInt() : 0;

            //cover image link
            String coverUrl = manga.getAsJsonObject("images").getAsJsonObject("jpg")
                    .get("image_url").getAsString();

            String score; //to hold the manga's score
            if (manga.has("score") && !manga.get("score").isJsonNull()) { //make sure the score exists
                score = manga.get("score").getAsString(); 
            } else {
                score = "N/A";
            }

            //get genres of manga
            String genres = "N/A";
            if (manga.has("genres") && !manga.get("genres").isJsonNull()) {
                JsonArray genreArr = manga.getAsJsonArray("genres");
                genres = "";
                for (int i = 0; i < genreArr.size(); i++) {
                    genres += genreArr.get(i).getAsJsonObject().get("name").getAsString();
                    if (i < genreArr.size() - 1) {
                        genres += ", ";
                    }
                }
}


            String synopsis; //to hold the manga's synopsis
            if (manga.has("synopsis") && !manga.get("synopsis").isJsonNull()) { //make sure the score exists
                synopsis = manga.get("synopsis").getAsString(); 
            } else {
                synopsis = "Synopsis isn't available";
            }

            //Truncate the synopsis of the manga if it's too long
            if (synopsis.length() > 200) synopsis = synopsis.substring(0, 200) + "...";

            System.out.println("\n=====================================");
            System.out.println((m + 1) + ". " + mangaTitle);
            System.out.println("-------------------------------------");
            System.out.printf("MAL ID   : %d%n", malID);
            System.out.printf("Score    : %s%n", score);
            System.out.printf("Chapters : %d%n", chapters);
            System.out.printf("Volumes  : %d%n", volumes);
            System.out.printf("Cover    : %s%n", coverUrl);
            System.out.printf("Genres   : %s%n", genres);
            System.out.println("Synopsis : ");
            System.out.println(synopsis);
            System.out.println("=====================================");

        }
        
        //In our full implementation, we'd actually parse through the entire JSON response properly
        //and it would formatted nicely. For now, we just show the raw JSON response
        //System.out.println(jsonResponse.substring(0, Math.min(500, jsonResponse.length())) + "...");
    }

    //gets url and returns the json string
    public static String getUrl(String apiurl) {
        try {
            URL url = new URL(apiurl);
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
            System.out.println("Error getting URL: " + e.getMessage());
            return null;
        }
    }

    //gets all manga genres in the Jikan API 
    public static Map<String, Integer> getGenres() {
        Map<String, Integer> genres = new HashMap<>(); //Map for genres and their malID

        try {
            String jsonString = getUrl("https://api.jikan.moe/v4/genres/manga");

            //Parse the JSON string to get the pagination and data keys
            JsonObject parsedString = JsonParser.parseString(jsonString).getAsJsonObject();
        
            //Now extract the data array by looking up the "data" key
            JsonArray data = parsedString.getAsJsonArray("data");

            //Store the name of the genre and its corresponding id into the map
            for (JsonElement element : data) {
                JsonObject genre = element.getAsJsonObject(); 
                String name = genre.get("name").getAsString().toLowerCase(); //Stores genre name
                int id = genre.get("mal_id").getAsInt(); //Stores genre's mal id

                genres.put(name, id);
            }
        } catch (Exception e) {
            System.out.println("Failed to get genres: " + e.getMessage());
        }

        return genres;
    }
}