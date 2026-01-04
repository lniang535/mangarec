//Saves and loads the Database object as JSON
//file system without gson bc wasn-t working at time

import java.io.*;
import java.util.*;

public class SimpleFileHandler {

    public static void saveData(Database userData) {
        try {
            new File("data").mkdirs();
            PrintWriter writer = new PrintWriter("data/ratings.txt");

            //save ratings
            for (Map.Entry<String, Integer> e : userData.getRatings().entrySet()) {
                writer.println("RATING:" + e.getKey() + ":" + e.getValue());            
            }

            //save reviews
            for (Map.Entry<String, String> e : userData.getReviews().entrySet()) {
                writer.println("REVIEW:" + e.getKey() + ":" + e.getValue());
            }

            writer.close();
            System.out.println("Data saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    //load ratings from text file
    public static void loadData(Database userData) {
        try {
            File file = new File ("data/ratings.txt");
            if (!file.exists()) {
                System.out.println("No previous data found. Starting fresh!");
                return;
            }

            Scanner scanner = new Scanner(file);
            int loadCount = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":", 3);

                if (parts.length >= 3) {
                    if (parts[0].equals("RATING")) {
                        String title = parts[1];
                        int rating = Integer.parseInt(parts[2]);
                        userData.addRating(title, rating);
                        loadCount++;
                    } else if (parts[0].equals("REVIEW")) {
                        String title = parts[1];
                        String review = parts[2];
                        userData.addReview(title, review);
                    }
                }

            }
        
            scanner.close();
            System.out.println("Loaded " + loadCount + " ratings from previous session.");
    } catch (IOException e) {
        System.out.println("Error loading data: " + e.getMessage());
    }
}
}