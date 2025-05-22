package View;

import java.awt.*;
import javax.swing.*;
import Controller.Controller;

public class GameOverPanel extends JPanel {
    private Controller controller;
    private JButton menuButton;

    public GameOverPanel(Controller controller, int score, boolean isNetworkGame, boolean isHost) {
        this.controller = controller;
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        JPanel innerPanel = new JPanel();
        innerPanel.setBackground(Color.BLACK);
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        JLabel gameOverLabel = new JLabel("GAME OVER", JLabel.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 40));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scoreLabel = new JLabel("Score : " + score, JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (isNetworkGame) {
            JLabel networkMessage = new JLabel("La partie est terminée.", JLabel.CENTER);
            networkMessage.setFont(new Font("Arial", Font.BOLD, 20));
            networkMessage.setForeground(Color.ORANGE);
            networkMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
            innerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            innerPanel.add(networkMessage);

            if (isHost) {
                JButton restartButton = new JButton("Restart");
                restartButton.setFont(new Font("Arial", Font.BOLD, 30));
                restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                restartButton.setForeground(Color.GREEN);
                restartButton.setBackground(Color.BLACK);
                restartButton.setActionCommand("Restart");
                restartButton.addActionListener(controller);
                restartButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                innerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                innerPanel.add(restartButton);
            } else {
                JLabel waitMessage = new JLabel("En attente de l'hôte pour redémarrer...", JLabel.CENTER);
                waitMessage.setFont(new Font("Arial", Font.BOLD, 20));
                waitMessage.setForeground(Color.YELLOW);
                waitMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                innerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                innerPanel.add(waitMessage);
            }
        } else {
            JButton restartButton = new JButton("Restart");
            restartButton.setFont(new Font("Arial", Font.BOLD, 30));
            restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            restartButton.setForeground(Color.GREEN);
            restartButton.setBackground(Color.BLACK);
            restartButton.setActionCommand("Restart");
            restartButton.addActionListener(controller);
            restartButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            innerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            innerPanel.add(restartButton);
        }

        menuButton = new JButton("Menu");
        menuButton.setFont(new Font("Arial", Font.BOLD, 30));
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setForeground(Color.GREEN);
        menuButton.setBackground(Color.BLACK);
        menuButton.setActionCommand("Menu");
        menuButton.addActionListener(controller);
        menuButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        innerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        innerPanel.add(gameOverLabel);
        innerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        innerPanel.add(scoreLabel);
        innerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        innerPanel.add(menuButton);

        add(innerPanel);
    }
}
