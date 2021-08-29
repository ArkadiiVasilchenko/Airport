package functionality;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import gui.Observer;

public class PassengerArriveThread extends Thread {

    private int passengerArriveId;
    private final TerminalMonitor terminal;
    private final AtomicLong passengerIdGenerator;//final???
    private final AtomicBoolean isWork = new AtomicBoolean(true);
    private final long timeBetweenArrive = 4_000;
    private final List<Observer> observers;

    public PassengerArriveThread(int passengerArriveId, TerminalMonitor terminal,
                                 AtomicLong passengerIdGenerator,List<Observer> observers) {
        setName(PassengerArriveThread.class.getSimpleName() + passengerArriveId);
        this.passengerArriveId = passengerArriveId;
        this.terminal = terminal;
        this.passengerIdGenerator = passengerIdGenerator;
        this.observers = observers;
    }

    @Override
    public void run() {
        System.out.println("PassengerArriveThread->" + getName() + " has being started");
        while (isWork.get()) {
            observers.forEach(e -> e.catchEvent(Event.ARRIVE_OF_PASSENGER));
            Passenger passenger =
                    new Passenger(passengerIdGenerator.getAndIncrement());
            terminal.tryToGetTicket(passenger);
            try {
                Thread.sleep(timeBetweenArrive);
            } catch (InterruptedException e) {

            }
        }
        System.out.println("!!!PassengerArriveThread is done!!!");
    }

    public void stopThread() {
        isWork.set(false);
    }
}