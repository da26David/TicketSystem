public class Mitarbeiter implements Runnable {
    private String name;
    private TicketSystem ticketSystem;


    public Mitarbeiter(String n, TicketSystem ts) {
        this.name = n;
        this.ticketSystem = ts;
    }

    @Override
    public void run() {

        try {
            while (true) {
                int ticket = ticketSystem.handleTicket();

                if (ticket == -1) {
                    System.out.println(name + " hat Feierabend!!!");
                    break;
                }
                System.out.println("Kunde mit der Nummer: " + ticket + " wird aufgerufen.");
                ticketSystem.wakeUpKunde(ticket);
                String anliegen = ticketSystem.getAnliegen(ticket);
                Thread.sleep(2000);
                System.out.println("Ticket" + ticket + " mit dem Problem: " + anliegen + " wurde erledigt von " + name );
            }

        } catch (InterruptedException e) {
            System.out.println(name + " hat ein Problem");
        }
    }
}
