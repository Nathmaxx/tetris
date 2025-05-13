package Model;

import java.awt.Color;

public class Game {

    private Grid grid;
    private int score;
    private int rows;
    private int cols;

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

}
