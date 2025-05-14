package Model;

import java.util.ArrayList;

public abstract class Piece {

    protected boolean[][] actualShape;

    protected int x;
    protected int y;

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean[][] getShape() {
        return this.actualShape;
    }

    public Integer[] maxDownIndex() {
        Integer[] maxIndices = new Integer[4];

        for (int col = 0; col < 4; col++) {
            for (int row = 3; row >= 0; row--) {
                if (actualShape[row][col]) {
                    maxIndices[col] = row;
                    break;
                }
            }
        }

        return maxIndices;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract void rotate(Side side);
}
