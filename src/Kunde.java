import java.util.concurrent.CountDownLatch;

public class Kunde implements Runnable{

    private String anliegen;
    private TicketSystem ticketSystem;

    private CountDownLatch latch = new CountDownLatch(1);

    public Kunde(TicketSystem ts, String a){
        this.anliegen = a;
        this.ticketSystem =ts;
    }

    @Override
    public void run(){
        int ticket = this.ticketSystem.createTicket(this);

        System.out.println("Dieser Kunde hat sein Ticket mit der Nummer: " + ticket + " gezogen");


        if(latch != null){
            try {
                latch.await();
                System.out.println("Der Kunde mit der Ticketnnumer: " + ticket + " sein Problem ist: " + anliegen );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public CountDownLatch getLatch(){
        return this.latch;
    }
    public String getAnliegen(){
        return this.anliegen;
    }
    public void wakeUp(){
        this.latch.countDown();
    }
}
