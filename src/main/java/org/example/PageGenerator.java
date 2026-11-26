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

    public static void generate() {
        try (FileWriter writer = new FileWriter("results.html")) {
            writer.write(htmlTemplateOpen);

            // add all the results
            writer.write(Tags.h1("Purdue Food Search"));

            writer.write(htmlTemplateClose);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
