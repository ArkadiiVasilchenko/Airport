package functionality;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TerminalMonitor {
    private final List<StewardessThread> stewardesses;
    private final int maxTakeoffQueueSize;
    private final int maxAllQueueSize;
    private final Lock lock;

    private static Queue<Passenger> trainQueue;


    public TerminalMonitor(List<StewardessThread> stewardesses,
                           int maxTakeoffQueueSize,
                           int maxAllQueueSize,
                           Lock lock) {
        this.stewardesses = stewardesses;
        this.maxTakeoffQueueSize = maxTakeoffQueueSize;
        this.maxAllQueueSize = maxAllQueueSize;
        this.lock = lock;
    }

    public boolean queueSizeChecking(StewardessThread stewardess) {
        Queue<Passenger> ticketQueue = stewardess.getTicketsQueue();
        Queue<Passenger> forTakeoff = stewardess.getTakeoffQueue();
        return ticketQueue.size() + forTakeoff.size() > maxTakeoffQueueSize;
    }

    public boolean allQueueSizeChecking(List<StewardessThread> stewardesses) {
        int count=0;
        try {
            lock.lock();
            for(int i=0;i < stewardesses.size(); i++) {
                StewardessThread stewardess = stewardesses.get(i);
                Queue<Passenger> ticketQueue = stewardess.getTicketsQueue();
                Queue<Passenger> forTakeoff = stewardess.getTakeoffQueue();
                count =+ ticketQueue.size()+forTakeoff.size();
            }
        } finally {
            lock.unlock();
        }
        return count > maxAllQueueSize;
    }

    public void tryToGetTicket(Passenger passenger) {
        if(allQueueSizeChecking(stewardesses)) {
            //TODO go train
            trainQueue.add(passenger);
            TrainThread trainThread = new TrainThread(trainQueue);
            trainThread.start();
            return;
        }
        for (int i = 0; i < stewardesses.size(); i++) {
            StewardessThread stewardess = stewardesses.get(i);
            Queue<Passenger> ticketQueue = stewardess.getTicketsQueue();
            Queue<Passenger> forTakeoff = stewardess.getTakeoffQueue();

            // TODO maxAllQueueSize checking
            synchronized (ticketQueue) {
                if(queueSizeChecking(stewardess)) {
                    continue;
                }
            }

            if ( ! stewardess.isBusy()) {
                synchronized (ticketQueue) {
                    if (ticketQueue.size() + forTakeoff.size() < maxTakeoffQueueSize) {
                        ticketQueue.add(passenger);
                        System.out.println("\tTerminalMonitor->Passenger " + passenger.getId() + " added to the ticketQueue | " + stewardess.getName() + " stewardess=" + stewardess.getStewardessId());
                        break;
                    }
                }
            } else {
                continue;
            }
        }

        // TODO go to train
    }
}


