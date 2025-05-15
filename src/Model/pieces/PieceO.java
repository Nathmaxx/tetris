package Model.pieces;

import java.awt.Color;

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
    protected boolean[][] getNorthShape() {
        return SHAPE;
    }

    @Override
    protected boolean[][] getEastShape() {
        return SHAPE;
    }

    @Override
    protected boolean[][] getSouthShape() {
        return SHAPE;
    }

    @Override
    protected boolean[][] getWestShape() {
        return SHAPE;
    }

}
