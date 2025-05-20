package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.*;

import Controller.Controller;

public class PausePanel extends JPanel {

    public PausePanel(Controller controller) {
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        JPanel innerPanel = new JPanel();
        innerPanel.setBackground(Color.BLACK);
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        JLabel pauseLabel = new JLabel("PAUSE", JLabel.CENTER);
        pauseLabel.setFont(new Font("Arial", Font.BOLD, 50));
        pauseLabel.setForeground(Color.RED);
        pauseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton resumeButton = new JButton("Resume");
        resumeButton.setFont(new Font("Arial", Font.BOLD, 40));
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumeButton.setForeground(Color.GREEN);
        resumeButton.setBackground(Color.BLACK);
        resumeButton.setActionCommand("Resume");
        resumeButton.addActionListener(controller);

        innerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        innerPanel.add(pauseLabel);
        innerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        innerPanel.add(resumeButton);

        add(innerPanel);
    }
}
