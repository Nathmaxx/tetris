package Model.Network;

import java.io.*;
import java.net.*;

import Model.Score;

public class Client {
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connected = false;
    private String serverAddress = "10.42.179.250";

    public void connect() {
        try {
            socket = new Socket(serverAddress, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            connected = true;
            System.out.println("Connecté au serveur !");

            new Thread(this::receiveMessages).start();
        } catch (IOException e) {
            System.out.println("Impossible de se connecter au serveur");
            e.printStackTrace();
        }
    }

    public void sendScore() {
        if (out != null) {
            out.println("SCORE:" + Score.getScore());
        }
    }

    private void receiveMessages() {
        try {
            String message;
            while (connected && (message = in.readLine()) != null) {
                handleMessage(message);
            }
        } catch (IOException e) {
            if (connected) {
                System.out.println("Connexion perdue");
                e.printStackTrace();
            }
        } finally {
            disconnect();
        }
    }

    private void handleMessage(String message) {
        // Pour l'instant, juste un log
        System.out.println("Message reçu: " + message);
        // Plus tard, vous pourriez traiter différents types de messages
    }

    public void disconnect() {
        connected = false;
        try {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connected;
    }
}