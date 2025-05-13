package Model;
import java.awt.Color;

public class Box {
    private int x;
    private int y;
    private Color color;
    private boolean isEmpty;

    public Box(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

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
    public boolean isEmpty() {
        return isEmpty;
    }
    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
    
}
