# mangarec
MangaRec
MangaRec is a beginner-friendly Java application that helps users discover new manga, track personal ratings and reviews, and receive genre-based recommendations using live data from the Jikan (MyAnimeList) REST API.

Instead of relying on a large local database, MangaRec fetches up-to-date manga information in real time, keeping the application lightweight and current.

Features
1. Search Manga by Title
Retrieve manga information including genres, synopsis, chapter count, volume count, scores, and cover image links.
2. Personal Ratings & Reviews
Users can assign ratings (1–5) and write reviews for manga they’ve read.
3. Genre-Based Recommendations
Get manga suggestions by entering a genre (e.g., action, romance, fantasy), powered by live API data.
4. Persistent Storage
Ratings and reviews are saved locally and automatically loaded on the next run.
5. Menu-Driven Interface
Simple command-line menu designed for ease of use and clarity.

Technologies Used
- Java
- Jikan REST API (MyAnimeList data)
- Gson for JSON parsing
- Java Collections Framework (HashMap, Set)
- File I/O for persistent data storage

How It Works
1. MangaRec fetches manga data from the Jikan API using HTTP requests.
2. JSON responses are parsed to extract relevant manga details.
3. User ratings and reviews are stored in memory using hash maps.
4. Data is saved to and loaded from local text files between sessions.
5. The recommendation system maps user-entered genres to Jikan genre IDs and retrieves relevant manga.

How to Run
1. Clone the repository
2. Ensure Java is installed (Java 8+ recommended)
3. Run the Main class
4. Follow the on-screen menu to search manga, add ratings/reviews, or get recommendations

Project Structure (Simplified)

- Main.java — application entry point and menu flow
- ApiHandler.java — handles API requests and JSON parsing
- Database.java — stores user ratings and reviews
- SimpleFileHandler.java — saves and loads user data

Future Improvements
1. Improved recommendation logic using user rating history
2. Better formatted output and pagination for results
3. GUI or web-based interface
4. More robust data storage (e.g., JSON or database)

Team & Contributions
This project was developed collaboratively.

