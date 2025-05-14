package Model;

import java.awt.Color;

public abstract class Piece {

    protected Color color;

    protected boolean[][] actualShape;

    public Piece() {
    }

    public boolean[][] getShape() {
        return this.actualShape;
    }

    public abstract void rotate(Side side);
}
