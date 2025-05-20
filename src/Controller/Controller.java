package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import View.View;

import Model.Game;

public class Controller implements KeyListener, ActionListener {
    private Game model;
    private View view;

    public Controller(Game model, View view) {
        this.model = model;
        this.view = view;

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                System.out.println("Left key pressed");
                if (model.getGrid().checkMoveLeft()) {
                    model.moveLeft();
                }
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("Right key pressed");
                if (model.getGrid().checkMoveRight()) {
                    model.moveRight();
                }
                break;
            case KeyEvent.VK_DOWN:
                model.run();
                break;
            
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("Up key pressed");
            if (model.getGrid().canRotate(model.getGrid().getCurrentPiece())) {
                model.rotate();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            if (model.isPaused()) {
                model.resume();
            } else {
                model.pause();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("Space key pressed");
            model.placePieceAtBottom();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    switch (command) {
        case "StartGame":
            view.startGame();
            model.restart();
            break;
        case "Multiplayer":
            view.showMultiplayerMessage();
            break;
        case "Restart":
            model.restart();
            break;
        case "Pause":
            model.pause();
            break;
        case "Resume":
            model.resume();
            break;
    }
}


    public void setModel(Game model) {
        this.model = model;
    }
}



