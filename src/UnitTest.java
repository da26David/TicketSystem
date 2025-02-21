import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;


public class UnitTest {
    TicketSystem ts;
    Kunde dummy;
    @BeforeEach
    public void setup(){
        ts = new TicketSystem();
        dummy = new Kunde(ts, "Testing");
    }
    @Test
    public void ticketVergabe(){
        int ticket1 = ts.createTicket(dummy);
        int ticket2 = ts.createTicket(dummy);
        assertEquals(1, ticket1);
        assertEquals(2, ticket2);

    }
    @Test
    void testHandleTicket() throws InterruptedException {
        TicketSystem ticketSystem = new TicketSystem();
        Kunde kunde1 = new Kunde(ticketSystem, "Hardwaredefekt");
        Kunde kunde2 = new Kunde(ticketSystem, "Netzwerkproblem");

        int ticket1 = ticketSystem.createTicket(kunde1);
        int ticket2 = ticketSystem.createTicket(kunde2);

        assertEquals(ticket1, ticketSystem.handleTicket());
        assertEquals(ticket2, ticketSystem.handleTicket());
    }
    @Test
    public void stressTest() throws InterruptedException {
        Thread[] kunden = new Thread[100];
        Thread[] mitarbeieter = new Thread[100];
        for (int  i = 0; i < 100; i++){
            kunden[i] = new Thread(new Kunde(ts, "Anliegen " + i));
            kunden[i].start();

        }
        for (int i = 1; i <= 100; i++) {
            assertEquals(i, ts.handleTicket()); // Prüft, ob alle Tickets korrekt vergeben wurden
            ts.wakeUpKunde(i);
        }


        for (Thread kunde : kunden) {
            kunde.join(); // Wartet auf alle Kunden-Threads
        }

    }
}
