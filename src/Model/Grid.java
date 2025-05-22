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

/** Classe Grille, contient la logique du jeu de tetris */
public class Grid {

    /** La liste 2D des cases de la grille */
    private Box[][] boxes;

    /** Nombre de lignes de la grille */
    private int rows;

    /** Nombre de colonnes de la grille */
    private int cols;

    /** Pièce actuelle descendant dans la grille */
    private Piece currentPiece;

    /** Couleur de fond de la grille */
    private Color backgroundColor = new Color(47, 0, 100);

    /** Valeur aléatoire */
    private Random random = new Random();

    /**
     * Liste d'integer contenant les numéros des pièces à déposer dans un ordre
     * aléatoire
     */
    private List<Integer> pieceBag = new ArrayList<>();

    /** Booléen déterinant si la partie est perdues */
    private boolean isGameOver = false;

    /** Prochaine pièce à apparaître dans la grille */
    private Piece nextPiece;

    /** Pièce à descendre dans la grille après nextPiece */
    private Piece nextPiece2;

    /** Pièce à descendre dans la grille après nextPiece2 */
    private Piece nextPiece3;

    /**
     * Constructeur de la grille
     * 
     * @param rows le nombre de lignes de la grille
     * @param cols le nombre de colonnes de la grille
     */
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

    /**
     * Permet de remplir la liste avec les numéros des pièces et mélanger la liste
     */
    private void fillBag() {
        pieceBag.clear();
        for (int i = 0; i < 7; i++) {
            pieceBag.add(i);
        }
        Collections.shuffle(pieceBag, random);
    }

    /**
     * Permet de créer une pièce à partir du prémier élément dans pieceBab
     * 
     * @return la pièce crée
     */
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

    /** Déplace la piece actuelle vers le bas */
    public void moveCurrentPieceDown() {
        Score.addPoints(1);
        currentPiece.setY(currentPiece.getY() + 1);
    }

    /** Déplace la piece actuelle vers la gauche */
    public void moveCurrentPieceLeft() {
        currentPiece.setX(currentPiece.getX() - 1);

    }

    /** Déplace la piece actuelle vers la droite */
    public void moveCurrentPieceRight() {
        currentPiece.setX(currentPiece.getX() + 1);
    }

    /**
     * Met à jour l'affichage de la grille de jeu en fonction de la pièce courante.
     * Efface et remet à jour avec la position de la nouvelle piece dans le jeu
     */
    public void updateGrid() {

        if (currentPiece == null) {
            return;
        }

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

    /** Affiche la grille dans la console */
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

    /**
     * Vérifie si une pièce peut être déplacée vers le bas
     * 
     * @return Booléen, vrai pour déplacement possible, faux sinon
     */
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

    /**
     * Vérifie si une pièce peut être déplacée vers la gauche
     * 
     * @return Booléen, vrai pour déplacement possible, faux sinon
     */
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

    /**
     * Vérifie si une pièce peut être déplacée vers la droite
     * 
     * @return Booléen, vrai pour déplacement possible, faux sinon
     */
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

    /**
     * Bloque la position de la pièce dans la grille une fois qu'elle ne peut plus
     * se déplacer
     */
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

        currentPiece = nextPiece;
        nextPiece = nextPiece2;
        nextPiece2 = nextPiece3;
        nextPiece3 = createPiece();

        if (!canPlacePiece(currentPiece)) {
            System.out.println("Game Over!");
            setIsGameOver(true);
        }
    }

    /**
     * Vérifie si une pièce peut se déplacer à droite, en bas ou à gauche
     * 
     * @param piece la pièce courante
     * @return booléen indiquant si la pièce peut se déplacer
     */
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

    /**
     * Détermine si une pièce peut effectuer une rotation
     * 
     * @param piece la pièce courante
     * @return booléen indiquant si la pièce peut rotationner
     */
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

    /**
     * Parcourt la grille et supprime les cases remplies, met également à jour en
     * fonction du nombre de lignes supprimées
     */
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

    /** Met à jour la direction de la pièce pour la rotation */
    public void rotatePiece() {
        this.currentPiece.setNextDirection(currentPiece.nextDirection());
    }

    /** Recommence avec une nouvelle grille */
    public void restart() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boxes[i][j].setColor(backgroundColor);
                boxes[i][j].setIsComplete(false);
            }
        }
        setIsGameOver(false);
    }

    /**
     * Affiche une pièce dans la console
     * 
     * @param piece la pièce à afficher
     */
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

    /** Affiche la projection pour le placement de la pièce dans la grille */
    public void showProjection() {
        boolean[][] shape = currentPiece.getShape();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (boxes[i][j].getColor().equals(currentPiece.getColor().darker().darker())
                        && !boxes[i][j].getIsComplete()) {
                    boxes[i][j].setColor(backgroundColor);
                }
            }
        }

        int projectionY = currentPiece.getY();
        while (canMoveProjectionDown(projectionY)) {
            projectionY++;
        }

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {
                    int newY = projectionY + i;
                    int newX = currentPiece.getX() + j;
                    if (newY >= 0 && newY < rows && newX >= 0 && newX < cols) {
                        boxes[newY][newX].setColor(currentPiece.getColor().darker().darker());
                    }
                }
            }
        }
    }

    /**
     * Vérifie si la projection d'une pièce peut descendre d'une case
     * supplémentaire.
     * Cette méthode est utilisée pour calculer l'emplacement final où la pièce se
     * posera
     * 
     * @param projectionY La coordonnée Y actuelle de la projection
     * @return true si la projection peut descendre d'une case supplémentaire,
     *         false si elle a atteint sa position finale (collision ou fond de
     *         grille)
     */
    private boolean canMoveProjectionDown(int projectionY) {
        boolean[][] shape = currentPiece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {
                    int newY = projectionY + i + 1;
                    int newX = currentPiece.getX() + j;
                    if (newY >= rows || (newY >= 0 && boxes[newY][newX].getIsComplete())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /* Getteurs et setteurs */

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
                return nextPiece;
            case 1:
                return nextPiece2;
            case 2:
                return nextPiece3;
            default:
                return null;
        }
    }

    public void setNextPiece(Piece piece) {
        this.nextPiece = piece;
    }

    public void setNextPiece2(Piece piece) {
        this.nextPiece2 = piece;
    }

    public void setNextPiece3(Piece piece) {
        this.nextPiece3 = piece;
    }
}
