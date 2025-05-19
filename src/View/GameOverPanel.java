package View;

import java.awt.*;
import javax.swing.*;
import Controller.Controller;

public class GameOverPanel extends JPanel {
    private Controller controller;

    public GameOverPanel(Controller controller, int score) {
        this.controller = controller;
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        JPanel innerPanel = new JPanel();
        innerPanel.setBackground(Color.BLACK);
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        JLabel gameOverLabel = new JLabel("GAME OVER", JLabel.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scoreLabel = new JLabel("Score : " + score, JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 40));
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setForeground(Color.GREEN);
        restartButton.setBackground(Color.BLACK);
        restartButton.setActionCommand("Restart");
        restartButton.addActionListener(controller);

        innerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        innerPanel.add(gameOverLabel);
        innerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        innerPanel.add(scoreLabel);
        innerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        innerPanel.add(restartButton);

        add(innerPanel);
    }
}
