package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

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
    private static final Matcher matcher = new Matcher();
    private static final HashMap<String, String> macrosSections = new HashMap<>();

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
            writer.write("<a href=\"favorites.html\">"
                        + Tags.button("Show Favorites")
                        + "</a>");
            writer.write("<a href=\"dislikes.html\">"
                        + Tags.button("Show Dislikes")
                        + "</a>");
            writer.write(htmlTemplateClose);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateFavorites() {
        try (FileWriter favWriter = new FileWriter("favorites.html")) {
            favWriter.write(htmlTemplateOpen);
            favWriter.write(Tags.h1("Favorites"));
            favWriter.write("<ul>");
            for (String favorite : matcher.getFavorites()) {
                favWriter.write(Tags.li(favorite));
            }
            if (matcher.getFavorites().isEmpty()) {
                favWriter.write(Tags.li("None"));
            }
            favWriter.write("</ul>");
            favWriter.write(htmlTemplateClose);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateDislikes() {
        try (FileWriter dislikesWriter = new FileWriter("dislikes.html")) {
            dislikesWriter.write(htmlTemplateOpen);
            dislikesWriter.write(Tags.h1("Dislikes"));
            dislikesWriter.write("<ul>");
            for (String dislike : matcher.getDislikes()) {
                dislikesWriter.write(Tags.li(dislike));
            }
            if (matcher.getDislikes().isEmpty()) {
                dislikesWriter.write(Tags.li("None"));
            }
            dislikesWriter.write("</ul>");
            dislikesWriter.write(htmlTemplateClose);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateMacros() {
        try (FileWriter macrosWriter = new FileWriter("macros.html")) {
            macrosWriter.write(htmlTemplateOpen);
            macrosWriter.write("<style>section{display:none} section:target{display:block}</style>");
            for (String section : macrosSections.values()) {
                macrosWriter.write(section);
            }
            macrosWriter.write(htmlTemplateClose);
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
    public static void endLoc() {
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

    public static void writeItem(String item, String id) throws IOException {
        String link = "macros.html#" + id;
        writer.write(Tags.li(Tags.link(link, item)));
        if (!macrosSections.containsKey(id)) {
            macrosSections.put(id, createMacroSection(item, id, DiningAPIClient.getItemDetails(id)));
        }
    }

    private static String createMacroSection(String name, String id, JsonNode details) {
        StringBuilder section = new StringBuilder();
        section.append("<section id=\"" + id + "\">");
        String header = "Nutrition Facts for " + name;
        section.append(Tags.h1(header));
        if (details == null || details.get("Nutrition") == null) {
            section.append(Tags.paragraph("No Nutrition Facts Available"));
        } else {
            JsonNode nutritionFacts = details.get("Nutrition");
            section.append("<table>");

            for (JsonNode fact : nutritionFacts) {
                section.append("<tr><td>" + fact.get("Name").asText("Unavailable") + "</td><td>");
                section.append(fact.get("LabelValue").asText("Unavailable") + "</td></tr>");
            }
            section.append("</table>");
        }
        section.append("</section>");

        return section.toString();
    }

    public static void startList() throws IOException {
        writer.write("<ul>");
    }

    public static void endList() throws IOException {
        writer.write("</ul>");
    }
}
