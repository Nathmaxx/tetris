package Model.pieces;

import java.awt.Color;

import Model.Side;

public class PieceI extends Piece {
        private final boolean[][] NORTH = new boolean[][] {
                        { true, true, true, true },
                        { false, false, false, false },
                        { false, false, false, false },
                        { false, false, false, false },
        };

        private final boolean[][] SOUTH = new boolean[][] {
                        { false, false, false, false },
                        { false, false, false, false },
                        { true, true, true, true },
                        { false, false, false, false },
        };

        private final boolean[][] EAST = new boolean[][] {
                        { false, false, true, false },
                        { false, false, true, false },
                        { false, false, true, false },
                        { false, false, true, false },
        };

        private final boolean[][] WEST = new boolean[][] {
                        { false, false, false, true },
                        { false, false, false, true },
                        { false, false, false, true },
                        { false, false, false, true },
        };

        public PieceI(int x, int y) {
                super(x, y);
                this.color = Color.CYAN;
                this.actualShape = EAST;
        }

        @Override
        public void rotate(Side side) {
                // TODO Auto-generated method stub
        }

}
