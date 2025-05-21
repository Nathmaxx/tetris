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

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket, this);
                clients.add(client);
                new Thread(client).start();
                System.out.println("Nouveau client connecté. Total clients : " + clients.size());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveValue(double valeur, ClientHandler expediteur) {
        System.out.println("Valeur reçue d'un client : " + valeur);
    }

    public void deleteClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Client déconnecté. Total clients : " + clients.size());
    }

}
