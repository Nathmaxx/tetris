package Model.pieces;

import java.awt.Color;

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
