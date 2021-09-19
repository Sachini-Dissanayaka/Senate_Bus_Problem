public class Bus implements Runnable {
    private SharedResources sharedResources;
    public int loaded=0;

    public Bus(SharedResources sharedResources) {
        this.sharedResources = sharedResources;
    }

    private void depart() {
        System.out.println("Bus loaded with " + loaded + " riders and " + sharedResources.waiting + " riders are left");
        System.out.println("BUS DEPARTED !!! \n");
    }

    @Override
    public void run() {
        try {
            sharedResources.mutex.acquire();              //avoid new riders when bus is at stop
            System.out.println("BUS ARRIVED !!! \n");
            System.out.println("Riders count wait for Bus : "+ sharedResources.waiting );
            System.out.println("Riders count who can board to Bus : "+ Math.min(sharedResources.waiting,50) );
            int maxRidersToBoard = Math.min(sharedResources.waiting,50);
            for(int i=0; i<maxRidersToBoard; i++){
                sharedResources.bus=this;
                sharedResources.busWait.release();    //Signal riders that bus arrived
                sharedResources.boarded.acquire();    //Wait till 50 or less are aboard
            }

            sharedResources.waiting = Math.max(sharedResources.waiting-50,0);
            sharedResources.mutex.release();

            depart();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}