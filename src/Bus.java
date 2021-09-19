public class Bus implements Runnable {
    private Resources resources;
    public int loaded=0;

    public Bus(Resources resources) {
        this.resources = resources;
    }

    private void depart() {
        System.out.println("Bus loaded with " + loaded + " riders and " + resources.waiting + " riders are left");
        System.out.println("BUS DEPARTED !!! \n");
    }

    @Override
    public void run() {
        try {
            resources.mutex.acquire();              //avoid new riders when bus is at stop
            System.out.println("BUS ARRIVED !!! \n");
            System.out.println("Riders count wait for Bus : "+ resources.waiting );
            System.out.println("Riders count who can board to Bus : "+ Math.min(resources.waiting,50) );
            if (resources.waiting > 0) {
                resources.bus=this;
                resources.busWait.release();    //Signal riders that bus arrived
                resources.boarded.acquire();    //Wait till 50 or less are aboard
            }
            depart();
            resources.mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}