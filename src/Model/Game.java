package Model;

import java.awt.Color;
import java.util.Observable;

import Model.pieces.Piece;

public class Game extends Observable implements Runnable {

    private Grid grid;
    private boolean isRestarted = false;

    public Game(Grid grid) {
        this.grid = grid;

    }

    public void moveLeft() {
        grid.moveCurrentPieceLeft();
        grid.updateGrid();
        
        setChanged(); 
        notifyObservers(); 
    }

    public void moveRight() {
        grid.moveCurrentPieceRight();
        grid.updateGrid();

        setChanged(); 
        notifyObservers(); 
    }

    public void rotate() {
        grid.rotatePiece();
        grid.updateGrid();
        System.out.println(grid.getCurrentPiece().getActualDirection());
        setChanged();
        notifyObservers();
    }

    public void restart() {
        isRestarted = true;
        grid.restart();
        grid.updateGrid();
        grid.printGrid();
        setChanged(); 
        notifyObservers(); 
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

    public boolean isGameOver() {
        return grid.getIsGameOver();
    }

    public boolean isRestarted() {
        return isRestarted;
    }

    @Override
    public void run() {
        System.out.println("RUN");
        isRestarted = false;

        if (!isGameOver()) {
            Piece currenPiece = grid.getCurrentPiece();
            if (currenPiece == null) {
                currenPiece = grid.createPiece();
                
                grid.setCurrentPiece(currenPiece);
                
                
                
                grid.updateGrid();

                

                setChanged(); 
                notifyObservers(); 
                
                
                return;
            }
            if (grid.checkMoveDown()) {
                grid.moveCurrentPieceDown();
                grid.updateGrid();
            

                setChanged(); 
                notifyObservers();
                return;
            } else {
                grid.placeCurrentPiece();
                grid.removeLine();
                grid.updateGrid();
                
            }
            
        }
        setChanged(); 
        notifyObservers();

    }
}
