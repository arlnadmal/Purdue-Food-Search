package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

/**
 * DiningAPIClient.java
 *
 * Handles the requests to Purdue Dining API
 *
 * @author Arln Admal
 * @version Nov 14, 2026
 */
public class DiningAPIClient {
    private final String baseURL = "https://api.hfs.purdue.edu/menus/v2/locations/";
    private final String[] locations  = {"Hillenbrand", "Earhart", "Windsor", "Wiley", "Ford"};
    private final HttpClient client;

    public DiningAPIClient() {
        client = HttpClient.newHttpClient();
    }

    // Get all the data from a specific dining hall
    public String findAtLocation(String location) {
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
    public String getDate() {
        LocalDate today = LocalDate.now();
        return today.toString();
    }
}
