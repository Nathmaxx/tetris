package Model.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ServerSocket serverSocket;
    private final ArrayList<ClientHandler> clients = new ArrayList<>();

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Serveur lancé sur le port " + port);

            while (clients.size() < 2) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket, this);
                clients.add(client);
                new Thread(client).start();
                System.out.println("Nouveau client connecté. Total clients : " + clients.size());

                if (clients.size() == 2) {
                    System.out.println("Deux joueurs connectés. Démarrage de la partie...");
                    broadcastMessage("START");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public void broadcastMessage(String message, ClientHandler excludeClient) {
        for (ClientHandler client : clients) {
            if (client != excludeClient) {
                client.sendMessage(message);
            }
        }
    }

    public void receiveValue(int value, ClientHandler client) {
        String formattedScore = "SCORE:" + value;
        broadcastMessage(formattedScore, client);
        System.out.println("Score broadcast: " + formattedScore);
    }

    public void receiveEndGame(ClientHandler client) {
        broadcastMessage("ENDGAME", client);
    }

    public void deleteClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Client déconnecté. Total clients : " + clients.size());
    }

}
