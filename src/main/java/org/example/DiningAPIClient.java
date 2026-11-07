package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DiningAPIClient {
    private final String baseURL = "https://api.hfs.purdue.edu/menus/v2/locations/";
    private final HttpClient client;

    public DiningAPIClient() {
        client = HttpClient.newHttpClient();
    }

    // Get all the data from the URL
    // edit this method to take an input of a string that represents the location and date so it will get
    // data from specific location and date
    public String findAll() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseURL)).GET().build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }
}
