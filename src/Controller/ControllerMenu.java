package Controller;

import View.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerMenu implements ActionListener {
    private View view;

    public ControllerMenu(View view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "StartGame":
                view.startGame();
                break;
            case "Multiplayer":
                view.showMultiplayerMessage();
                break;
            
        }
    }
}
