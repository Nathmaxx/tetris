package Model.Network;

import java.io.*;
import java.net.*;

public class Client {
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connected = false;

    public void connect(String serverAddress) {
        try {
            socket = new Socket(serverAddress, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            connected = true;
            System.out.println("Connecté au serveur !");

            // Lancer un thread pour recevoir les messages
            new Thread(this::receiveMessages).start();
        } catch (IOException e) {
            System.out.println("Impossible de se connecter au serveur");
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
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