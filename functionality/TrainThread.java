package functionality;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class TrainThread extends Thread {
    private final Queue<Passenger> trainQueue;
    private final AtomicBoolean Leaving = new AtomicBoolean(true);

    public TrainThread (Queue<Passenger> trainQueue) {
        this.trainQueue = trainQueue;
    }

    public void run() {
        while (Leaving.get()) {
            if(!trainQueue.isEmpty()) {
                //to paint object that is going to the train
                trainQueue.poll();
            } else {
                Leaving.set(false);
            }
        }
    }

}

