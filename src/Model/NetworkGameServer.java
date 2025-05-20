package Model;

import Model.Network.Client;
import Model.Network.Server;

public class NetworkGameServer extends Game {

    private Client client;
    private Server server;

    public NetworkGameServer(Grid grid, Client client) {
        super(grid);
        this.client = client;
        this.server = new Server();
    }

}
