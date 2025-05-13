import View.View;

import Model.Game;
import Model.Scheduler;

public class App {
    public static void main(String[] args) throws Exception {
        // Create the game model
        Game model = new Game(20, 10);

        // Create the view
        View vue = new View(model);

        Scheduler scheduler = new Scheduler(model);
        scheduler.start();
    }
}
