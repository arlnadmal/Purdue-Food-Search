package org.example;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
/**
 * Main.java
 *
 * Runs the entire program
 *
 * @author Arln Admal
 * @version Nov 27, 2026
 */
public class Main {
    private static final String[] locations  = {"Hillenbrand", "Earhart", "Windsor", "Wiley", "Ford"};

    // runs the DiningAPIClient and generates output files
    private static void runClient() {
        // generate results page
        PageGenerator.open();
        for (String location : locations) {
            PageGenerator.startLoc(location);
            DiningAPIClient.parse(DiningAPIClient.findAtLocation(location));
            PageGenerator.endLoc();
        }
        PageGenerator.close();
        
        // generate all other html pages
        PageGenerator.generateFavorites();
        PageGenerator.generateDislikes();
        PageGenerator.generateMacros();
    }

    // Opens results.html in the user's browser
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
