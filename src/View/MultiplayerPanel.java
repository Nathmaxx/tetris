package View;


import java.awt.*;

import javax.swing.*;

import Controller.Controller;

public class MultiplayerPanel extends JPanel {
        private Color backgroundColor = new Color(47, 0, 100);


    public MultiplayerPanel(Controller controller) {
        setLayout(new BorderLayout());
        setBackground(backgroundColor.brighter());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));


        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1, 50, 50));
        buttonsPanel.setBackground(backgroundColor.brighter());
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));

        JButton button1 = new JButton("Créer une partie");
        JButton button2 = new JButton("Rejoindre une partie");

        button1.setActionCommand("createGame");
        button2.setActionCommand("joinGame");

        button1.addActionListener(controller);
        button2.addActionListener(controller);

        customizeButton(button1);
        customizeButton(button2);

        add(button1);
        add(button2);


        buttonsPanel.add(button1);
        buttonsPanel.add(button2);

        add(buttonsPanel, BorderLayout.CENTER);

    }

        private void customizeButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 30));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(backgroundColor.darker(), 5));
    }
}
