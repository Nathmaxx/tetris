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
                if (line.startsWith("SCORE:")) {
                    try {
                        int score = Integer.parseInt(line.substring(6));
                        server.receiveValue(score, this);
                        System.out.println("Score reçu du client: " + score);
                    } catch (NumberFormatException e) {
                        System.out.println("Format de score invalide: " + line);
                    }
                } else if (line.equals("ENDGAME")) {
                    try {
                        server.receiveEndGame(this);
                    } catch (Exception e) {
                        System.out.println("Erreur de réception de fin de partie");
                    }
                } else if (line.equals("WINGAME")) {
                    try {
                        server.receiveWinGame(this);
                    } catch (Exception e) {
                        System.out.println("Erreur de réception de victoire de partie");
                    }
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
            System.out.println("Message envoyé au client: " + message);
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