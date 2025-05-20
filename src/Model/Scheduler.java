package Model;

public class Scheduler extends Thread {
    private Runnable r;
    private long pause = 1000;

    public Scheduler(Runnable r) {
        this.r = r;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (!((Game) r).isGameOver() && !((Game) r).isPaused()) {
                    r.run();
                }
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Ensure the thread stops gracefully
                break; // Exit the loop when interrupted
            }
        }
        System.out.println("Scheduler stopped.");
    }

    public void setPause(long pause) {
        this.pause = pause;
    }

    public long getPause() {
        return pause;
    }
}