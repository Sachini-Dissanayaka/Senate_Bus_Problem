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

            sharedResources.mutex.acquire();      //passenger locks the mutex
            sharedResources.waiting.incrementAndGet();      //increment the number of waiting passengers by one and this is an atomic instruction
            System.out.println("Rider "+sharedResources.waiting+" arrived at "+java.time.LocalTime.now());
            sharedResources.mutex.release();       //release the mutex

            sharedResources.busWait.acquire();    //acquire the bus semaphore to get on board

            board();
            sharedResources.bus.loaded+=1;          //get the count of loaded passengers
            sharedResources.boarded.release();      //once boarded, release the boarded semaphore

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
