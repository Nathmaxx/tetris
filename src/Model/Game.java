package Model;

import java.awt.Color;
import java.util.Observable;

import Model.pieces.Piece;

public class Game extends Observable implements Runnable {

    private Grid grid;

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
            currenPiece = grid.createPiece();
            grid.setCurrentPiece(currenPiece);
            grid.updateGrid();

            setChanged();
            notifyObservers();
            return;
        }
        if (grid.checkMoveDown()) {
            grid.moveCurrentPieceDown();
        } else {
            grid.placeCurrentPiece();

            Piece newPiece = grid.createPiece();
            grid.setCurrentPiece(newPiece);

            if (!grid.canPlacePiece(newPiece)) {
                System.out.println("Game Over!");
            }
        }

        grid.removeLine();

        grid.updateGrid();
        // grid.printGrid();

        setChanged();
        notifyObservers();

        System.out.println("Game is running");
    }
}
