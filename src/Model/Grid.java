package Model;

import java.awt.Color;

public class Grid {
    private Box[][] boxes;
    private int rows;
    private int cols;
    private Piece currentPiece;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        boxes = new Box[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boxes[i][j] = new Box(i, j, Color.WHITE);
                boxes[i][j].setEmpty(true);
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

    public void setCurrentPiece(Piece piece) {
        this.currentPiece = piece;        
    }

    public void moveCurrentPieceDown() {
        currentPiece.setY(currentPiece.getY() + 1);
    }


    public void updateGrid() {
        boolean[][] shape = currentPiece.getShape();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j]) {
                    boxes[currentPiece.getY() + i][currentPiece.getX() + j].setEmpty(shape[i][j]);
                    boxes[currentPiece.getY() + i][currentPiece.getX() + j].setColor(Color.RED);
                }
            }
        }
    }
}
