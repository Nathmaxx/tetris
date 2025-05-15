package Model.pieces;

public class PieceT extends Piece {
	private final boolean[][] NORTH = new boolean[][] {
			{ false, false, true, false },
			{ false, true, true, true },
			{ false, false, false, false },
			{ false, false, false, false },
	};

	private final boolean[][] WEST = new boolean[][] {
			{ false, false, true, false },
			{ false, false, true, true },
			{ false, false, true, false },
			{ false, false, false, false },
	};

	private final boolean[][] SOUTH = new boolean[][] {
			{ false, false, false, false },
			{ false, true, true, true },
			{ false, false, true, false },
			{ false, false, false, false },
	};

	private final boolean[][] EAST = new boolean[][] {
			{ false, false, false, false },
			{ false, false, true, false },
			{ false, true, true, false },
			{ false, false, true, false },

	};

	public PieceT(int x, int y) {
		super(x, y);
		this.color = java.awt.Color.MAGENTA;
		this.actualShape = NORTH;
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
