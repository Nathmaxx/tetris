package Model;

import java.awt.Color;

public class Grid {
    private Box[][] boxes;
    private int rows;
    private int cols;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        boxes = new Box[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boxes[i][j].setEmpty(false);
            }
        }
    }

    public boolean getBoxEmpty(int row, int col) {
        return boxes[row][col].isEmpty();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Box getBox(int row, int col) {
        return boxes[row][col];
    }

    public void addPiece(Piece piece) {
        boolean[][] shape = piece.getShape();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j]) {
                    boxes[i][j + 2].setEmpty(shape[i][j]);
                    boxes[i][j + 2].setColor(Color.RED);
                }
            }
        }
    }

}
