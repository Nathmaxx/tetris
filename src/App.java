import View.View;

import Model.Game;
import Model.Grid;
import Model.Scheduler;

public class App {
    public static void main(String[] args) throws Exception {

        Grid grid = new Grid(22, 10);
        Game model = new Game(grid);

        new View(model);

        Scheduler scheduler = new Scheduler(model);
        scheduler.start();
    }
}
