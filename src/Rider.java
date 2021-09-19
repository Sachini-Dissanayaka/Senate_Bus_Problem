public class Rider implements Runnable {
    private SharedResources sharedResources;

    public Rider(SharedResources sharedResources) {
        this.sharedResources = sharedResources;
    }

    private void board() {
        System.out.println("RIDER : board to bus.");
    }

    @Override
    public void run() {
        try {

            sharedResources.mutex.acquire();      //rider count protect, avoid new riders when bus is at stop
            sharedResources.waiting += 1;
            System.out.println("Rider on waiting");
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
