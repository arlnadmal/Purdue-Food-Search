package org.example;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class Main {
    private static final String[] locations  = {"Hillenbrand", "Earhart", "Windsor", "Wiley", "Ford"};

    private static void runClient() {
        PageGenerator.open();
        for (String location : locations) {
            PageGenerator.startLoc(location);
            DiningAPIClient.parse(DiningAPIClient.findAtLocation(location));
            PageGenerator.endLoc();
        }
        PageGenerator.close();
        PageGenerator.generateFavorites();
        PageGenerator.generateDislikes();
        PageGenerator.generateMacros();
    }

    private static void openInBrowser() {
        try {
            File results = new File("results.html");
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(results.toURI());
            } else {
                System.out.println("Desktop API is not supported on this platform.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void main() {
        runClient();
        openInBrowser();
    }
}
