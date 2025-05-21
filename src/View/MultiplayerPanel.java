package View;

import javax.swing.JButton;
import javax.swing.JPanel;

import Controller.Controller;

public class MultiplayerPanel extends JPanel {

    public MultiplayerPanel(Controller controller) {
        JButton button1 = new JButton("Créer une partie");
        JButton button2 = new JButton("Rejoindre une partie");

        button1.setActionCommand("createGame");
        button2.setActionCommand("joinGame");

        button1.addActionListener(controller);
        button2.addActionListener(controller);

        add(button1);
        add(button2);
    }
}
