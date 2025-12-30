import java.util.Scanner;
// not working : import com.google.gson.Gson;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Database database = new Database();

    public static void main(String[] args) {
        System.out.println("=== Welcome to Mangarec ===");
        System.out.println("Your personal manga tracker and recommender!");

        //load data using simplefilehandler

        boolean running = true;

        //main prohram loop
        while (running) {
            menu();

            String userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    //search for manga
                    searchManga();
                    break;
                case "2":
                    //add rating
                    addRating();
                    break;
                case "3":
                    //add review
                    addReview();
                    break;
                case "4":
                    showMyRatings();
                    break;
                case "5":
                    //get recommendations
                case "6":
                    //save everything and exit

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
        //we still have to go in an edit apihandler to display nicely
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

        if (database.getRatings().isEmpty()) {
            System.out.println("No ratings yet. Rate some manga first!");
        } else {
             for (String title : database.getRatings().keySet()) {
                int rating = database.getRating(title);
                String review = database.getReview(title);
                System.out.println(title + ": " + rating + "/10");
                if (review != null) {
                    System.out.println("  Review: " + review);
                }
                System.out.println(); // Empty line 
            }
        }
    }
    
}





