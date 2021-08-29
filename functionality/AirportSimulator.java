package functionality;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import gui.Observable;
import gui.Observer;

public class AirportSimulator implements Observable{

    private  int maxAllQueueSize = 30;
    private  int maxTakeoffQueueSize = 15;
    private  int stewardessCount = 3;
    private  int passengerArriveMaxSize = 2;

    private  List<StewardessThread> stewardessThreads = new ArrayList<>();
    private  List<PassengerArriveThread> passengerArriveThreads = new ArrayList<>();
    private  List<Queue<Passenger>> ticketsQueue = new ArrayList<>();
    private  List<Queue<Passenger>> takeoffQueue = new ArrayList<>();
    private  AtomicLong passengerIdGenerator = new AtomicLong();

    private  List<Plane> planeFleet = new ArrayList<>(2);
    private  Lock lock = new ReentrantLock();
    private  List<Observer> observers =  new ArrayList();

    public void start() {
        planeFleet.add(new Plane("Plane#1"));
        planeFleet.add(new Plane("Plane#2"));

        for (int i = 0; i < stewardessCount; i++) {
            ticketsQueue.add(new LinkedList<>());
            takeoffQueue.add(new LinkedList<>());
            stewardessThreads.add(new StewardessThread(i, ticketsQueue.get(i),
                    takeoffQueue.get(i), maxTakeoffQueueSize, planeFleet,lock,observers));
        }

        TerminalMonitor terminal = new TerminalMonitor(stewardessThreads, maxTakeoffQueueSize,maxAllQueueSize,lock);

        for (int i = 0; i < passengerArriveMaxSize; i++) {
            passengerArriveThreads.add(new PassengerArriveThread(i, terminal, passengerIdGenerator,observers));
        }

        passengerArriveThreads.forEach(e -> e.start());

        stewardessThreads.forEach(e -> e.start());
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

}

