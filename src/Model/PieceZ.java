package Model;

public class PieceZ extends Piece{
    private final boolean[][] NORTH = new boolean[][] {
            { true, true, false, false },
            { false, true, true, false },
            { false, false, false, false },
            { false, false, false, false },
    };

    private final boolean[][] WEST = new boolean[][] {
            { false, false, false, true },
            { false, false, true, true },
            { false, false, true, false },
            { false, false, false, false },
    };

    private final boolean[][] SOUTH = new boolean[][] {
            { false, false, false, false },
            { false, false, false, false },
            { true, true, false, false },
            { false, true, true, false },
    };

    private final boolean[][] EST = new boolean[][] {
            { false, true, false , false},
            { true ,true ,false ,false},
            { true ,false ,false ,false},
            { false ,false ,false ,false},
    };

    public PieceZ(int x,int y){
        super(x,y);
        this.color = java.awt.Color.RED;
        this.actualShape = NORTH;
    }

    @Override
    public void rotate(Side side) {
        // Z piece does not rotate
    }
    
}
