package org.example;

public class Main {
    private static final String[] locations  = {"Hillenbrand", "Earhart", "Windsor", "Wiley", "Ford"};

    static void main() {
        for (String location : locations) {
            IO.println(location);
            IO.println("+++++++++++++\n*************");
            DiningAPIClient.parse(DiningAPIClient.findAtLocation(location));
            IO.println("+++++++++++++\n*************");
        }
    }
}
