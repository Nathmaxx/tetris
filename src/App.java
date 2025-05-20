import View.View;

import Model.Game;
import Model.Grid;
import Model.Scheduler;
import Model.Network.Client;
import Model.Network.Server;

public class App {
    public static void main(String[] args) throws Exception {

        Grid grid = new Grid(22, 10);
        Game model = new Game(grid);

        new View(model);

        Server server = new Server();
        server.start();

        Client client = new Client();
        client.connect("localhost");

        Scheduler scheduler = new Scheduler(model);
        scheduler.start();
    }
}
