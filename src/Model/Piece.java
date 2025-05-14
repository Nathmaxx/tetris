package Model;

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
