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

    public View(Game model) {
        this.model = model;
        model.addObserver(this); 
         frame = new JFrame("TETRIS");
        //  frame.setUndecorated(true); // Make the frame undecorated

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = 1600;
        int frameWidth = 1200;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, screenHeight);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        controller = new Controller(model);
        frame.addKeyListener(controller);

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(model.getRows(), model.getCols()));

        Border border = BorderFactory.createLineBorder(model.getGrid().getBackgroundColor().darker() , 1);
        
        for (int i = 0; i < model.getRows(); i++) { //on commence à 2 pour permettre a la piece de decendre
            for (int j = 0; j < model.getCols(); j++) {
                JPanel boxPanel = new JPanel();
                Color colorCase = model.getGrid().getBox(i, j).getColor();
                boxPanel.setBackground(colorCase);
                gamePanel.add(boxPanel);
                boxPanel.setBorder(border);

               
            }
        }

        gamePanel.setPreferredSize(new Dimension(screenHeight, screenHeight/2));
        gamePanel.setBackground(model.getGrid().getBackgroundColor().brighter());
        gamePanel.setBorder(BorderFactory.createEmptyBorder(80, 10, 10, 10));
        frame.add(gamePanel, BorderLayout.CENTER);

        nextPiecesPanel = new JPanel();
        nextPiecesPanel.setLayout(new GridLayout(3, 1, 20, 20)); // 3 rows for 3 grids, with larger spacing
        nextPiecesPanel.setBorder(BorderFactory.createEmptyBorder(250, 50 ,250, 50)); // Add padding around the panel
        nextPiecesPanel.setBackground(model.getGrid().getBackgroundColor().brighter());
        nextPiecesPanel.setPreferredSize(new Dimension(400,screenHeight));

        for (int k = 0; k < 3; k++) {
            JPanel gridPanel = new JPanel();
            gridPanel.setLayout(new GridLayout(4, 4, 2, 2)); // 4x4 grid for each piece, with spacing between cells
            gridPanel.setBackground(model.getGrid().getBackgroundColor().darker());
            gridPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Thicker border for better visibility

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    JPanel cell = new JPanel();
                    gridPanel.add(cell);
                }
            }

            nextPiecesPanel.add(gridPanel);
        }

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
                            cell.setBackground(model.getGrid().getBackgroundColor()); // Réinitialise l'arrière-plan
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