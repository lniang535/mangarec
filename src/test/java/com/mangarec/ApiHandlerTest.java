package com.mangarec;

import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ApiHandlerTest {
    private final PrintStream originalOut = System.out; //Stores original System.out
    private ByteArrayOutputStream outText; //Captures the printed text during a test
    
    @BeforeEach
    public void setUp() {
        outText = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outText));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut); //restores the original console output
    }

    @Test
    @DisplayName("mangaSearch returns a Json string")
    public void testMangaSearchReturnsJsonString() {
        String JsonString = ApiHandler.mangaSearch("Naruto");
        assertNotNull(JsonString, "mangaSearch returns a non-null Json string");
        assertTrue(JsonString.contains("Naruto") || JsonString.contains("data"), "The string should contain 'Naruto' or 'data'");
    }

    @Test
    @DisplayName("displaySearchResults prints manga titles, score and genres")
    public void testDisplaySearchResultsPrintsProperResults() {
        String JsonResponse = "{\"data\":[{\"mal_id\":1,\"title\":\"Naruto\",\"chapters\":72,\"volumes\":12," +
                "\"images\":{\"jpg\":{\"image_url\":\"url\"}},\"score\":8.5,\"genres\":[{\"name\":\"Action\"}]," +
                "\"synopsis\":\"Test synopsis\"}]}";

        ApiHandler.displaySearchResults(JsonResponse);
        String output = outText.toString();

        assertTrue(output.contains("Naruto"), "Output should have manga title");
        assertTrue(output.contains("Score"), "Output should have score label");
        assertTrue(output.contains("Genres"), "Output should have genres label");
    }

    @Test
    @DisplayName("getGenres returns a map that has 'action'")
    public void testGetGenresHasAction() {
        Map<String, Integer> genres = ApiHandler.getGenres();
        assertNotNull(genres, "getGenres returns a non-null map");
        assertTrue(genres.containsKey("action"), "Genres has the action keyword");
        assertTrue(genres.get("action") > 0, "'action' has a valid malID");
    }

    @Test
    @DisplayName("getUrl returns a nonNull json string for a valid url")
    public void testGetUrlReturnsNonNullJsonString() {
        String JsonString = ApiHandler.getUrl("https://api.jikan.moe/v4/manga?q=naruto&limit=1");
        assertNotNull(JsonString, "getUrl returns a non-null response");
        assertTrue(JsonString.contains("data"), "The json string has 'data'");
    }
}
