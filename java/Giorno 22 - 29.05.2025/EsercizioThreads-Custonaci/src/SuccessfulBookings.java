public class SuccessfulBookings {
    private final String username;
    private final int ticketsBooked;

    public SuccessfulBookings() {
        this.username = "Default User";
        this.ticketsBooked = 0;
    }

    public SuccessfulBookings(String username, int ticketsBooked) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username non può essere null o vuoto");
        }
        if (ticketsBooked < 0) {
            throw new IllegalArgumentException("Il numero di biglietti prenotati non può essere negativo");
        }
        this.username = username;
        this.ticketsBooked = ticketsBooked;
    }


    // Getters
    public String getUsername() { return username; }
    public int getTicketsBooked() { return ticketsBooked; }


    // Helpers methods
    @Override
    public String toString() {
        return "Utente: " + username + ", Biglietti prenotati: " + ticketsBooked;
    }

}
