package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.*;

import Controller.Controller;
import Model.Game;

public class PausePanel extends JPanel {
    private Controller controller;

    public PausePanel(Controller controller, Game model) {
        this.controller = controller;
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        JPanel innerPanel = new JPanel();
        innerPanel.setBackground(Color.BLACK);
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        JLabel pauseLabel = new JLabel("PAUSE", JLabel.CENTER);
        pauseLabel.setFont(new Font("Arial", Font.BOLD, 45));
        pauseLabel.setForeground(Color.RED);
        pauseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        if (!model.getOpponentMessage().equals("L'adversaire a gagné") && !model.getOpponentMessage().equals("L'adversaire a perdu") && !model.getOpponentMessage().equals("Vous avez gagné") ) {
            System.out.println("Opponent message: " + model.getOpponentMessage());
            JButton resumeButton = new JButton("Resume");
            resumeButton.setFont(new Font("Arial", Font.BOLD, 25));
            resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            resumeButton.setForeground(Color.GREEN);
            resumeButton.setBackground(Color.BLACK);
            resumeButton.setActionCommand("Resume");
            resumeButton.addActionListener(controller);
            resumeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            innerPanel.add(resumeButton);

        }


        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.BOLD, 25));
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setActionCommand("QuitToMenu"); // Use "Menu" action to navigate to the main menu
        quitButton.setForeground(Color.RED);
        quitButton.setBackground(Color.BLACK);
        quitButton.addActionListener(controller);
        quitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
  

        innerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        innerPanel.add(pauseLabel);
        innerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        innerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        innerPanel.add(quitButton);

        add(innerPanel);
    }
}
