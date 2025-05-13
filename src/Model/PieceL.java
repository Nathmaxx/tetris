package Model;

import java.util.HashMap;

public class PieceL extends Piece {

    private HashMap<Direction, boolean[][]> shapes = new HashMap<>();

    private final boolean[][] NORTH = new boolean[][] {
            { false, false, true, false },
            { false, false, true, false },
            { false, false, true, true },
            { false, false, false, false },

    };

    private final boolean[][] SOUTH = new boolean[][] {
            { false, true, true, false },
            { false, false, true, false },
            { false, false, true, false },
            { false, false, false, false },

    };

    private final boolean[][] EAST = new boolean[][] {
            { false, false, false, false },
            { false, true, true, true },
            { false, true, false, false },
            { false, false, false, false },

    };

    private final boolean[][] WEST = new boolean[][] {
            { false, false, true, true },
            { false, true, true, true },
            { false, true, false, false },
            { false, false, false, false },

    };

    public PieceL(int startX, int startY) {
        super(startX, startY);
        actualShape = NORTH;
        shapes.put(Direction.NORTH, NORTH);
        shapes.put(Direction.SOUTH, SOUTH);
        shapes.put(Direction.WEST, WEST);
        shapes.put(Direction.EAST, EAST);
    }

    public void rotate(Side side) {
        if (side == Side.RIGHT) {
            rotateRight();
        } else {
            rotateLeft();
        }
    }

    public void rotateLeft() {
        if (actualShape == NORTH) {
            actualShape = EAST;
        } else if (actualShape == EAST) {
            actualShape = SOUTH;
        } else if (actualShape == SOUTH) {
            actualShape = WEST;
        } else if (actualShape == WEST) {
            actualShape = NORTH;
        }
    }

    public void rotateRight() {
        if (actualShape == NORTH) {
            actualShape = WEST;
        } else if (actualShape == WEST) {
            actualShape = SOUTH;
        } else if (actualShape == SOUTH) {
            actualShape = EAST;
        } else if (actualShape == EAST) {
            actualShape = NORTH;
        }
    }
}
