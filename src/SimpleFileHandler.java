//Saves and loads the Database object as JSON
//file system without gson bc not working

import java.io.*;
import java.util.*;

public class SimpleFileHandler {

    public static void savedata(Database userData) {
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
            System.out.println("Error saving data: " + e.getMessage())
        }
    }

    //load ratings from text file
    public static void loadData(Database userData)
}