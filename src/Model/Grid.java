package Model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Model.pieces.Piece;
import Model.pieces.PieceI;
import Model.pieces.PieceJ;
import Model.pieces.PieceL;
import Model.pieces.PieceO;
import Model.pieces.PieceS;
import Model.pieces.PieceT;
import Model.pieces.PieceZ;

public class Grid {
    private Box[][] boxes;
    private int rows;
    private int cols;
    private Piece currentPiece;
    private Color backgroundColor = new Color(47, 0, 100);
    private Random random = new Random();
    private List<Integer> pieceBag = new ArrayList<>();
    private boolean isGameOver = false;
    private Piece nexPiece;
    private Piece nextPiece2;
    private Piece nextPiece3;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        boxes = new Box[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boxes[i][j] = new Box(i, j, backgroundColor);
                boxes[i][j].setIsComplete(false);
            }
        }

        this.random = new Random(System.currentTimeMillis());
    }

    private void fillBag() {
        pieceBag.clear();
        for (int i = 0; i < 7; i++) {
            pieceBag.add(i);
        }
        Collections.shuffle(pieceBag, random);
    }

    public Piece createPiece() {
        if (pieceBag.isEmpty()) {
            fillBag();
        }

        int pieceType = pieceBag.remove(0);

        switch (pieceType) {
            case 0:
                return new PieceI(2, 0);
            case 1:
                return new PieceJ(2, 0);
            case 2:
                return new PieceL(2, 0);
            case 3:
                return new PieceO(2, 0);
            case 4:
                return new PieceS(2, 0);
            case 5:
                return new PieceT(2, 0);
            case 6:
                return new PieceZ(2, 0);
            default:
                return new PieceI(2, 0); // Par défaut
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
                    boxes[i][j].setColor(backgroundColor);
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
                    System.out.print("X");
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
        currentPiece = nexPiece;
        nexPiece = nextPiece2;
        nextPiece2 = nextPiece3;
        nextPiece3 = createPiece();
        generateNewPiece();
    }

    private void generateNewPiece() {
        if (!canPlacePiece(currentPiece)) {

            System.out.println("Game Over!");
            setIsGameOver(true);
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

    public boolean canRotate(Piece piece) {
        boolean[][] newShape = piece.nextDirectionShape(piece.getActualDirection());
        int x = piece.getX();
        int y = piece.getY();

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (newShape[row][col]) {
                    int newX = x + col;
                    int newY = y + row;

                    if (newX < 0 || newX >= cols || newY < 0 || newY >= rows) {
                        return false;
                    }

                    if (boxes[newY][newX].getIsComplete()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void removeLine() {
        boolean isComplete;
        int numberCompleteRows = 0;
        for (int row = rows - 1; row >= 0; row--) {
            isComplete = true;

            for (int col = 0; col < cols; col++) {
                if (!boxes[row][col].getIsComplete()) {
                    isComplete = false;
                    break;
                }
            }

            if (isComplete) {
                numberCompleteRows += 1;
                for (int moveRow = row; moveRow > 0; moveRow--) {
                    for (int col = 0; col < cols; col++) {
                        boxes[moveRow][col].setColor(boxes[moveRow - 1][col].getColor());
                        boxes[moveRow][col].setIsComplete(boxes[moveRow - 1][col].getIsComplete());
                    }
                }

                for (int col = 0; col < cols; col++) {
                    boxes[0][col].setColor(backgroundColor);
                    boxes[0][col].setIsComplete(false);
                }

                row++;
            }
        }

        if (numberCompleteRows == 1) {
            Score.addPoints(100);
        } else if (numberCompleteRows == 2) {
            Score.addPoints(300);
        } else if (numberCompleteRows == 3) {
            Score.addPoints(500);
        } else {
            int points = (int) Math.floor(Math.pow(numberCompleteRows * 200, 1.10));
            Score.addPoints(points);
        }
    }

    public void rotatePiece() {
        this.currentPiece.setNextDirection(currentPiece.nextDirection());
    }

    public void restart() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boxes[i][j].setColor(backgroundColor);
                boxes[i][j].setIsComplete(false);
            }
        }
        setIsGameOver(false);
        generateNewPiece();
    }

    public void printPiece(Piece piece) {
        boolean[][] shape = piece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {
                    System.out.print("X ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public Color getBackgroundColor() {
        return backgroundColor;
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

    public void setIsGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    public Piece getNextPiece(int index) {
        switch (index) {
            case 0:
                return nexPiece;
            case 1:
                return nextPiece2;
            case 2:
                return nextPiece3;
            default:
                return null;
        }
    }

    public void setNextPiece(Piece piece) {
        this.nexPiece = piece;
    }

    public void setNextPiece2(Piece piece) {
        this.nextPiece2 = piece;
    }

    public void setNextPiece3(Piece piece) {
        this.nextPiece3 = piece;
    }

}
