import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.*;


public class TicketSystem {
    private int number = 0;
    private Queue<Integer> schlange = new LinkedList<>();
    Lock lock = new ReentrantLock();
    private final Condition available = lock.newCondition();

    private Map<Integer, Kunde> customerCountdown = new ConcurrentHashMap<>();

    public int createTicket(Kunde n) {
        lock.lock();
        try {
            number++;
            schlange.add(number);
            System.out.println("Ticket mit der Nummer " + number + " vergeben.");
            customerCountdown.put(number, n);
            available.signalAll();
            return number;
        } finally {
            lock.unlock();
        }

    }

    public int handleTicket() {
        lock.lock();
        try {
            while (schlange.isEmpty()) {
                available.await();
            }
            int ticketNumber = schlange.poll();
            return ticketNumber;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
    public void shutdown(){
        lock.lock();
        try{
            schlange.add(-1);
            available.signalAll();
        }
        finally {
            lock.unlock();
        }
    }

    public void wakeUpKunde(int ticket){
        this.customerCountdown.get(ticket).wakeUp();
    }
    public String getAnliegen(int ticket){
        return this.customerCountdown.get(ticket).getAnliegen();
    }


}
