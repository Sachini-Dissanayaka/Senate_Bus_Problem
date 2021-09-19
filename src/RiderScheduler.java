import java.util.Random;

public class RiderScheduler implements Runnable {
    public static Random random;
    private SharedResources sharedResources;
    private float meanTime = 3f * 1000;

    public RiderScheduler(SharedResources sharedResources) {
        this.sharedResources = sharedResources;
        random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            new Thread(new Rider(sharedResources)).start();
            try {
                float lambda = 1 / meanTime;
                Thread.sleep(Math.round(-Math.log(1 - random.nextFloat()) / lambda));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}