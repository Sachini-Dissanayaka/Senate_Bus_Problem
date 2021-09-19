import java.util.concurrent.Semaphore;
import java.time.LocalDateTime;

public class SharedResources {
    final public int max_seats = 50;
    public int waiting;
    public Semaphore busWait;
    public Semaphore boarded;
    public Semaphore mutex;
    public Bus bus;

    public SharedResources() {
        this.waiting = 0;
        this.busWait = new Semaphore(0);        //signal when bus arrive, wait on bus
        this.boarded = new Semaphore(0);        //bus wait on till all aboard
        this.mutex = new Semaphore(1);          //rider count protect, avoid new riders when bus is at stop
    }

}