package Model;

import java.awt.Color;

/**
 * Classe du modèle permettant de représenter une case de la grille de tétris
 */
public class Box {

    /** Position x de la grille */
    private int x;

    /** Position y de la grille */
    private int y;

    /** Couleur de la case */
    private Color color;

    /** Etat de la pièce (remplie ou vide) */
    private boolean isComplete;

    /**
     * Constructeur de la classe Box
     * 
     * @param x     position x de la case dans la grille
     * @param y     position y d ela case dans la grille
     * @param color couleur de la case
     */
    public Box(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /* Getteurs et setteurs */

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

}
