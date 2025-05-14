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

    public void moveCurrentPieceDown() {
        currentPiece.setY(currentPiece.getY() + 1);
    }


    public void updateGrid() {
        boolean[][] shape = currentPiece.getShape();
        if(currentPiece.getY() >= 1){
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (shape[i][j]) {
                        boxes[currentPiece.getY() + (i-1) ][currentPiece.getX() + j].setIsComplete(false);
                        boxes[currentPiece.getY() + (i-1) ][currentPiece.getX() + j].setColor(Color.WHITE);
                    }
                }
            }
        } else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (shape[i][j]) {
                        boxes[currentPiece.getY() + i ][currentPiece.getX() + j].setIsComplete(false);
                        boxes[currentPiece.getY() + i ][currentPiece.getX() + j].setColor(Color.WHITE);
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j]) {
                    boxes[currentPiece.getY() + i][currentPiece.getX() + j].setIsComplete(true);
                    boxes[currentPiece.getY() + i][currentPiece.getX() + j].setColor(Color.RED);

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
}
