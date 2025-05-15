package Model.pieces;

import java.awt.Color;

import Model.Side;

public class PieceO extends Piece {

    private final boolean[][] SHAPE = new boolean[][] {
            { false, false, true, true },
            { false, false, true, true },
            { false, false, false, false },
            { false, false, false, false },
    };

    public PieceO(int x, int y) {
        super(x, y);
        this.color = Color.YELLOW;
        this.actualShape = SHAPE;
    }

    @Override
    public void rotate(Side side) {
        // O piece does not rotate
    }

}
