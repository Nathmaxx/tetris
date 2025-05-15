package Model.pieces;

public class PieceS extends Piece {
	private final boolean[][] NORTH = new boolean[][] {
			{ false, false, true, true },
			{ false, true, true, false },
			{ false, false, false, false },
			{ false, false, false, false },
	};

	private final boolean[][] WEST = new boolean[][] {
			{ false, false, false, false },
			{ false, false, true, false },
			{ false, false, true, true },
			{ false, false, false, true },
	};

	private final boolean[][] SOUTH = new boolean[][] {
			{ false, false, false, false },
			{ false, false, false, false },
			{ false, true, true, false },
			{ true, true, false, false },
	};

	private final boolean[][] EAST = new boolean[][] {
			{ true, false, false, false },
			{ true, true, false, false },
			{ false, true, false, false },
			{ false, false, false, false },
	};

	public PieceS(int x, int y) {
		super(x, y);
		this.color = java.awt.Color.GREEN;
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
