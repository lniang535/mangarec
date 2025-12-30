import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String, Integer> ratings; //stores manga title and rating 1-5
    private Map<String, String> reviews; // stores manga title and review text

    public Database() {
        ratings = new HashMap<>();
        reviews = new HashMap<>();
    }

    //add or update a rating 
    public void addRating(String title, int rating) {
        ratings.put(title, rating);
        System.out.println("Rating saved: " + title + " - " + rating + "/5");
    }

    //add or update a review
    public void addReview(String title, String review) {
        reviews.put(title, review);
        System.out.println("Review saved for: " + title);
    }

    //get all ratings
    public Map<String, Integer> getRatings() {
        return ratings;
    }

    //get all reviews
    public Map<String, String> getReviews() {
        return reviews;
    }


    //get rating for specific manga 
    public Integer getRating(String title) {
        return ratings.get(title);
    }

    //get review for specific manga
    public String getReview(String title) {
        return reviews.get(title);
    }
}