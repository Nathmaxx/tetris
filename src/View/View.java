package View;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Model.Game;
import Model.PieceL;

public class View implements Observer{
    private JPanel gamePanel;
    private Game modele;


    public View(Game modele) {
        this.modele = modele;
        modele.addObserver(this); // S'abonne aux mises à jour du modèle
        JFrame frame = new JFrame("TETRIS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 1400);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(modele.getRows(), modele.getCols()));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int i = 0; i < modele.getRows(); i++) {
            for (int j = 0; j < modele.getCols(); j++) {
                JPanel boxPanel = new JPanel();
                boxPanel.setBorder(border);
                boxPanel.setBackground(modele.getGrid().getBox(i, j).getColor());
                gamePanel.add(boxPanel);
            }
        }

        gamePanel.setSize(new Dimension(800, 600));
        gamePanel.setBackground(Color.LIGHT_GRAY);
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Game) {
            Game game = (Game) o;
            for (int i = 0; i < game.getRows(); i++) {
                for (int j = 0; j < game.getCols(); j++) {
                    Color color = game.getGrid().getBox(i, j).getColor();
                    gamePanel.getComponent(i * game.getCols() + j).setBackground(color);
                }
            }
        }
    }
}
