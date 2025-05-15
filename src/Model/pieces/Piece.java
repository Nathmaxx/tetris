package Model.pieces;

import Model.Direction;
import java.awt.Color;

public abstract class Piece {

    protected boolean[][] actualShape;
    protected Direction actualDirection;

    protected abstract boolean[][] getNorthShape();

    protected abstract boolean[][] getEastShape();

    protected abstract boolean[][] getSouthShape();

    protected abstract boolean[][] getWestShape();

    protected int x;
    protected int y;
    protected Color color;

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
        this.actualShape = getNorthShape();
        this.actualDirection = Direction.NORTH;
    }

    public boolean[][] getShape() {
        return this.actualShape;
    }

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

    public void setNextDirection(Direction direction) {
        this.actualShape = nextDirectionShape(direction);
        this.actualDirection = direction;
    }

}
