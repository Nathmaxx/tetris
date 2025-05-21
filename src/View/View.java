package View;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import Controller.Controller;
import Controller.MusicPlayer;
import Model.Game;
import Model.Score;

public class View implements Observer {
    private JFrame frame;
    private MainMenuPanel mainMenuPanel;
    private GamePanel gamePanel;
    private NextPiecesPanel nextPiecesPanel;
    private GameOverPanel gameOverPanel;
    private PausePanel pausePanel;
    private Game model;
    private Controller controller;
    private MusicPlayer musicPlayer = new MusicPlayer();

    public View(Game model) {
        this.model = model;
        model.addObserver(this);
        musicPlayer.play("src/resources/Tetris.wav", true);

        frame = new JFrame("TETRIS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adjusted frame size for better layout
        frame.setSize(600, 700); // Increased width for better spacing
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        controller = new Controller(model, this);
        frame.addKeyListener(controller);

        mainMenuPanel = new MainMenuPanel(controller);
        model.setPause(true);
        frame.add(mainMenuPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void startGame() {
        frame.remove(mainMenuPanel);

        this.gamePanel = new GamePanel(model);
        nextPiecesPanel = new NextPiecesPanel(model, controller);

        // Adjust layout proportions
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(nextPiecesPanel, BorderLayout.EAST);
        nextPiecesPanel.setPreferredSize(new Dimension(300, 700)); // Reduced width for NextPiecesPanel

        frame.revalidate();
        frame.repaint();
        frame.requestFocusInWindow();
    }

    public void showMultiplayerMessage() {
        JOptionPane.showMessageDialog(frame, "Multiplayer mode is not implemented yet.", "Multiplayer",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Game) {
            Game game = (Game) o;
            if (gamePanel != null) {

                if (game.isGameOver()) {
                    showGameOverScreen();
                    return;
                }
                if (game.isRestarted()) {
                    restartGame();
                }
                if (game.isPaused()) {
                    showPauseScreen();
                    return;
                } else {
                    hidePauseScreen();
                }

                gamePanel.update(game);
                nextPiecesPanel.update(game);
            }
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

    public void showPauseScreen() {
        if (pausePanel == null) {
            pausePanel = new PausePanel(controller);
        }

        frame.getContentPane().remove(gamePanel);
        frame.getContentPane().add(pausePanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void hidePauseScreen() {
        if (pausePanel != null) {
            frame.getContentPane().remove(pausePanel);
            pausePanel = null; // Ensure the reference is cleared
        }

        if (gamePanel != null) {
            frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
            frame.requestFocusInWindow();
        }
    }

    public void showMainMenu() {
        if (gameOverPanel != null) {
            frame.getContentPane().remove(gameOverPanel);
            gameOverPanel = null;
        }
        if (gamePanel != null) {
            frame.getContentPane().remove(gamePanel);
            gamePanel = null;
        }
        if (nextPiecesPanel != null) {
            frame.getContentPane().remove(nextPiecesPanel);
            nextPiecesPanel = null;
        }
        if (pausePanel != null) {
            frame.getContentPane().remove(pausePanel);
            pausePanel = null;
        }
        model.setPause(true);
        mainMenuPanel = new MainMenuPanel(controller);
        frame.add(mainMenuPanel, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }

    public JFrame getFrame() {
        return this.frame;
    }
}
