package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import Model.Game;

public class GamePanel extends JPanel {
    private Color backgroundColor;

    public GamePanel(Game model) {
        this.backgroundColor = model.getGrid().getBackgroundColor();
        setLayout(new GridLayout(model.getRows(), model.getCols()));
        setBackground(backgroundColor.brighter());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Border border = BorderFactory.createLineBorder(backgroundColor.darker(), 1);

        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                JPanel boxPanel = new JPanel();
                Color colorCase = model.getGrid().getBox(i, j).getColor();
                boxPanel.setBackground(colorCase);
                boxPanel.setPreferredSize(new Dimension(50, 50));
                boxPanel.setBorder(border);
                add(boxPanel);
            }
        }
    }

    public void update(Game game) {
        for (int i = 0; i < game.getRows(); i++) {
            for (int j = 0; j < game.getCols(); j++) {
                Color color = game.getGrid().getBox(i, j).getColor();
                getComponent(i * game.getCols() + j).setBackground(color);
            }
        }
    }
}
