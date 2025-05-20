package View;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import Controller.Controller;
import Controller.ControllerMenu;
import Controller.MusicPlayer;
import Model.Game;
import Model.Grid;
import Model.Scheduler;

public class View implements Observer {
    private JFrame frame;
    private MainMenuPanel mainMenuPanel;
    private GamePanel gamePanel;
    private NextPiecesPanel nextPiecesPanel;
    private GameOverPanel gameOverPanel;
    private PausePanel pausePanel;
    private Game model;
    private Controller controller;
    private ControllerMenu controllerMenu;
    private MusicPlayer musicPlayer = new MusicPlayer();
    private Scheduler scheduler;

    public View() {
        musicPlayer.play("src/resources/Tetris.wav", true);

        frame = new JFrame("TETRIS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        controllerMenu = new ControllerMenu( this);

        mainMenuPanel = new MainMenuPanel(controllerMenu);
        
        frame.add(mainMenuPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void startGame() {
        stopScheduler(); // Ensure any existing scheduler is stopped
        System.out.println("Starting game...");

        frame.remove(mainMenuPanel);
        this.model = new Game(new Grid(20, 10));
        model.addObserver(this);

        scheduler = new Scheduler(model);
        scheduler.start();

        controller = new Controller(model,this);
        this.gamePanel = new GamePanel(model);
        nextPiecesPanel = new NextPiecesPanel(model, controller);
        frame.addKeyListener(controller);

        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(nextPiecesPanel, BorderLayout.EAST);

        frame.revalidate();
        frame.repaint();
        frame.requestFocusInWindow();
    }

    public void showMultiplayerMessage() {
        JOptionPane.showMessageDialog(frame, "Multiplayer mode is not implemented yet.", "Multiplayer", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Game) {
            Game game = (Game) o;

            if (gamePanel == null || nextPiecesPanel == null) {
                // Components are not initialized yet, skip the update
                return;
            }

            if (game.isGameOver()) {
                showGameOverScreen();
                return;
            }
          
            if (game.isPaused()) {
                showPauseScreen();
                return;
            } else {
                hidePauseScreen();
            }

            gamePanel.update(game);
            nextPiecesPanel.update(game); // Ensure the score and next pieces are updated
        }
    }

    private void showGameOverScreen() {
        stopScheduler(); 
        if (gameOverPanel == null) {
            gameOverPanel = new GameOverPanel(controller, model.getScore());
        }


        frame.getContentPane().remove(gamePanel);
        frame.getContentPane().add(gameOverPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
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
        stopScheduler(); // Stop the scheduler thread when returning to the menu

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

        if (model != null) {
            model.deleteObservers(); // Remove all observers from the model
            model = null; // Set the model to null
        }

        mainMenuPanel = new MainMenuPanel(controllerMenu);
        frame.add(mainMenuPanel, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }

    private void stopScheduler() {
        if (scheduler != null) {
            scheduler.interrupt(); // Interrupt the scheduler thread
            try {
                scheduler.join(); // Wait for the thread to terminate
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupt status
            }
            scheduler = null; // Clear the reference
        }
    }
}
