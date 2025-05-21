package Model;

public class Scheduler extends Thread{
    private Runnable r;
    private int level = 1;
    public  Scheduler(Runnable r) {
        this.r = r;

    }

    private long pause = 1000;
    public  void run(){
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

    public void levelupdate() {
        int newLevel = Score.getLevel();
        if (newLevel != level) {
            level = newLevel;
            System.out.println("Level updated to: " + level);
            pause = pause - 100;
            if (pause < 100) {
                pause = 100;
            }
        }

    }

    public void setPause(long pause) {
        this.pause = pause;
    }
    public long getPause() {
        return pause;
    }


}