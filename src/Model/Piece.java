package Model;

public abstract class Piece {

    protected int x;
    protected int y;

    protected boolean[][] shape;

    public Piece(int startX, int startY) {
        this.x = startX;
        this.y = startY;
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
    }

    public void moveLeft() {
    }

    public void moveRight() {
    }

    public abstract void rotate();
}
