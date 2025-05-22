package View;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import Controller.Controller;
import Controller.MusicPlayer;
import Model.Game;
import Model.Network.NetworkManager;

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
    private NetworkManager nm;
    private boolean isNetworkGame;

    public View(Game model) {
        this.model = model;
        this.nm = new NetworkManager(model);
        model.addObserver(this);
        musicPlayer.play("src/resources/Tetris.wav", true);

        frame = new JFrame("TETRIS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adjusted frame size for better layout
        frame.setSize(600, 800); // Increased width for better spacing
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
        this.isNetworkGame = false;
        this.gamePanel = new GamePanel(model);
        nextPiecesPanel = new NextPiecesPanel(model, controller, isNetworkGame);

        // Adjust layout proportions
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(nextPiecesPanel, BorderLayout.EAST);
        nextPiecesPanel.setPreferredSize(new Dimension(300, 800)); // Reduced width for NextPiecesPanel

        frame.revalidate();
        frame.repaint();
        frame.requestFocusInWindow();
    }

    public void showMultiplayerPanel() {
        frame.getContentPane().removeAll();

        // S'assurer que le jeu est en pause avant d'afficher le panneau multijoueur
        model.setPause(true);

        MultiplayerPanel mp = new MultiplayerPanel(controller);
        frame.getContentPane().add(mp);

        frame.revalidate();
        frame.repaint();
        frame.requestFocusInWindow();
    }

    public void startServerMultiplayerGame() {
        frame.getContentPane().removeAll();
        this.isNetworkGame = true;

        // Toujours réinitialiser la grille pour être sûr que la partie précédente est
        // effacée
        model.restart();

        this.gamePanel = new GamePanel(model);
        nextPiecesPanel = new NextPiecesPanel(model, controller, isNetworkGame);

        frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
        frame.getContentPane().add(nextPiecesPanel, BorderLayout.EAST);

        frame.revalidate();
        frame.repaint();
        frame.requestFocusInWindow();

        boolean connected = nm.startServer();

        // Démarrer l'envoi automatique des scores ici, après la connexion
        if (connected) {
            nm.startSendingScores();
        }
    }

    public void startMultiplayerGame() {
        while (true) {
            // Demande à l'utilisateur de saisir une adresse IP
            String ipAddress = JOptionPane.showInputDialog(frame, "Entrez l'adresse IP du serveur :",
                    "Rejoindre une partie", JOptionPane.QUESTION_MESSAGE);

            // Si l'utilisateur annule ou ne saisit rien, on quitte la boucle
            if (ipAddress == null || ipAddress.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Connexion annulée.", "Annulation",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Tente de se connecter au serveur avec l'adresse IP fournie par l'utilisateur
            if (nm.connectToServer(ipAddress.trim())) {
                JOptionPane.showMessageDialog(frame, "Connexion réussie à " + ipAddress, "Succès",
                        JOptionPane.INFORMATION_MESSAGE);

                // Charge l'interface de jeu
                frame.getContentPane().removeAll();
                this.gamePanel = new GamePanel(model);
                nextPiecesPanel = new NextPiecesPanel(model, controller, true);

                frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
                frame.getContentPane().add(nextPiecesPanel, BorderLayout.EAST);

                frame.revalidate();
                frame.repaint();
                frame.requestFocusInWindow();

                nm.startSendingScores();

                return;
            } else {
                // Affiche une erreur si la connexion échoue
                JOptionPane.showMessageDialog(frame,
                        "Impossible de se connecter à " + ipAddress + ". Veuillez réessayer.", "Erreur de connexion",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
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
                if (gamePanel != null) {
                    gamePanel.update(game);
                }

                if (nextPiecesPanel != null) {
                    nextPiecesPanel.update(game);
                }
            }
        }
    }

    private void showGameOverScreen() {
        boolean isHost = nm.isServerMode(); // Vérifie si le joueur est l'hôte
        boolean isWinner = !model.isGameOver(); // Si le joueur actuel n'a pas perdu, il est le gagnant

        if (gameOverPanel == null) {
            gameOverPanel = new GameOverPanel(controller, model.getScore(), isNetworkGame, isHost, isWinner);
        }

        if (isNetworkGame && nm != null) {
            nm.sendEndGame(); // Notifie l'autre joueur que la partie est terminée
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

        if (gamePanel != null) {
            gamePanel.setVisible(true);
            frame.getContentPane().add(gamePanel, BorderLayout.CENTER);

            // Mettre à jour immédiatement après le redémarrage
            if (nextPiecesPanel != null) {
                nextPiecesPanel.update(model);
            }

            frame.revalidate();
            frame.repaint();
            frame.requestFocusInWindow();
        } else {
            System.out.println("ERREUR: gamePanel est null dans restartGame()");
        }
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
