package Model;

import java.awt.Color;
import java.util.Observable;

public class Game extends Observable implements Runnable {

    private Grid grid;


    public Game(Grid grid) {
        this.grid = grid;

    }
    
    public Grid getGrid() {
        return this.grid;
    }

    public int getRows() {
        return grid.getRows();
    }

    public int getCols() {
        return grid.getCols();
    }

    @Override
    public void run() {
        Piece currenPiece = grid.getCurrentPiece();
        if (currenPiece == null) {
            currenPiece = new PieceL(2, 0);
            grid.setCurrentPiece(currenPiece);
            grid.updateGrid();

            setChanged(); 
            notifyObservers(); 
            return;
        }
        if (grid.checkMoveDown()) {
            grid.moveCurrentPieceDown();
        }

        grid.updateGrid();
        grid.printGrid();

        setChanged(); 
        notifyObservers(); 



        System.out.println("Game is running");
    }
}
