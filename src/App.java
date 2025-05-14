import View.View;

import Model.Game;
import Model.Grid;
import Model.Scheduler;

public class App {
    public static void main(String[] args) throws Exception {
        Grid grid = new Grid(20, 10);
        
        // Create the game model
        Game model = new Game(grid);

        // Create the view
        View vue = new View(model);

        Scheduler scheduler = new Scheduler(model);
        scheduler.start();
    }
}
