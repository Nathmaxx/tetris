package Model.pieces;

public class PieceS extends Piece {

	/** Direction Nord */
	private final boolean[][] NORTH = new boolean[][] {
			{ false, false, true, true },
			{ false, true, true, false },
			{ false, false, false, false },
			{ false, false, false, false },
	};

	/** Direction Est */
	private final boolean[][] EAST = new boolean[][] {
			{ false, false, true, false },
			{ false, false, true, true },
			{ false, false, false, true },
			{ false, false, false, false },
	};

	/**
	 * Constructeur de PieceS
	 * 
	 * @param x Position x de la pièce dans la grille
	 * @param y Position y de la pièce dans la grille
	 */
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
		return NORTH;
	}

	@Override
	protected boolean[][] getWestShape() {
		return EAST;
	}

}
