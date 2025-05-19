package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import View.View;

import Model.Game;

public class Controller implements KeyListener, ActionListener {
    private Game model;

    public Controller(Game model) {
        this.model = model;

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

    }

    @Override
    public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Restart")) {
                System.out.println("Restart button clicked");
                model.restart();
                
            } 
        }


    public void setModel(Game model) {
        this.model = model;
    }
}



