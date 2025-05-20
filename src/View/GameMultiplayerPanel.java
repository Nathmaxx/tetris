package View;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameMultiplayerPanel extends JPanel {

    public GameMultiplayerPanel(ActionListener actionListener) {

        JButton button1 = new JButton("JoueurServeur");
        JButton button2 = new JButton("Joueur");

        button1.setActionCommand("Server");
        button2.setActionCommand("Player");

        button1.addActionListener(actionListener);
        button2.addActionListener(actionListener);

        add(button1);
        add(button2);
    }
}
