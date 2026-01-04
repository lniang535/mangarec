package com.mangarec;

import org.junit.jupiter.api.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class DatabaseTest {
    private Database database;

    @BeforeEach
    public void setUp() {
        database = new Database();
    }

    @Test
    @DisplayName("Adds a rating properly") 
    public void testAddRating() {
        database.addRating("Naruto", 5);
        assertEquals(5, database.getRating("Naruto"));
    }

    @Test
    @DisplayName("Gets a rating properly") 
    public void testGetRating() {
        database.addRating("Naruto", 5);
        assertTrue(database.getRatings().containsKey("Naruto"));
    }

    @Test
    @DisplayName("Adds a review properly") 
    public void testAddReview() {
        database.addReview("Naruto", "Reviewed");
        assertEquals("Reviewed", database.getReview("Naruto"));
    }

    @Test
    @DisplayName("Gets a review properly") 
    public void testGetReview() {
        database.addReview("Naruto", "Reviewed");
        assertTrue(database.getReviews().containsKey("Naruto"));
    }

    @Test
    @DisplayName("Updates an existing rating")
    public void testUpdateRating() {
        database.addRating("Naruto", 5);
        database.addRating("Naruto", 3);

        assertEquals(3, database.getRating("Naruto"));
    }

    @Test
    @DisplayName("Updates an existing review") 
    public void testUpdateReview() {
        database.addReview("Naruto", "Reviewed");
        database.addReview("Naruto", "Reviewed again");

        assertEquals("Reviewed again", database.getReview("Naruto"));
    }

    @Test
    @DisplayName("The review map has the correct number of entries")
    public void testReviewMapSize() {
        database.addReview("Naruto", "Reviewed");
        database.addReview("Bleach", "Cool manga");

        Map<String, String> reviews = database.getReviews();
        assertEquals(2, reviews.size());
    }

    @Test
    @DisplayName("The rating map has the correct number of entries")
    public void testRatingMapSize() {
        database.addRating("Naruto", 4);
        database.addRating("Bleach", 3);

        Map<String, Integer> ratings = database.getRatings();
        assertEquals(2, ratings.size());
    }

    @Test
    @DisplayName("Trying to get a nonexistent rating/review returns null")
    public void testGetNonExistentRatingAndReviewReturnsNull() {
        assertNull(database.getRating("One Piece"));
        assertNull(database.getReview("One Piece"));
    }
}
