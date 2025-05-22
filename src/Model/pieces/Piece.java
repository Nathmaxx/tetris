package Model.pieces;

import Model.Direction;
import java.awt.Color;

/** Classe abstraite parente des pièces du jeu tetris */
public abstract class Piece {

    /** Matrice 4*4 de booléens permettant de déterminer la forme de la pièce */
    protected boolean[][] actualShape;

    /** La direction actuelle de la pièce (Nord, Sur, Est, Ouest) */
    protected Direction actualDirection;

    /**
     * Getteur pour la forme de la pièce en direction nord
     * 
     * @return matrice de booléen 4*4
     */
    protected abstract boolean[][] getNorthShape();

    /**
     * Getteur pour la forme de la pièce en direction est
     * 
     * @return matrice de booléen 4*4
     */
    protected abstract boolean[][] getEastShape();

    /**
     * Getteur pour la forme de la pièce en direction sud
     * 
     * @return matrice de booléen 4*4
     */
    protected abstract boolean[][] getSouthShape();

    /**
     * Getteur pour la forme de la pièce en direction ouest
     * 
     * @return matrice de booléen 4*4
     */
    protected abstract boolean[][] getWestShape();

    /** Position x du coin supérieur gauche de la matrice dans la grille */
    protected int x;

    /** Position y du coin supérieur gauche de la matrice dans la grille */
    protected int y;

    /** La couleur de la pièce */
    protected Color color;

    /**
     * Constructeur de Piece
     * 
     * @param x position x de la pièce dans la grille
     * @param y position y de la pièce dans la grille
     */
    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
        this.actualShape = getNorthShape();
        this.actualDirection = Direction.NORTH;
    }

    /**
     * Getteur pour la matrice donnant l'état actuel de la pièce
     * 
     * @return matrice de booléen 4*4
     */
    public boolean[][] getShape() {
        return this.actualShape;
    }

    /**
     * Retourne 4 indexs qui sont les positions de la pièce les plus basses de
     * chaque colonne
     * 
     * @return Tabelau d'Integer de taille 4
     */
    public Integer[] maxDownIndex() {
        Integer[] maxIndices = new Integer[4];

        for (int col = 0; col < 4; col++) {
            for (int row = 3; row >= 0; row--) {
                if (actualShape[row][col]) {
                    maxIndices[col] = row;
                    break;
                }
            }
        }
        return maxIndices;
    }

    /**
     * Retourne 4 indexs qui sont les positions de la pièce les plus à gauche de
     * chaque ligne
     * 
     * @return Tabelau d'Integer de taille 4
     */
    public Integer[] leftIndex() {
        Integer[] maxIndices = new Integer[4];

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (actualShape[row][col]) {
                    maxIndices[row] = col;
                    break;
                }
            }
        }
        return maxIndices;
    }

    /**
     * Retourne 4 indexs qui sont les positions de la pièce les plus à droite de
     * chaque ligne
     * 
     * @return Tabelau d'Integer de taille 4
     */
    public Integer[] rightIndex() {
        Integer[] maxIndices = new Integer[4];

        for (int row = 0; row < 4; row++) {
            for (int col = 3; col >= 0; col--) {
                if (actualShape[row][col]) {
                    maxIndices[row] = col;
                    break;
                }
            }
        }
        return maxIndices;
    }

    /**
     * Donne le prochain tabelau de booléens en prenant une direction
     * 
     * @param direction la direction de départ
     * @return matrice 4*4 de la pièce dans la prochaine direction
     */
    public boolean[][] nextDirectionShape(Direction direction) {
        boolean[][] returnValue = null;
        switch (direction) {
            case NORTH:
                returnValue = getEastShape();
                break;
            case EAST:
                returnValue = getSouthShape();
                break;
            case SOUTH:
                returnValue = getWestShape();
                break;
            case WEST:
                returnValue = getNorthShape();
                break;
            default:
                returnValue = getNorthShape();
                break;
        }
        return returnValue;
    }

    /**
     * Donne la prochaine direction à partir de la direction actuelle
     * 
     * @return Direction
     */
    public Direction nextDirection() {
        Direction nextDir;
        switch (actualDirection) {
            case NORTH:
                nextDir = Direction.EAST;
                break;
            case EAST:
                nextDir = Direction.SOUTH;
                break;
            case SOUTH:
                nextDir = Direction.WEST;
                break;
            case WEST:
                nextDir = Direction.NORTH;
                break;
            default:
                nextDir = Direction.NORTH;
        }
        return nextDir;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Color getColor() {
        return this.color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getActualDirection() {
        return this.actualDirection;
    }

    /**
     * Donne la matrice pour la pièce en fonction de la direction souhaitée
     * 
     * @param direction la Direction initiale
     * @return la matrice 4*4 de la pièce dans la direction
     */
    private boolean[][] getShapeForDirection(Direction direction) {
        switch (direction) {
            case NORTH:
                return getNorthShape();
            case EAST:
                return getEastShape();
            case SOUTH:
                return getSouthShape();
            case WEST:
                return getWestShape();
            default:
                return getNorthShape();
        }
    }

    public void setNextDirection(Direction direction) {
        this.actualShape = getShapeForDirection(direction);
        this.actualDirection = direction;
    }

}
