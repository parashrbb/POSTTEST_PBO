package app;

import server.HttpServerApp;

public class Main {

    public static void main(String[] args) {

        // Membuat object server
        HttpServerApp server = new HttpServerApp();

        // Menjalankan server
        server.startServer();

    }
}