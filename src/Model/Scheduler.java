package Model;

/**
 * Classe Scheduler qui gère la cadence du jeu Tetris.
 * Cette classe étend Thread pour s'exécuter en parallèle du thread principal.
 */
public class Scheduler extends Thread {
    /**
     * L'objet Runnable à exécuter périodiquement, instance de Game
     */

    private Runnable r;

    /** Le niveau actuel du jeu, qui détermine la vitesse */
    private int level = 1;

    /**
     * Constructeur du Scheduler
     * 
     * @param r L'objet Runnable à exécuter périodiquement
     */
    public Scheduler(Runnable r) {
        this.r = r;

    }

    /** Temps de pause entre chaque exécution (en millisecondes) */
    private long pause = 1000;

    /**
     * Méthode principale du thread qui s'exécute en continu.
     * Vérifie si le jeu est en cours puis exécute une itération de la logique de
     * jeu
     * La durée de la pause diminue à mesure que le niveau augmente
     */
    public void run() {
        while (true) {
            levelupdate();
            System.out.println("Level: " + level);
            System.out.println("Scheduler is running");

            if (!((Game) r).isGameOver() && !((Game) r).isPaused()) {

                r.run();

            }
            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("GAME OVER : " + ((Game) r).isGameOver());
        }
    }

    /**
     * Met à jour le niveau actuel et ajuste la vitesse du jeu, cette méthode
     * vérifie si le niveau du jeu a changé et, si c'est le cas,
     * réduit le temps de pause pour accélérer le jeu.
     */
    public void levelupdate() {
        int newLevel = Score.getLevel();
        if (newLevel==1){
            level=1000;
        }
        if (newLevel != level) {
            level = newLevel;
            System.out.println("Level updated to: " + level);
            pause = pause - 100;
            if (pause < 100) {
                pause = 100;
            }
        }

    }

    /* Getteurs et setteurs */

    public void setPause(long pause) {
        this.pause = pause;
    }

    public long getPause() {
        return pause;
    }

}