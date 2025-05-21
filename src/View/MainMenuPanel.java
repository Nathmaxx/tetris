package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private JButton startButton;
    private JButton multiplayerButton;
    private Color backgroundColor = new Color(47, 0, 100);

    public MainMenuPanel(ActionListener actionListener) {
        setLayout(new BorderLayout());
        setBackground(backgroundColor.brighter());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Add Tetris logo (scaled down)
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("src/resources/Tetris_logo.png"); // Replace with the actual path to your image
        Image scaledImage = logoIcon.getImage().getScaledInstance(150,110, Image.SCALE_SMOOTH); // Adjust dimensions as needed
        logoLabel.setIcon(new ImageIcon(scaledImage));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(logoLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1, 50, 50));
        buttonsPanel.setBackground(backgroundColor.brighter());
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        startButton = new JButton("Commencer");
        multiplayerButton = new JButton("Multiplayer");

        startButton.setActionCommand("StartGame");
        multiplayerButton.setActionCommand("Multiplayer");

        startButton.addActionListener(actionListener);
        multiplayerButton.addActionListener(actionListener);

        customizeButton(startButton);
        customizeButton(multiplayerButton);

        buttonsPanel.add(startButton);
        buttonsPanel.add(multiplayerButton);

        add(buttonsPanel, BorderLayout.CENTER);
    }

    private void customizeButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 40));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(backgroundColor.darker(), 5));
    }
}
