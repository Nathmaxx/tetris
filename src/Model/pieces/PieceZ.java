package Model.pieces;

public class PieceZ extends Piece {

	/** Position Nord */
	private final boolean[][] NORTH = new boolean[][] {
			{ false, true, true, false },
			{ false, false, true, true },
			{ false, false, false, false },
			{ false, false, false, false },
	};

	/** Position Est */
	private final boolean[][] EAST = new boolean[][] {
			{ false, false, false, true },
			{ false, false, true, true },
			{ false, false, true, false },
			{ false, false, false, false },
	};

	/**
	 * Constructeur de PieceT
	 * 
	 * @param x Position x de la pièce dans la grille
	 * @param y Position y de la pièce dans la grille
	 */
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
