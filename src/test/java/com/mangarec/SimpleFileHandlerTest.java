package com.mangarec;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleFileHandlerTest {
    private Database database;

    @BeforeEach
    public void setUp() {
        database = new Database();

        //Cleans up previous files made during the testing
        File file = new File("data/ratings.txt");
        if (file.exists()) file.delete();

        //Deletes data directory if it exists
        new File("data").delete();
    }

    @Test
    @DisplayName("A file is created after saving user reviews and ratings")
    public void testSaveDataCreatesFile() {
        database.addRating("Naruto", 5);
        database.addReview("Naruto", "Reviewed");

        //Save the data
        SimpleFileHandler.saveData(database);
        File file = new File("data/ratings.txt");

        assertTrue(file.exists(), "ratings.txt should exist after having saved");
    }

    @Test
    @DisplayName("File that is saved holds correct review and rating")
    public void testSaveDataCreatesFileWithCorrectReviewAndRating() {
        database.addRating("Naruto", 5);
        database.addReview("Naruto", "Reviewed");

        //Save the data
        SimpleFileHandler.saveData(database);
        File file = new File("data/ratings.txt");

        assertTrue(file.exists(), "ratings.txt should exist after having saved");

        try (Scanner scan = new Scanner(file)) {
            boolean rating = false; //Holds whether file has rating or not
            boolean review = false; //Holds whether file has rating or not

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line.equals("RATING:NARUTO:5")) rating = true;
                if (line.equals("REVIEW:NARUTO:Reviewed")) review = true;
            }

            assertTrue(rating, "Saved file should have the correct rating");
            assertTrue(review, "Saved file should have the correct review");
        } catch (Exception e) {
            fail("Reading file threw an exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("LoadData method should correctly read the information from reviews and ratings")
    public void testLoadDataCorrectlyReadsReviewsAndRatings() {
        //Manually fill the file
        new File("data").mkdirs();
        try (PrintWriter writer = new PrintWriter("data/ratings.txt")) {
            writer.println("RATING:NARUTO:4");
            writer.println("REVIEW:NARUTO:Reviewed");
        } catch (Exception e) {
            fail("Writing the test file failed: " + e.getMessage());
        }

        SimpleFileHandler.loadData(database);

        Map<String, String> reviews = database.getReviews();
        Map<String, Integer> ratings = database.getRatings();

        assertEquals(1, ratings.size(), "There should be one rating");
        assertEquals(1, reviews.size(), "There should be one review");
        assertEquals(4, ratings.get("NARUTO"));
        assertEquals("Reviewed", reviews.get("NARUTO"));
    }

    @Test
    @DisplayName("If file doesn't exist, loadData shouldn't throw")
    public void testLoadDataWorksWithNonexistentFile() {
        //Make sure the file doesn't exist
        File file = new File("data/ratings.txt");
        if (file.exists()) file.delete();

        assertTrue(database.getRatings().isEmpty());
        assertTrue(database.getReviews().isEmpty());
    }
}
