package Model.pieces;

public class PieceZ extends Piece {
	private final boolean[][] NORTH = new boolean[][] {
			{ false, true, true, false },
			{ false, false, true, true },
			{ false, false, false, false },
			{ false, false, false, false },
	};

	private final boolean[][] EAST = new boolean[][] {
			{ false, false, false, true },
			{ false, false, true, true },
			{ false, false, true, false },
			{ false, false, false, false },
	};

	public PieceZ(int x, int y) {
		super(x, y);
		this.color = java.awt.Color.RED;
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
		return NORTH;
	}

	@Override
	protected boolean[][] getWestShape() {
		return EAST;
	}

}
