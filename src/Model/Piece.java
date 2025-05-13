package Model;

import java.awt.Color;

public abstract class Piece {

    protected int x;
    protected int y;
    protected Color color;

    protected boolean[][] shape;

    public Piece(int startX, int startY, Color color) {
        this.x = startX;
        this.y = startY;
        this.color = color;
    }

    public boolean[][] getShape() {
        return this.shape;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveDown() {
        y--;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public abstract void rotate(Side side);
}
