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
        return NORTH;
    }

    @Override
    protected boolean[][] getEastShape() {
        return EAST;
    }

    @Override
    protected boolean[][] getSouthShape() {
        return SOUTH;
    }

    @Override
    protected boolean[][] getWestShape() {
        return WEST;
    }

}
