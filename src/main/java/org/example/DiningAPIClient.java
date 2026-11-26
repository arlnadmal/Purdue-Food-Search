package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
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
                IO.println("=========");
                IO.println(getName(meal));
                if (meal.get("Status").asText().equals("Closed")) {
                    IO.println("Closed");
                    IO.println("=========");
                    continue;
                }
                JsonNode stations = meal.get("Stations");
                for (JsonNode station : stations) {
                    IO.println("--------");
                    IO.println(getName(station));
                    JsonNode items = station.get("Items");
                    for (JsonNode item : items) {
                        String foodName = getName(item);
                        if (matcher.isMatch(foodName)) {
                            IO.println(foodName);
                        }
                    }
                    IO.println("--------");

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // return the name of given node
    private static String getName(JsonNode node) {
        return node.get("Name").asText();
    }
}