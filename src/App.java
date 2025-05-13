import View.View;

import Model.Game;

public class App {
    public static void main(String[] args) throws Exception {
        // Create the game model
        Game model = new Game(20, 10);

        // Create the view
        View vue = new View(model);

        // Set the frame to be visible
        

        // Add a box to the grid
        // model.getGrid().getBox(0, 0).setColor(java.awt.Color.RED);
        // model.getGrid().getBox(0, 0).setEmpty(false);

        // Update the view with the model data
        // vue.updateView(model);
    }
}
