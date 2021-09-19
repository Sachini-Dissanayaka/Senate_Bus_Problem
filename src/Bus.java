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
            sharedResources.mutex.acquire();        //bus locks the mutex
            System.out.println("BUS ARRIVED AT "+java.time.LocalTime.now()+" !!! \n");
            System.out.println("Riders count wait for Bus : "+ sharedResources.waiting );
            System.out.println("Riders count who can board to Bus : "+ Math.min(sharedResources.waiting.get(),50) );
            int maxRidersToBoard = Math.min(sharedResources.waiting.get(), sharedResources.max_seats);
            for (int i = 0; i < maxRidersToBoard; i++){     //A loop to get all the available passengers on board
                sharedResources.bus=this;
                sharedResources.busWait.release();      //bus signals that it has arrived and can take a passenger on board
                sharedResources.boarded.acquire();      //Allows one passengers to get on board
            }


          /* If the number of passengers waiting earlier was greater than 50, then the remaining passengers is waiting- 50
            Else if the number of passengers who were waiting earlier was less than 50, then all of them got on board. therefore the number of remaining passengers is 0.
          */
            sharedResources.waiting.set(Math.max(sharedResources.waiting.get()-sharedResources.max_seats,0));
            sharedResources.mutex.release();        //bus unlocks the mutex

            depart();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}