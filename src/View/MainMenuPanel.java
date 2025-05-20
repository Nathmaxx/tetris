package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private JButton startButton;
    private JButton multiplayerButton;

    public MainMenuPanel(ActionListener actionListener) {
        setLayout(new GridLayout(2, 1, 10, 10));

        startButton = new JButton("Commencer");
        multiplayerButton = new JButton("Multiplayer");

        startButton.setActionCommand("StartGame");
        multiplayerButton.setActionCommand("Multiplayer");

        startButton.addActionListener(actionListener);
        multiplayerButton.addActionListener(actionListener);

        add(startButton);
        add(multiplayerButton);
    }
}
