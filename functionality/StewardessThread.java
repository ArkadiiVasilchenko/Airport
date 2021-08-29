package functionality;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

import gui.Observer;

public class StewardessThread extends Thread {

    private final long waitTimeIfQueueEmpty = 3_000;
    private final long waitTimeToSellTicket = 5_000;
    private final long waitTimeForGroupToAirplane = 10_000;

    private final AtomicBoolean isWork = new AtomicBoolean(true);
    private final AtomicBoolean firstPlaneIsFree = new AtomicBoolean(true);
    private boolean isBusy = false;

    private final int stewardessId;
    private final Queue<Passenger> ticketsQueue;
    private final Queue<Passenger> takeoffQueue;
    private final int maxTakeoffQueueSize;

    private final  List<Plane> planeFleet;
    private final Lock lock;

    private final List<Observer> observers;

    public StewardessThread(int stewardessId,
                            Queue<Passenger> ticketsQueue,
                            Queue<Passenger> takeoffQueue,
                            int maxTakeoffQueueSize,
                            List<Plane> planeFleet,
                            Lock lock,
                            List<Observer> observers) {
        setName(StewardessThread.class.getSimpleName() + stewardessId);
        this.stewardessId = stewardessId;
        this.ticketsQueue = ticketsQueue;
        this.takeoffQueue = takeoffQueue;
        this.maxTakeoffQueueSize = maxTakeoffQueueSize;
        this.planeFleet = planeFleet;
        this.lock = lock;
        this.observers = observers;
    }



    @Override
    public void run() {
        System.out.println("StewardessThread->" + getName() + " has being started");

        while (isWork.get()) {
            boolean isWait = false;
            synchronized (ticketsQueue) {
                if (ticketsQueue.isEmpty()) {
                    isWait = true;
                }
            }

            if (isWait) {
                try {
                    Thread.sleep(waitTimeIfQueueEmpty);
                } catch (InterruptedException e) {

                }
                continue;
            }

            try {
                Thread.sleep(waitTimeToSellTicket);
            } catch (InterruptedException e) {

            }

            synchronized (ticketsQueue) {
                try {
                    lock.lock();
                    Passenger passenger = ticketsQueue.poll();
                    System.out.println("Passenger " + passenger.getId() + " bought ticket Stewardess " + stewardessId + " ticketsQueue=" + ticketsQueue.size() + " takeoffQueue=" + takeoffQueue.size());

                    takeoffQueue.add(passenger);
                    // observers.forEach(e -> e.catchEvent(Event.BOUGHT_TICKET)); //!!!!!!!!!!!!!!!!!!!!!!!!
                }
                finally {
                    lock.unlock();
                }
                if (takeoffQueue.size() == maxTakeoffQueueSize) {
                    isBusy = true;
                }


            }

            if (isBusy) {
                try {
                    System.out.println("------ takeoff stewardessId=" + stewardessId);
                    Thread.sleep(waitTimeForGroupToAirplane);
                    //TODO go to plane
                    if(firstPlaneIsFree.get()) {
                        PlaneTakeOffThread planeTakeOffThread = new PlaneTakeOffThread(planeFleet.get(0),takeoffQueue);
                        planeTakeOffThread.start();
                    } else {
                        PlaneTakeOffThread planeTakeOffThread = new PlaneTakeOffThread(planeFleet.get(1),takeoffQueue);
                        planeTakeOffThread.start();
                    }

                } catch (InterruptedException e) {

                }
                synchronized (ticketsQueue) {
                    takeoffQueue.clear();
                    isBusy = false;
                }
            }

        }
        System.out.println("Stewardess offline");
    }


    public void stopThread() {
        isWork.set(false);
    }

    public boolean isBusy() {
        return isBusy;
    }

    public Queue<Passenger> getTakeoffQueue() {
        return takeoffQueue;
    }

    public Queue<Passenger> getTicketsQueue() {
        return ticketsQueue;
    }

    public int getStewardessId() {
        return stewardessId;
    }
}
