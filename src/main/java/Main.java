import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;


public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Database database = new Database();

    //all genres on jikan
    private static Map<String, Integer> genreMap;

    public static void main(String[] args) {
        System.out.println("=== Welcome to Mangarec ===");
        System.out.println("Your personal manga tracker and recommender!");

        //Get all genres in the Jikan API
        genreMap = ApiHandler.getGenres();
        if (genreMap.isEmpty()) System.out.println("Warning: Could not load genres from Jikan");

        //load data using simplefilehandler
        SimpleFileHandler.loadData(database);

        boolean running = true;

        //main prohram loop
        while (running) {
            menu();
            String userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    searchManga();
                    break;
                case "2":
                    addRating();
                    break;
                case "3":
                    addReview();
                    break;
                case "4":
                    showMyRatings();
                    break;
                case "5":
                    getRecommendations();
                    break;
                case "6":
                    //save everything and exit
                    SimpleFileHandler.saveData(database);
                    System.out.println("Your manga ratings and reviews have been saved in the folder 'data'. ");
                    System.out.println("Thank you for using Mangarec! Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void menu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Search Manga");
        System.out.println("2. Add Rating");
        System.out.println("3. Add Review");
        System.out.println("4. Show My Ratings & Reviews");
        System.out.println("5. Get Recommendations");
        System.out.println("6. Exit");
        System.out.print("Choose an option (1-6): ");
    }

    private static void searchManga() {
        System.out.print("\nEnter manga title to search: ");
        String title = scanner.nextLine();

        System.out.println("Searching for: " + title + "...");
        String results = ApiHandler.mangaSearch(title);

        ApiHandler.displaySearchResults(results);
    }

    private static void addRating() {
        System.out.print("\nEnter manga title: ");
        String title = scanner.nextLine();

        System.out.print("Enter your rating (1-5): ");

        try {
            int rating = Integer.parseInt(scanner.nextLine());

            if (rating < 1 || rating > 5) {
                System.out.println("Rating must be between 1 and 5.");
                return;
            }

            database.addRating(title, rating);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number. ");
        }
    }

    private static void addReview() {
        System.out.print("\nEnter manga title: ");
        String title = scanner.nextLine();

        System.out.print("Enter your review: ");
        String review = scanner.nextLine();

        database.addReview(title, review);
    }

    private static void showMyRatings() {

        System.out.println("\n=== My Manga Ratings & Reviews ===");

        Set<String> all = new HashSet<>();
        all.addAll(database.getRatings().keySet());
        all.addAll(database.getReviews().keySet());

        if (all.isEmpty()) {
            System.out.println("No ratings or reviews yet. Add some first!");
            return;
        }
        
        for (String title : all) {
            Integer rating = database.getRating(title);
            String review = database.getReview(title);
            
            System.out.println("-------------------------------------");
            System.out.printf("%-8s: %s%n", "Title", title);
            System.out.printf("%-8s: %s%n", "Rating", rating != null ? rating + "/5" : "No rating");
            System.out.printf("%-8s: %s%n", "Review", review != null ? review : "N/A");
        }

    System.out.println("-------------------------------------");
 
    }
    

    private static void getRecommendations() {

        String userInput = null;
        int genreId = -1;

        while (genreId == -1) {
            System.out.print("\nEnter the genre of manga you want (e.g., action, romance, vampire): ");
            userInput = scanner.nextLine().toLowerCase().replaceAll("[^a-z\\s]", "").trim();

            if (genreMap.containsKey(userInput)) {
                genreId = genreMap.get(userInput);
            }

            else {
                System.out.println("Sorry, unknown genre. Please use one of the following:");
                System.out.println(String.join(", ", genreMap.keySet()));
            }
        }
        
        System.out.println("\nGetting mangas in genre: " + userInput);

        //get manga by genre from jikan
        try {
            String apiURL = "https://api.jikan.moe/v4/manga?genres=" + genreId + "&sfw=false&limit=5";
            String json = ApiHandler.getUrl(apiURL);

            if (json == null || json.isEmpty()) {
                System.out.println("No mangas found for this genre.");
                return;
            }

            ApiHandler.displaySearchResults(json);

        } catch (Exception e) {
            System.out.println("Error fetching manga: " + e.getMessage());
        }
    }
}





