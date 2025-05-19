package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionListener;
import Controller.Controller;

import Model.Game;

public class NextPiecesPanel extends JPanel {
    private JLabel scoreLabel;
    private Color backgroundColor;
    private JButton pauseButton;

    public NextPiecesPanel(Game model, Controller controller) {
        this.backgroundColor = model.getGrid().getBackgroundColor();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(backgroundColor.brighter());
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Score label
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(scoreLabel);
        add(Box.createVerticalStrut(10));

        // Pause button
        pauseButton = new JButton("Pause");
        pauseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pauseButton.addActionListener(controller);
        pauseButton.setBackground(backgroundColor);
        pauseButton.setForeground(Color.WHITE);
        pauseButton.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // Exemple : bordure rouge
        pauseButton.setFont(new Font("Arial", Font.BOLD, 15)); // Police plus grande
        pauseButton.setPreferredSize(new Dimension(140, 40)); // Taille plus grande
        pauseButton.setMaximumSize(new Dimension(80, 40));   // Taille max pour éviter l'étirement
        
        pauseButton.setBorder(BorderFactory.createLineBorder(backgroundColor, 0));

        add(pauseButton);
        add(Box.createVerticalStrut(30));

        // Add 3 next piece grids
        for (int k = 0; k < 3; k++) {
            JPanel gridPanel = new JPanel(new GridLayout(4, 4, 1, 1));
            gridPanel.setBackground(backgroundColor.darker());
            gridPanel.setBorder(BorderFactory.createLineBorder(backgroundColor.darker(), 4));
            gridPanel.setMaximumSize(new Dimension(120, 120));
            gridPanel.setPreferredSize(new Dimension(120, 120));
            for (int i = 0; i < 16; i++) {
                JPanel cell = new JPanel();
                cell.setBackground(backgroundColor);
                gridPanel.add(cell);
            }
            add(gridPanel);
            add(Box.createVerticalStrut(20));
        }
    }

    public void update(Game game) {
        // Update score
        scoreLabel.setText("Score: " + game.getScore());

        Component[] components = getComponents();
        int gridIndex = 0;
        for (int k = 0; k < components.length; k++) {
            if (components[k] instanceof JPanel) {
                JPanel gridPanel = (JPanel) components[k];
                boolean[][] shape = game.getGrid().getNextPiece(gridIndex).getShape();
                Color pieceColor = game.getGrid().getNextPiece(gridIndex).getColor();

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        int index = i * 4 + j;
                        JPanel cell = (JPanel) gridPanel.getComponent(index);

                        if (i < shape.length && j < shape[i].length && shape[i][j]) {
                            cell.setBackground(pieceColor);
                        } else {
                            cell.setBackground(backgroundColor);
                        }
                    }
                }
                gridIndex++;
            }
        }
    }
}
