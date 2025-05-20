package Model;

import Model.Network.Client;

public class NetworkGame extends Game {

    private Client client;

    public NetworkGame(Grid grid) {
        super(grid);
        this.client = new Client();
    }

    public void connectClient(String adress) {
        this.client.connect();
    }

    public void createServer() {

    }

    // 10.42.179.250
}
