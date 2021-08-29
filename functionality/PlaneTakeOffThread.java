package functionality;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class PlaneTakeOffThread extends Thread{

    private final Plane plane;
    private final Queue<Passenger> takeoffQueue;
    private final long waitTimeToEnterTheAirplane = 1_000;

    public PlaneTakeOffThread(Plane plane, Queue<Passenger> takeoffQueue) {
        this.plane = plane;
        this.takeoffQueue = takeoffQueue;
    }

    public void run() {
        System.out.println("Plane thread has started");
        synchronized(takeoffQueue) {
            while(!takeoffQueue.isEmpty()) {
                try {
                    takeoffQueue.poll();
                    Thread.sleep(waitTimeToEnterTheAirplane);
                } catch (InterruptedException e) {

                }
            }
            System.out.println("The whole group of passengers is on plane!");
        }
    }

}
