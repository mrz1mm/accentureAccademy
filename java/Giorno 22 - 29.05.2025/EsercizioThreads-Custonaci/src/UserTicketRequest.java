public class UserTicketRequest implements Runnable {

    private final TicketReservationSystem trs;
    private final String username;
    private final int ticketsRequested;


    public UserTicketRequest() {
        super();
        this.trs = new TicketReservationSystem();
        this.username = "Default User";
        this.ticketsRequested = 0;
    }

    public UserTicketRequest(TicketReservationSystem trs, String username, int ticketsRequested) {
        super();
        if (trs == null ){
            throw new IllegalArgumentException("Il sistema di prenotazione dei biglietti non può essere null");
        }
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Il nome utente non può essere null o vuoto");
        }
        this.trs = trs;
        this.username = username;
        this.ticketsRequested = ticketsRequested;
    }


    // Getters
    public TicketReservationSystem getTicketReservationSystem() { return trs; }
    public String getUsername() { return username; }
    public int getTicketsRequested() { return ticketsRequested; }

    @Override
    public void run() {
        System.out.println("TENTATIVO: Utente [" + username + "] tenta di prenotare " + ticketsRequested + " biglietti...");
        trs.processTickets(ticketsRequested, username);
    }

}
