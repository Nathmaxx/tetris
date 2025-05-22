package Model.pieces;

/** Piece T du jeu tetris */
public class PieceT extends Piece {

	/** Direction Nord */
	private final boolean[][] NORTH = new boolean[][] {
			{ false, false, true, false },
			{ false, true, true, true },
			{ false, false, false, false },
			{ false, false, false, false },
	};

	/** Direction Ouest */
	private final boolean[][] WEST = new boolean[][] {
			{ false, false, true, false },
			{ false, true, true, false },
			{ false, false, true, false },
			{ false, false, false, false },
	};

	/** Direction Sud */
	private final boolean[][] SOUTH = new boolean[][] {
			{ false, false, false, false },
			{ false, true, true, true },
			{ false, false, true, false },
			{ false, false, false, false },
	};

	/** Direction Est */
	private final boolean[][] EAST = new boolean[][] {
			{ false, false, false, false },
			{ false, false, true, false },
			{ false, false, true, true },
			{ false, false, true, false },

	};

	/**
	 * Constructeur de PieceT
	 * 
	 * @param x Position x de la pièce dans la grille
	 * @param y Position y de la pièce dans la grille
	 */
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
