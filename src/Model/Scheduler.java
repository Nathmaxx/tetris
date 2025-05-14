package Model;

public class Scheduler extends Thread{
    private Runnable r;
    public  Scheduler(Runnable r) {
        this.r = r;

    }

    private long pause = 500;
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
}