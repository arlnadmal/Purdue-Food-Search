package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * DiningAPIClient.java
 *
 * Handles the requests to Purdue Dining API and parses responses
 *
 * @author Arln Admal
 * @version Nov 14, 2026
 */
public class DiningAPIClient {
    private static final String baseURL = "https://api.hfs.purdue.edu/menus/v2/locations/";
    private static final String itemBaseURL = "https://api.hfs.purdue.edu/menus/v2/items/";
    private static final HashMap<String, JsonNode> itemDetails = new HashMap<>();
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Get all the data from a specific dining hall
    public static String findAtLocation(String location) {
        String url = baseURL + location + "/" + getDate();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }

    // Get the today's date
    private static String getDate() {
        LocalDate today = LocalDate.now();
        //return today.toString();
        return "2026-11-14"; // for testing purposes
    }

    // parse should iterate through meals, check if each meal is closed
    // IF CLOSED -> just print the name of the meal (Lunch, Dinner, etc.)
    // IF NOT CLOSED -> :
    // Iterate through station
    // Iterate through item in station
    // If match -> Print
    public static void parse(String src) {
        Matcher matcher = new Matcher();
        try {
            JsonNode root = objectMapper.readTree(src);
            JsonNode meals = root.get("Meals");
            for (JsonNode meal : meals) {
                PageGenerator.writeMeal(getName(meal));
                if (meal.get("Status").asText().equals("Closed")) {
                    PageGenerator.writeStatusClosed();
                    continue;
                }
                HashSet<String> displayedMeals = new HashSet<>(); // Ensure the same meal isn't displayed twice
                JsonNode stations = meal.get("Stations");
                PageGenerator.startList();
                for (JsonNode station : stations) {
                    JsonNode items = station.get("Items");
                    for (JsonNode item : items) {
                        String foodName = getName(item);

                        // Check if in favorites & not in dislikes and has not been displayed previously
                        if (matcher.isMatch(foodName) && !displayedMeals.contains(foodName)) {
                            String id = item.get("ID").asText();
                            displayedMeals.add(foodName);
                            PageGenerator.writeItem(foodName, id);
                        }
                    }
                }
                PageGenerator.endList();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonNode getItemDetails(String id) {
        if (itemDetails.containsKey(id)) {
            return itemDetails.get(id);
        }
        String url = itemBaseURL + id;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode details = objectMapper.readTree(response.body());
            itemDetails.put(id, details);
            return details;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // return the name of given node
    private static String getName(JsonNode node) {
        return node.get("Name").asText();
    }
}