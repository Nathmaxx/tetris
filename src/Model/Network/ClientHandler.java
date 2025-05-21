package Model.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Server server;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String line;

            while ((line = in.readLine()) != null) {
                try {
                    double value = Double.parseDouble(line);
                    server.receiveValue(value, this);
                } catch (NumberFormatException e) {
                    System.out.println("Donnée non numérique reçue : " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur client : " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    private void closeConnection() {
        try {
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.deleteClient(this);
    }
}