public class Bus implements Runnable {
    public int loaded=0;
    private SharedResources sharedResources;

    public Bus(SharedResources sharedResources) {
        this.sharedResources = sharedResources;
    }

    private void depart() {
        System.out.println("Bus loaded with " + loaded + " riders and " + sharedResources.waiting + " riders are left");
        System.out.println("BUS DEPARTED AT "+java.time.LocalTime.now()+" !!! \n");
    }

    @Override
    public void run() {
        try {
            sharedResources.mutex.acquire();
            System.out.println("BUS ARRIVED AT "+java.time.LocalTime.now()+" !!! \n");
            System.out.println("Riders count wait for Bus : "+ sharedResources.waiting );
            System.out.println("Riders count who can board to Bus : "+ Math.min(sharedResources.waiting.get(),50) );
            int maxRidersToBoard = Math.min(sharedResources.waiting.get(), sharedResources.max_seats);
            for (int i = 0; i < maxRidersToBoard; i++){
                sharedResources.bus=this;
                sharedResources.busWait.release();
                sharedResources.boarded.acquire();
            }

            sharedResources.waiting.set(Math.max(sharedResources.waiting.get()-sharedResources.max_seats,0));
            sharedResources.mutex.release();

            depart();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}