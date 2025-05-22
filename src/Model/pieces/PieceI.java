package Model.pieces;

import java.awt.Color;

public class PieceI extends Piece {
	/** Position Nord */
	private final boolean[][] NORTH = new boolean[][] {
			{ false, false, false, false },
			{ true, true, true, true },
			{ false, false, false, false },
			{ false, false, false, false },
	};

	/** Position Est */
	private final boolean[][] EAST = new boolean[][] {
			{ false, false, true, false },
			{ false, false, true, false },
			{ false, false, true, false },
			{ false, false, true, false },
	};

	/**
	 * 
	 * @param x
	 * @param y
	 */
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
		return NORTH;
	}

	@Override
	protected boolean[][] getWestShape() {
		return EAST;
	}

}
