package org.example;

public class Tags {
    public static String h1(String text) {
        return "<h1>" + text + "</h1>";
    }

    public static String h2(String text) {
        return "<h2>" + text + "</h2>";
    }

    public static String h3(String text) {
        return "<h3>" + text + "</h3>";
    }

    public static String link(String link, String text) {
        return "<a href=\"" + link + "\">" + text + "</a>";
    }

    public static String li(String text) {
        return "<li>" + text + "</li>";
    }

    public static String button(String text) {
        return "<button>" + text + "</button>";
    }

    public static String paragraph(String text) {
        return "<p>" + text + "</p>";
    }
}
