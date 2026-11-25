package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Matcher {
    private HashSet<String> favorites;
    private HashSet<String> dislikes;
    private final String favoritesFilePath = "src/main/java/org/example/favorites.txt";
    private final String dislikesFilePath = "src/main/java/org/example/dislikes.txt";

    // initialize favorites and dislikes lists by reading from their respective files
    public Matcher() {
        favorites = new HashSet<>();
        dislikes = new HashSet<>();
        try (BufferedReader favoritesIn = new BufferedReader(new FileReader(favoritesFilePath));
             BufferedReader dislikesIn = new BufferedReader(new FileReader(dislikesFilePath))){

            // initialize favorites
            String line = favoritesIn.readLine();
            while (line != null) {
                favorites.add(line);
                line = favoritesIn.readLine();
            }

            // initialize dislikes
            line = dislikesIn.readLine();
            while (line != null) {
                dislikes.add(line);
                line = dislikesIn.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Should check if the food matches anything in favorites
    // AND that no parts of the food are in dislikes
    public boolean isMatch(String item) {
        String[] foods = item.split(" ");

        // make sure no dislikes
        for (String food : foods) {
            if (dislikes.contains(food)) {
                return false;
            }
        }

        // check if contains any favorites
        for (String favorite : favorites) {
            if (item.contains(favorite)) {
                return true;
            }
        }

        return false;
    }
}
