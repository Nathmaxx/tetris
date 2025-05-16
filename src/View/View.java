package View;

import java.awt.*;
import Controller.Controller;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Model.Game;
import Model.pieces.PieceL;

public class View implements Observer {
    private JPanel gamePanel;
    private JFrame frame;

    public View(Game model) {

        model.addObserver(this); 
         frame = new JFrame("TETRIS");
        //  frame.setUndecorated(true); // Make the frame undecorated

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height - 100;
        int frameWidth = screenHeight / 2;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, screenHeight);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        Controller controller = new Controller(model);
        frame.addKeyListener(controller);

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(model.getRows(), model.getCols()));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int i = 0; i < model.getRows(); i++) { //on commence à 2 pour permettre a la piece de decendre
            for (int j = 0; j < model.getCols(); j++) {
                JPanel boxPanel = new JPanel();
                boxPanel.setBorder(border);
                boxPanel.setBackground(model.getGrid().getBox(i, j).getColor());
                gamePanel.add(boxPanel);
            }
        }

        gamePanel.setSize(new Dimension(800, 600));
        gamePanel.setBackground(Color.LIGHT_GRAY);
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.setVisible(true);     // Ensure this is called after the above
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Game) {

            
            Game game = (Game) o;
            if (game.isGameOver()) {
                System.out.println("Game Over");

                frame.getContentPane().removeAll(); 

                JPanel gameOverPanel = new JPanel();
                gameOverPanel.setBackground(Color.BLACK);
                gameOverPanel.setLayout(new GridBagLayout()); // Use GridBagLayout to center components

                JPanel innerPanel = new JPanel();
                innerPanel.setBackground(Color.BLACK);
                innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

                JLabel gameOverLabel = new JLabel("GAME OVER", JLabel.CENTER);
                gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50));
                gameOverLabel.setForeground(Color.RED);
                gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel scoreLabel = new JLabel("Score: ", JLabel.CENTER);
                scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
                scoreLabel.setForeground(Color.WHITE);
                scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JButton restartButton = new JButton("Restart");
                restartButton.setFont(new Font("Arial", Font.BOLD, 40));
                restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                restartButton.setForeground(Color.GREEN);
                restartButton.setBackground(Color.BLACK);

                innerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing
                innerPanel.add(gameOverLabel);
                innerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
                innerPanel.add(scoreLabel);
                innerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
                innerPanel.add(restartButton);

                gameOverPanel.add(innerPanel); 

                frame.getContentPane().add(gameOverPanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();

                return;
            }
            for (int i = 0; i < game.getRows(); i++) {
                for (int j = 0; j < game.getCols(); j++) {
                    Color color = game.getGrid().getBox(i, j).getColor();
                    gamePanel.getComponent(i * game.getCols() + j).setBackground(color);
                }
            }
        }
    }
}
