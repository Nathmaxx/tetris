package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import View.View;

import Model.Game;

public class Controller implements KeyListener, ActionListener {
    private Game model;

    public Controller(Game model, View view) {
        this.model = model;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (model.getGrid().checkMoveLeft()) {
                    model.moveLeft();
                }
                break;
            case KeyEvent.VK_RIGHT:
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
            if (model.getGrid().canRotate(model.getGrid().getCurrentPiece())) {
                model.rotate();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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

        if (command.equals("Restart")) {
            model.restart();

        }
        if (command.equals("Pause")) {
            model.pause();
        }
        if (command.equals("Resume")) {
            model.resume();
        }
    }

    public void setModel(Game model) {
        this.model = model;
    }
}
