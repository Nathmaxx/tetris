package Controller;

import java.awt.event.*;

import View.View;

import Model.Game;

/**
 * Contrôleur MVC gérant les entrées clavier et les actions déclenchées
 * par la vue ou le timer.
 */
public class Controller implements KeyListener, ActionListener {
    private Game model;
    private View view;

    /**
     * Initialise le contrôleur avec le modèle et la vue.
     * @param model le modèle du jeu
     * @param view la vue associée
     */
    public Controller(Game model, View view) {
        this.model = model;
        this.view = view;

    }

    /**
     * Traite les appuis de touches :
     * - Flèche gauche/droite pour déplacer la pièce
     * - Flèche bas pour la chute accélérée
     */
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
            model.placePieceAtBottom();
        }
    }

    /**
     * Réagit aux événements d’action 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "StartGame":
                view.startGame();
                model.startGame();
                break;
            case "Multiplayer":
                view.showMultiplayerPanel();
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
            case "Menu":
                view.showMainMenu();
                break;
            case "QuitToMenu":
                model.getGrid().restart();
                view.showMainMenu();
                break;
            case "createGame":
                view.startServerMultiplayerGame();
                break;
            case "joinGame":
                view.startMultiplayerGame();
                break;
        }
    }

    public void setModel(Game model) {
        this.model = model;
    }
}
