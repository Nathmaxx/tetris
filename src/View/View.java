package View;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Model.Game;
import Model.PieceL;

public class View {
    private JPanel gamePanel;

    public View(Game modele) {
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
}
