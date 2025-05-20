package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.NetworkGame;
import View.View;

public class NetworkController implements ActionListener {

    private NetworkGame networkGame;
    private View view;

    public NetworkController(NetworkGame model, View view) {
        this.networkGame = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Server":
                break;
            case "Player":
                break;
        }
    }
}
