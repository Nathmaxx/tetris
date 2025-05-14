package Model;

public class Scheduler extends Thread{
    private Runnable r;
    public  Scheduler(Runnable r) {
        this.r = r;

    }

    private long pause = 1000;
    public  void run(){
        while (true){
            r.run();
            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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