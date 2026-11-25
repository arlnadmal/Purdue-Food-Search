package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Matcher {
    private HashSet<String> favorites;
    private String filePath = "src/main/java/org/example/favorites.txt";

    // initialize favorites array by reading from the text file
    public Matcher() {
        favorites = new HashSet<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            String line = in.readLine();
            while (line != null) {
                favorites.add(line);
                line = in.readLine();
            }
        } catch (IOException e) {

        }
        IO.println(favorites.size());
    }

    public boolean isMatch(String food) {
        return favorites.contains(food);
    }
}
