package View;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import Controller.Controller;
import Model.Game;

public class View implements Observer {
    private JFrame frame;
    private GamePanel gamePanel;
    private NextPiecesPanel nextPiecesPanel;
    private GameOverPanel gameOverPanel;
    private Game model;
    private Controller controller;

    public View(Game model) {
        this.model = model;
        model.addObserver(this);

        frame = new JFrame("TETRIS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        controller = new Controller(model);
        frame.addKeyListener(controller);

        gamePanel = new GamePanel(model);
        nextPiecesPanel = new NextPiecesPanel(model);

        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(nextPiecesPanel, BorderLayout.EAST);

        frame.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Game) {
            Game game = (Game) o;
            if (game.isGameOver()) {
                showGameOverScreen();
                return;
            }
            if (game.isRestarted()) {
                restartGame();
            }

            gamePanel.update(game);
            nextPiecesPanel.update(game);
        }
    }

    private void showGameOverScreen() {
        if (gameOverPanel == null) {
            gameOverPanel = new GameOverPanel(controller, model.getScore());
        }

        frame.getContentPane().remove(gamePanel);
        frame.getContentPane().add(gameOverPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public void restartGame() {
        if (gameOverPanel != null) {
            frame.getContentPane().remove(gameOverPanel);
            gameOverPanel = null;
        }

        gamePanel.setVisible(true);
        frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        frame.requestFocusInWindow();
    }
}
