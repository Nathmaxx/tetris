package Model;

import java.awt.Color;

public class Grid {
    private Box[][] boxes;
    private int rows;
    private int cols;
    private Piece currentPiece;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        boxes = new Box[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boxes[i][j] = new Box(i, j, Color.WHITE);
                boxes[i][j].setIsComplete(false);
            }
        }
    }

    public void moveCurrentPieceDown() {
        currentPiece.setY(currentPiece.getY() + 1);
    }

    public void moveCurrentPieceLeft() {
        currentPiece.setX(currentPiece.getX() - 1);

    }

    public void moveCurrentPieceRight() {
        currentPiece.setX(currentPiece.getX() + 1);
    }

    public void updateGrid() {
        boolean[][] shape = currentPiece.getShape();
        Color pieceColor = currentPiece.getColor();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (boxes[i][j].getColor().equals(pieceColor) && !boxes[i][j].getIsComplete()) {
                    boxes[i][j].setColor(Color.WHITE);
                }
            } 
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j]) {
                    int newY = currentPiece.getY() + i;
                    int newX = currentPiece.getX() + j;
                    if (newY >= 0 && newY < rows && newX >= 0 && newX < cols) {
                        boxes[newY][newX].setColor(pieceColor);
                    }
                }
            }
        }
    }

    public void printGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!boxes[i][j].getIsComplete()) {
                    System.out.print(" ");
                } else {
                    System.out.print("1 ");
                }
            }
            System.out.println();
        }
    }

    public boolean checkMoveDown() {
        Integer[] maxIndices = currentPiece.maxDownIndex();
        for (int i = 0; i < maxIndices.length; i++) {
            if (maxIndices[i] != null) {
                if (currentPiece.getY() + maxIndices[i] + 1 >= rows) {
                    return false;
                }
                if (boxes[currentPiece.getY() + maxIndices[i] + 1][currentPiece.getX() + i].getIsComplete()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkMoveLeft() {
        Integer[] maxIndices = currentPiece.leftIndex();
        for (int i = 0; i < maxIndices.length; i++) {
            if (maxIndices[i] != null) {
                if (currentPiece.getX() + maxIndices[i] == 0) {
                    return false;
                }
                if (boxes[currentPiece.getY() + i][currentPiece.getX() + maxIndices[i] - 1].getIsComplete()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkMoveRight() {
        Integer[] maxIndices = currentPiece.rightIndex();
        for (int i = 0; i < maxIndices.length; i++) {
            if (maxIndices[i] != null) {
                if (currentPiece.getX() + maxIndices[i] == cols - 1) {
                    return false;
                }
                if (boxes[currentPiece.getY() + i][currentPiece.getX() + maxIndices[i] + 1].getIsComplete()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void placeCurrentPiece() {
        boolean[][] shape = currentPiece.getShape();
        int pieceRow = currentPiece.getY();
        int pieceCol = currentPiece.getX();
    
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {
                    boxes[pieceRow + i][pieceCol + j].setIsComplete(true);
                    boxes[pieceRow + i][pieceCol + j].setColor(currentPiece.getColor());
                }
            }
        }
    
        generateNewPiece();
    }
    
    private void generateNewPiece() {
        currentPiece = creatPiece();
    
        if (!canPlacePiece(currentPiece)) {
            System.out.println("Game Over!");
        }
    }
    
    boolean canPlacePiece(Piece piece) {
        boolean[][] shape = piece.getShape();
    
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {
                    if (!checkMoveDown() || !checkMoveLeft() || !checkMoveRight()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Piece creatPiece() {
        Piece piece = new PieceL(2,0);
        return piece;
    }




    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Box getBox(int row, int col) {
        return boxes[row][col];
    }

    public void setCurrentPiece(Piece piece) {
        this.currentPiece = piece;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    
}
