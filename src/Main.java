import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        new Main().startProgram();
    }

    private void startProgram() {
        System.out.println("-----Program started-----");
        SharedResources sharedResources = new SharedResources();

        Thread busScheduler = new Thread(new BusScheduler(sharedResources));
        Thread riderScheduler = new Thread(new RiderScheduler(sharedResources));

        busScheduler.start();
        riderScheduler.start();

        try {
            busScheduler.join();
            riderScheduler.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Program terminated");
    }
}