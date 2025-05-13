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
                boxes[i][j] = new Box(i, j, Color.WHITE);
            }
        }
    }
    
    public Box getBox(int row, int col) {
        return boxes[row][col];
    }

   
}
