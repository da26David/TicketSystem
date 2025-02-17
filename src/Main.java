public class Main {
    public static void main(String[] args) throws InterruptedException {
        TicketSystem ts = new TicketSystem();

        Kunde k1 = new Kunde(ts, "Falsche Farbe");
        Kunde k2 = new Kunde(ts, "Zu klein");

        Mitarbeiter m1 = new Mitarbeiter("Max", ts);
        Mitarbeiter m2 = new Mitarbeiter("Laura", ts);

        Thread kunde1 = new Thread(k1);
        Thread kunde2 = new Thread(k2);
        Thread mitarbeiter1 = new Thread(m1);
        Thread mitarbeiter2 = new Thread(m2);
        mitarbeiter1.start();
        mitarbeiter2.start();
        kunde1.start();
        kunde2.start();


        Thread.sleep(5000);

        ts.shutdown();
        ts.shutdown();

        mitarbeiter1.join();
        mitarbeiter2.join();
        System.out.println("System beendet");
    }
}