package Model;

import java.util.Observable;

import Model.pieces.Piece;

/** Classe contenant toute la logique du jeu tetris */
public class Game extends Observable implements Runnable {

    /** La grille du jeu */
    private Grid grid;

    /** Etat de démarrage du jeu */
    private boolean isRestarted = false;

    /** Etat de pause du jeu */
    private boolean isPaused = false;

    /** Le score d'un adversaire en cas de partie en réseau */
    private int opponentScore = 0;

    /** Information sur la situation de l'adversaire en cas de partie en réseau */
    private String opponentMessage = "Attente de l'adversaire...";

    /**
     * Constructeur de la classe Game
     * 
     * @param grid la grille de jeu
     */
    public Game(Grid grid) {
        this.grid = grid;
        Score.loadBestScore(); // Charger le meilleur score au démarrage
    }

    /** Permet de mettre à jour la grille avec la projection des pièces */
    public void updateGridWithProjection() {
        grid.updateGrid();
        grid.showProjection();
        setChanged();
        notifyObservers();
    }

    /** Déplace la pièce à gauche */
    public void moveLeft() {
        grid.moveCurrentPieceLeft();
        updateGridWithProjection();
    }

    /** Déplace la pièce à droite */
    public void moveRight() {
        grid.moveCurrentPieceRight();
        updateGridWithProjection();
    }

    /** Tourne la pièce actuelle vers la droite */
    public void rotate() {
        grid.rotatePiece();
        updateGridWithProjection();
    }

    /** Débute une partie de tetris */
    public void startGame() {
        isRestarted = false;
        isPaused = false;
        Score.resetScore();
        opponentScore = 0;
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

    /** Recommence une partie de tetris */
    public void restart() {
        isRestarted = true;
        isPaused = false;
        Score.resetScore();
        opponentScore = 0;
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

    /** Met le jeu sur pause */
    public void pause() {
        isPaused = true;
        setChanged();
        notifyObservers();
    }

    /** Continue le jeu après une pause */
    public void resume() {
        isPaused = false;
        setChanged();
        notifyObservers();
    }

    /** Permet de placer une pièce instantanément dans la grille (touche espace) */
    public void placePieceAtBottom() {
        while (grid.checkMoveDown()) {
            grid.moveCurrentPieceDown();
        }
        grid.updateGrid();
        grid.placeCurrentPiece();
        grid.removeLine();
        updateGridWithProjection();
    }

    /** Contient toute la logique de déroulement du jeu de tetris */
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

    /* Getteurs et setteurs */

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

    public int getLevel() {
        return Score.getLevel();
    }

    public void setPause(boolean pause) {
        this.isPaused = pause;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public String getOpponentMessage() {
        return this.opponentMessage;
    }

    public void setOpponentScore(int score) {
        this.opponentScore = score;
        setChanged();
        notifyObservers();
    }

    public void setOpponentMessage(String message) {
        this.opponentMessage = message;
        setChanged();
        notifyObservers();
    }
}
