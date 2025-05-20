package Model;

import java.util.Observable;

import Model.pieces.Piece;

public class Game extends Observable implements Runnable {

    private Grid grid;
    private boolean isRestarted = false;
    private boolean isPaused = false;

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
        setChanged();
        notifyObservers();
    }

    public void restart() {
        isRestarted = true;
        Score.resetScore();
        grid.restart();
        grid.updateGrid();
        grid.printGrid();
        setChanged();
        notifyObservers();
    }

    public void pause() {
        isPaused = true;
        setChanged();
        notifyObservers();
    }

    public void resume() {
        isPaused = false;
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

    public boolean isPaused() {
        return isPaused;
    }

    public int getScore() {
        return Score.getScore();
    }

    public void placePieceAtBottom() {
        while (grid.checkMoveDown()) {
            grid.moveCurrentPieceDown();
        }
        grid.updateGrid();

        grid.placeCurrentPiece();
        grid.removeLine();
        grid.updateGrid();
        setChanged();
        notifyObservers();
    }

    @Override
    public void run() {
        isRestarted = false;
        if (!isGameOver()) {
            Piece currentPiece = grid.getCurrentPiece();
            Piece nextPiece = grid.getNextPiece(0);
            Piece nextPiece2 = grid.getNextPiece(1);
            Piece nextPiece3 = grid.getNextPiece(2);

            if (currentPiece == null) {
                currentPiece = grid.createPiece();
                grid.setCurrentPiece(currentPiece);

                nextPiece = grid.createPiece();
                grid.setNextPiece(nextPiece);

                nextPiece2 = grid.createPiece();
                grid.setNextPiece2(nextPiece2);

                nextPiece3 = grid.createPiece();
                grid.setNextPiece3(nextPiece3);

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
