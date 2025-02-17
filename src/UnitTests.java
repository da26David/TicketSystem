import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {

    private TicketSystem ts; //Hauptticketverwaltlng

    @BeforeEach
    public void setUp() {
        ts = new TicketSystem(); // Initialisiert die TicketVerwaltung vor jedem Test
    }

    @Test
    public void testTicketVergabe() {
        int ticket1 = ts.createTicket(new Kunde(ts, "test"));
        int ticket2 = ts.createTicket(new Kunde(ts, "test"));
        assertEquals(1, ticket1);
        assertEquals(2, ticket2);
    }

    @Test
    public void testSchalterBearbeitung() throws InterruptedException {
        // Testet, ob ein Schalter korrekt ein Ticket bearbeitet
        Thread schalterThread = new Thread(() -> {
            int ticket = ts.handleTicket(); // Holt das nächste Ticket
            assertEquals(1, ticket); // Prüft, ob das richtige Ticket bearbeitet wird
        });
        schalterThread.start(); // Startet den Schalter-Thread
        Thread.sleep(100); // Wartet kurz
        ts.createTicket(new Kunde(ts, "test")); // Vergibt ein Ticket
        schalterThread.join(); // Wartet auf das Ende des Schalter-Threads
    }

    @Test
    public void testVerschiedeneKundenAnliegen() throws InterruptedException {
        // Testet zwei Kunden mit unterschiedlichen Anliegen
        Thread kunde1 = new Thread(new Kunde(ts, "Anliegen 1"));
        Thread kunde2 = new Thread(new Kunde(ts, "Anliegen 2"));
        kunde1.start(); // Startet Kunde 1
        kunde2.start(); // Startet Kunde 2
        kunde1.join(); // Wartet auf Kunde 1
        kunde2.join(); // Wartet auf Kunde 2
        assertEquals(1, ts.handleTicket()); // Prüft Ticket von Kunde 1
        assertEquals(2, ts.handleTicket()); // Prüft Ticket von Kunde 2
    }
}

    /*
    @Test
    public void testVieleKundenGleichzeitig() throws InterruptedException {
        // Testet 100 Kunden, die gleichzeitig Tickets holen
        Thread[] kunden = new Thread[100];
        for (int i = 0; i < 100; i++) {
            kunden[i] = new Thread(new Kunde(ts, "Anliegen " + i)); // Erstellt Kunden-Threads
            kunden[i].start(); // Startet jeden Kunden
        }
        for (Thread kunde : kunden) {
            kunde.join(); // Wartet auf alle Kunden-Threads
        }
        for (int i = 1; i <= 100; i++) {
            assertEquals(i, ts.handleTicket()); // Prüft, ob alle Tickets korrekt vergeben wurden
            assertEquals(i, ts.handleTicket()); // Prüft, ob alle Tickets korrekt vergeben wurden
        }
    }
}
*/