import java.util.Random;

public class BusScheduler implements Runnable {
    private Resources resources;
    private float meanTime = 2 * 60f * 1000;
    public static Random random;

    public BusScheduler(Resources resources) {
        this.resources = resources;
        random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            try {
                float lambda = 1 / meanTime;
                Thread.sleep(Math.round(-Math.log(1 - random.nextFloat()) / lambda));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(new Bus(resources)).start();
        }
    }
}