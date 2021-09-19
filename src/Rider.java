public class Rider implements Runnable {
    private SharedResources sharedResources;

    public Rider(SharedResources sharedResources) {
        this.sharedResources = sharedResources;
    }

    private void board() {
        System.out.println("Rider boarded to bus at "+java.time.LocalTime.now());
    }

    @Override
    public void run() {
        try {

            sharedResources.mutex.acquire();      //rider count protect, avoid new riders when bus is at stop
            sharedResources.waiting.incrementAndGet();
//            sharedResources.waiting += 1;
            System.out.println("Rider "+sharedResources.waiting+" arrived at "+java.time.LocalTime.now());
            sharedResources.mutex.release();

            sharedResources.busWait.acquire();    //Waiting for bus, lock when boarding so only 1 board at same time

            board();
            sharedResources.bus.loaded+=1;
            sharedResources.boarded.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
