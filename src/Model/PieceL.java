package Model;

import java.awt.Color;

public class PieceL extends Piece {

    public PieceL(int startX, int startY) {
        super(startX, startY);
        shape = new boolean[][] {
                { false, false, true, false },
                { false, false, true, false },
                { false, false, true, false },
                { false, false, true, true },
        };
    }

    public void rotate(Side side) {
        if (side == Side.RIGHT) {
            rotateRight();
        } else {
            rotateLeft();
        }
    }

    public void rotateLeft() {

    }

    public void rotateRight() {

    }
}
