package Model.pieces;

import java.awt.Color;

/** Piece O du jeu tetris */
public class PieceO extends Piece {

    /** Forme pour toute les directions */
    private final boolean[][] SHAPE = new boolean[][] {
            { false, false, true, true },
            { false, false, true, true },
            { false, false, false, false },
            { false, false, false, false },
    };

    /**
     * Constructeur de PieceO
     * 
     * @param x Position x de la pièce dans la grille
     * @param y Position y de la pièce dans la grille
     */
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
