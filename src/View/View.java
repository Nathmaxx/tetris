package View;

import java.awt.*;
import Controller.Controller;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Model.Game;
import Model.PieceL;

public class View implements Observer{
    private JPanel gamePanel;
    


    public View(Game model) {

        model.addObserver(this); // S'abonne aux mises à jour du modèle
        JFrame frame = new JFrame("TETRIS");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height- 100; 
        int frameWidth = screenHeight/2;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, screenHeight);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        Controller controller = new Controller(model);
        frame.addKeyListener(controller);

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(model.getRows(), model.getCols()));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int i = 0; i < model.getRows(); i++) {
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
        frame.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Game) {
            Game game = (Game) o;
            for (int i = 0; i < game.getRows(); i++) {
                for (int j = 0; j < game.getCols(); j++) {
                    Color color = game.getGrid().getBox(i, j).getColor();
                    gamePanel.getComponent(i * game.getCols() + j).setBackground(color);
                }
            }
        }
    }
}
