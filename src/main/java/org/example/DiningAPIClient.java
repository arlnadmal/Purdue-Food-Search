//package org.example;

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
 * Handles the requests to Purdue Dining API
 *
 * @author Arln Admal
 * @version Nov 14, 2026
 */
public class DiningAPIClient {
    private static final String baseURL = "https://api.hfs.purdue.edu/menus/v2/locations/";
    private static final String[] locations  = {"Hillenbrand", "Earhart", "Windsor", "Wiley", "Ford"};
    private static final HttpClient client = HttpClient.newHttpClient();
    private static ObjectMapper objectMapper = new ObjectMapper();

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
    // Get name of food
    public static void parse(String src) {
        try {
            JsonNode root = objectMapper.readTree(src);
            JsonNode meals = root.get("Meals");
            for (JsonNode meal : meals) {
                IO.println(meal.get("Name").asText());
                if (meal.get("Status").asText().equals("Closed")) continue;
                JsonNode stations = meal.get("Stations");
                for (JsonNode station : stations) {
                    IO.println("--------");
                    IO.println(station.get("Name").asText());
                    JsonNode items = station.get("Items");
                    for (JsonNode item : items) {
                        IO.println(item.get("Name").asText());
                    }
                }
            }
        } catch (IOException e) {

        }
    }
}

void main() {
    DiningAPIClient.parse(DiningAPIClient.findAtLocation("Windsor"));
}