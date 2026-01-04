import java.io.*;
import java.nio.file.*;

public class FileHandler {
    //Helps load existing user data so that users can find their ratings
    public static String read(String path) {
        try {
            String file = new String(Files.readAllBytes(Paths.get(path)));
            return file; //reads user's saved manga ratings and reviews from the JSON file
        } catch (IOException e) {
            return "{}";
        }
    }

    //Helps save user data when new ratings are added
    public static void write(String path, String data) {
        try {
            //If the data folder doesn't exist, make a new one
            new File(path).getParentFile().mkdirs();

            //Save current user ratings and reviews to the JSON file
            Files.write(Paths.get(path), data.getBytes());
        } catch (IOException e) {
            System.out.println("File save error: " + e.getMessage());
        }
    }
}