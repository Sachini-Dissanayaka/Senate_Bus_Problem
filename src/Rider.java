public class Rider implements Runnable {
    private Resources resources;

    public Rider(Resources resources) {
        this.resources = resources;
    }

    private void board() {
        System.out.println("RIDER : board to bus.");
    }

    @Override
    public void run() {
        try {

            resources.mutex.acquire();      //rider count protect, avoid new riders when bus is at stop
            resources.waiting += 1;
            System.out.println("Rider on waiting");
            resources.mutex.release();

            resources.busWait.acquire();    //Waiting for bus, lock when boarding so only 1 board at same time

            board();

            resources.bus.loaded+=1;
            if(resources.bus.loaded==50 || resources.bus.loaded==resources.waiting){
                resources.waiting=Math.max(resources.waiting - 50, 0);
                resources.boarded.release();
            }else {
                resources.busWait.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
