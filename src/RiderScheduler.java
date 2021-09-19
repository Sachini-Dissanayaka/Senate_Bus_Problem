import java.util.Random;

public class RiderScheduler implements Runnable {
    private Resources resources;
    private float meanTime = 3f * 1000;
    public static Random random;

    public RiderScheduler(Resources resources) {
        this.resources = resources;
        random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            new Thread(new Rider(resources)).start();
            try {
                float lambda = 1 / meanTime;
                Thread.sleep(Math.round(-Math.log(1 - random.nextFloat()) / lambda));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}