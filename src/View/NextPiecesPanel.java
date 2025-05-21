package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionListener;
import Controller.Controller;

import Model.Game;
import Model.Score;

public class NextPiecesPanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel bestScoreLabel;
    private JLabel opponentScoreLabel;
    private Color backgroundColor;
    private JButton pauseButton;

    public NextPiecesPanel(Game model, Controller controller) {
        this.backgroundColor = model.getGrid().getBackgroundColor();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(backgroundColor.brighter());
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Score label
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(scoreLabel);

        // Best score label
        bestScoreLabel = new JLabel("Best Score: " + Score.getBestScore());
        bestScoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        bestScoreLabel.setForeground(Color.YELLOW);
        bestScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(10));
        add(bestScoreLabel);

        // Opponent score label
        opponentScoreLabel = new JLabel("Adversaire: 0");
        opponentScoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        opponentScoreLabel.setForeground(Color.ORANGE);
        opponentScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(10));
        add(opponentScoreLabel);

        // Pause button
        pauseButton = new JButton("Pause");
        pauseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pauseButton.addActionListener(controller);
        pauseButton.setBackground(backgroundColor);
        pauseButton.setForeground(Color.WHITE);
        pauseButton.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // Exemple : bordure rouge
        pauseButton.setFont(new Font("Arial", Font.BOLD, 15)); // Police plus grande
        pauseButton.setPreferredSize(new Dimension(140, 40)); // Taille plus grande
        pauseButton.setMaximumSize(new Dimension(80, 40)); // Taille max pour éviter l'étirement

        pauseButton.setBorder(BorderFactory.createLineBorder(backgroundColor, 0));

        add(Box.createVerticalStrut(10));
        add(pauseButton);
        add(Box.createVerticalStrut(30));

        // Add 3 next piece grids
        for (int k = 0; k < 3; k++) {
            JPanel gridPanel = new JPanel(new GridLayout(4, 4, 1, 1));
            gridPanel.setBackground(backgroundColor.darker());
            gridPanel.setBorder(BorderFactory.createLineBorder(backgroundColor.darker(), 4));
            gridPanel.setMaximumSize(new Dimension(80, 80));
            gridPanel.setPreferredSize(new Dimension(80, 80));
            for (int i = 0; i < 16; i++) {
                JPanel cell = new JPanel();
                cell.setBackground(backgroundColor);
                gridPanel.add(cell);
            }
            add(gridPanel);
            add(Box.createVerticalStrut(20));
        }
        int fontSize = 12;
        // Add controls section using JLabels
        JLabel controlsTitle = new JLabel("Controls:");
        controlsTitle.setFont(new Font("Arial", Font.BOLD, fontSize + 2));
        controlsTitle.setForeground(Color.WHITE);
        controlsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(controlsTitle);

        JLabel moveLeftLabel = new JLabel("← : Move Left");
        moveLeftLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));
        moveLeftLabel.setForeground(Color.WHITE);
        moveLeftLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(moveLeftLabel);

        JLabel moveRightLabel = new JLabel("→ : Move Right");
        moveRightLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));
        moveRightLabel.setForeground(Color.WHITE);
        moveRightLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(moveRightLabel);

        JLabel moveDownLabel = new JLabel("↓ : Move Down");
        moveDownLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));
        moveDownLabel.setForeground(Color.WHITE);
        moveDownLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(moveDownLabel);

        JLabel rotateLabel = new JLabel("↑ : Rotate");
        rotateLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));
        rotateLabel.setForeground(Color.WHITE);
        rotateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(rotateLabel);

        JLabel dropPieceLabel = new JLabel("SPACE : Drop Piece");
        dropPieceLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));
        dropPieceLabel.setForeground(Color.WHITE);
        dropPieceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(dropPieceLabel);

        JLabel pauseResumeLabel = new JLabel("ESCAPE : Pause/Resume");
        pauseResumeLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));
        pauseResumeLabel.setForeground(Color.WHITE);
        pauseResumeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(pauseResumeLabel);
    }

    public void update(Game game) {
        // Update score
        scoreLabel.setText("Score: " + game.getScore());

        // Update best score
        bestScoreLabel.setText("Best Score: " + Score.getBestScore());

        // Update opponent score
        opponentScoreLabel.setText("Adversaire: " + game.getOpponentScore());

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
