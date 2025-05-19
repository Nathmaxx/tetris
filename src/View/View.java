package View;

import java.awt.*;
import Controller.Controller;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.Border;

import Model.Game;
import Model.pieces.PieceL;

public class View implements Observer {
    private JPanel gamePanel;
    private JPanel nextPiecesPanel;
    private JFrame frame;
    private Controller controller;
    private JPanel gameOverPanel;
    private Game model;
    private Color backgroundColor ;

    public View(Game model) {
        this.model = model;
        model.addObserver(this); 
        frame = new JFrame("TETRIS");
        backgroundColor = model.getGrid().getBackgroundColor(); // Couleur de fond
        // Récupère les dimensions de l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = 1500; 
        int frameWidth = 1100; 

        // Configure la fenêtre principale
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, screenHeight); // Taille de la fenêtre
        frame.setLocationRelativeTo(null); // Centre la fenêtre
        frame.setLayout(new BorderLayout());
        // frame.setResizable(false);

        controller = new Controller(model);
        frame.addKeyListener(controller);



        // Initialisation du panneau de jeu
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(model.getRows(), model.getCols()));
        gamePanel.setPreferredSize(new Dimension(750, 1500));
        gamePanel.setBackground(model.getGrid().getBackgroundColor().brighter());
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Border border = BorderFactory.createLineBorder(backgroundColor.darker(), 1);

        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                JPanel boxPanel = new JPanel();
                Color colorCase = model.getGrid().getBox(i, j).getColor();
                boxPanel.setBackground(colorCase);
                boxPanel.setPreferredSize(new Dimension(50, 50)); // Taille des cellules
                boxPanel.setBorder(border);
                gamePanel.add(boxPanel);
            }
        }

        frame.add(gamePanel, BorderLayout.CENTER);

        // Initialisation du panneau des prochaines pièces
        nextPiecesPanel = new JPanel();
        nextPiecesPanel.setLayout(new GridLayout(3, 1, 20, 20)); // 3 grilles avec espacement
        nextPiecesPanel.setBorder(BorderFactory.createEmptyBorder(250, 40, 250, 40)); // Padding autour du panneau
        nextPiecesPanel.setBackground(backgroundColor.brighter());
        nextPiecesPanel.setPreferredSize(new Dimension(350, 1500)); 
        
        for (int k = 0; k < 3; k++) {
            JPanel gridPanel = new JPanel();
            gridPanel.setLayout(new GridLayout(4, 4, 5, 5)); // Grille 4x4 avec espacement
            gridPanel.setBackground(backgroundColor.darker());
            gridPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Bordure blanche

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    JPanel cell = new JPanel();
                    cell.setBackground(Color.BLACK); // Couleur par défaut
                    gridPanel.add(cell);
                }
            }

            nextPiecesPanel.add(gridPanel);
        }

        frame.add(nextPiecesPanel, BorderLayout.EAST);
        frame.setVisible(true); // Affiche la fenêtre
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


            updateGamePanel(game);
            updateNextPiecesPanel(game);
        }
    }

    private void updateGamePanel(Game game) {
        gamePanel.setVisible(true);

        for (int i = 0; i < game.getRows(); i++) {
            for (int j = 0; j < game.getCols(); j++) {
                Color color = game.getGrid().getBox(i, j).getColor();
                gamePanel.getComponent(i * game.getCols() + j).setBackground(color);
            }
        }

        frame.revalidate();
        frame.repaint();
        frame.requestFocusInWindow(); 
    }

    private void updateNextPiecesPanel(Game game) {
        Component[] components = nextPiecesPanel.getComponents();

        for (int k = 0; k < components.length; k++) {
            if (components[k] instanceof JPanel) {
                JPanel gridPanel = (JPanel) components[k];
                boolean[][] shape = game.getGrid().getNextPiece(k).getShape(); // Supposé retourner la k-ième prochaine pièce
                Color pieceColor = game.getGrid().getNextPiece(k).getColor();

                // Met à jour les cellules de la grille 4x4
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        int index = i * 4 + j;
                        JPanel cell = (JPanel) gridPanel.getComponent(index);

                        if (i < shape.length && j < shape[i].length && shape[i][j]) {
                            cell.setBackground(pieceColor); // Définit la couleur de la pièce
                        } else {
                            cell.setBackground(backgroundColor); // Réinitialise l'arrière-plan
                        }
                    }
                }
            }
        }
    }

    private void showGameOverScreen() {
        if (gameOverPanel == null) {
            gameOverPanel = new JPanel();
            gameOverPanel.setBackground(Color.BLACK);
            gameOverPanel.setLayout(new GridBagLayout());

            JPanel innerPanel = new JPanel();
            innerPanel.setBackground(Color.BLACK);
            innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

            JLabel gameOverLabel = new JLabel("GAME OVER", JLabel.CENTER);
            gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50));
            gameOverLabel.setForeground(Color.RED);
            gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel scoreLabel = new JLabel("Score: " , JLabel.CENTER);
            scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton restartButton = new JButton("Restart");
            restartButton.setFont(new Font("Arial", Font.BOLD, 40));
            restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            restartButton.setForeground(Color.GREEN);
            restartButton.setBackground(Color.BLACK);
            restartButton.setActionCommand("Restart");
            restartButton.addActionListener(controller);

            innerPanel.add(Box.createRigidArea(new Dimension(0, 20))); 
            innerPanel.add(gameOverLabel);
            innerPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
            innerPanel.add(scoreLabel);
            innerPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
            innerPanel.add(restartButton);

            gameOverPanel.add(innerPanel);
        }

        frame.getContentPane().remove(gamePanel); // Hide the game panel
        frame.getContentPane().add(gameOverPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public void restartGame() {

        // Supprime le panneau "Game Over" s'il existe
        if (gameOverPanel != null) {
            frame.getContentPane().remove(gameOverPanel);
            gameOverPanel = null; 
        }

        // Réaffiche le panneau de jeu
        gamePanel.setVisible(true);
        frame.getContentPane().add(gamePanel, BorderLayout.CENTER);

        // Met à jour la fenêtre
        frame.revalidate();
        frame.repaint();
        frame.requestFocusInWindow();
    }
}