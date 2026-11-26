package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class PageGenerator {
    private static final String htmlTemplateOpen = "<!DOCTYPE html>\r\n"
            + "<html lang=\"en\">\r\n"
            + "<head>\r\n"
            + "    <meta charset=\"UTF-8\">\r\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial scale=1.0\">\r\n"
            + "    <title>Document</title>\r\n"
            + "</head>\r\n"
            + "<body>";
    private static final String htmlTemplateClose = "\r\n</body>\r\n" + "</html>";
    private static FileWriter writer = null;

    // gets the html ready to be written into
    public static void open() {
        try {
            writer = new FileWriter("results.html");
            writer.write(htmlTemplateOpen);
            writer.write(Tags.h1("Purdue Food Search"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // end the html file
    public static void close() {
        try {
            writer.write(htmlTemplateClose);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // starts the section for the current location
    public static void startLoc(String location) {
        try {
            writer.write("<h2>");
            writer.write(Tags.h2(location));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ends the section for the current location
    public static void endLoc(String location) {
        try {
            writer.write("</h2>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // writes the meal into the html file
    public static void writeMeal(String name) throws IOException {
        writer.write(Tags.h3(name));
    }

    public static void writeStatusClosed() throws IOException {
        writer.write(Tags.paragraph("Closed"));
    }

    public static void writeStation(String station) throws IOException {
        writer.write(Tags.h4(station));
    }

    public static void writeItem(String item) throws IOException {
        writer.write(Tags.li(item));
    }

    public static void startList() throws IOException {
        writer.write("<ul>");
    }

    public static void endList() throws IOException {
        writer.write("</ul>");
    }
}
