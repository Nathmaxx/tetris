package Model;

import java.util.Observable;

import Model.pieces.Piece;

public class Game extends Observable implements Runnable {

    private Grid grid;
    private boolean isRestarted = false;
    private boolean isPaused = false;

    public Game(Grid grid) {
        this.grid = grid;
        Score.loadBestScore(); // Charger le meilleur score au démarrage
    }

    public void updateGridWithProjection() {
        grid.updateGrid();
        grid.showProjection(); // Affiche la projection de la pièce
        setChanged();
        notifyObservers();
    }

    public void moveLeft() {
        grid.moveCurrentPieceLeft();
        updateGridWithProjection();
    }

    public void moveRight() {
        grid.moveCurrentPieceRight();
        updateGridWithProjection();
    }

    public void rotate() {
        grid.rotatePiece();
        updateGridWithProjection();
        System.out.println(grid.getCurrentPiece().getActualDirection());
    }

    public void startGame() {
        isRestarted = false;
        isPaused = false;
        Score.resetScore();
        grid.restart();
        Piece currentPiece = grid.createPiece();
        grid.setCurrentPiece(currentPiece);

        Piece nextPiece = grid.createPiece();
        grid.setNextPiece(nextPiece);

        Piece nextPiece2 = grid.createPiece();
        grid.setNextPiece2(nextPiece2);

        Piece nextPiece3 = grid.createPiece();
        grid.setNextPiece3(nextPiece3);
        grid.updateGrid();
        grid.printGrid();
        setChanged();
        notifyObservers();
    }

    public void restart() {
        isRestarted = true;
        isPaused = false;
        Score.resetScore();
        grid.restart();

        Piece currentPiece = grid.createPiece();
        grid.setCurrentPiece(currentPiece);

        Piece nextPiece = grid.createPiece();
        grid.setNextPiece(nextPiece);

        Piece nextPiece2 = grid.createPiece();
        grid.setNextPiece2(nextPiece2);

        Piece nextPiece3 = grid.createPiece();
        grid.setNextPiece3(nextPiece3);
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

    public void setPause(boolean pause) {
        this.isPaused = pause;
    }

    public void placePieceAtBottom() {
        while (grid.checkMoveDown()) {
            grid.moveCurrentPieceDown();
        }
        grid.updateGrid();
        grid.placeCurrentPiece();
        grid.removeLine();
        updateGridWithProjection();
    }

    @Override
    public void run() {
        System.out.println("RUN");
        isRestarted = false;
        System.out.println(" pause : " + isPaused);
        System.out.println(" game over : " + isGameOver());
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
                updateGridWithProjection();
                System.out.println(Score.getScore());
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
