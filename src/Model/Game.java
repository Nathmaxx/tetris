package Model;

import java.awt.Color;

public class Game implements Runnable {

    private Grid grid;
    private int score;
    private int rows;
    private int cols;
    private Piece currentPiece;
    private boolean playing = false;

    public Game(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Grid(rows, cols);
        this.score = 0;
    }

    public boolean rowCompleted(int row) {
        for (int i = 0; i < cols; i++) {
            if (grid.getBox(row, i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean nextBox(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }
        return grid.getBox(row, col).isEmpty();
    }

    public void clearLine(int row) {
        for (int i = 0; i < cols; i++) {
            grid.getBox(row, i).setEmpty(true);
            grid.getBox(row, i).setColor(Color.WHITE);
        }
        score += 10;
    }

    public Grid getGrid() {
        return grid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Piece getNewPiece() {
        // Randomly generate a new piece
        // int randomPiece = (int) (Math.random() * 7);
        // switch (randomPiece) {
        // case 0:
        // currentPiece = new PieceL(0, 0);
        // break;
        // case 1:
        // currentPiece = new PieceT(0, 0);
        // break;
        // case 2:
        // currentPiece = new PieceI(0, 0);
        // break;
        // case 3:
        // currentPiece = new PieceO(0, 0);
        // break;
        // case 4:
        // currentPiece = new PieceS(0, 0);
        // break;
        // case 5:
        // currentPiece = new PieceZ(0, 0);
        // break;
        // case 6:
        // currentPiece = new PieceJ(0, 0);
        // break;
        // }
        currentPiece = new PieceL();

        return currentPiece;

    }

    @Override
    public void run() {
        if (!playing) {
            playing = true;
            currentPiece = getNewPiece();
        }
        System.out.println("Game is running");
    }
}
