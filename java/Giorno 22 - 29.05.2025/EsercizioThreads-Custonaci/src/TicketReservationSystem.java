import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class TicketReservationSystem {

    private int availableTickets;
    private final Lock bookingLock = new ReentrantLock(true);
    private final List<SuccessfulBookings> successfulBookings = new ArrayList<>();
    private final int MAX_TICKETS_PER_BOOKING = 10;

    public TicketReservationSystem() {
        super();
        this.availableTickets = 0;
    }

    public TicketReservationSystem(int initialTickets) {
        super();
        if (initialTickets < 0) {
            throw new IllegalArgumentException("Il numero iniziale di biglietti non può essere negativo.");
        }
        this.availableTickets = initialTickets;
        System.out.println("Sistema di Prenotazione inizializzato con " + initialTickets + " biglietti.");
    }


    // Getters
    public int getAvailableTickets() {
        bookingLock.lock();
        try {
            System.out.println("Biglietti disponibili: " + availableTickets);
            return availableTickets;
        } finally {
            bookingLock.unlock();
        }
    }

    public List<String> getUsersWithSuccessfulBookings() {
        bookingLock.lock();
        try {
            return successfulBookings.stream()
                                         .map(SuccessfulBookings::getUsername) // equivalente a s -> s.getUsername()
                                         .distinct()
                                         .collect(Collectors.toList());
        } finally {
            bookingLock.unlock();
        }
    }

        public List<SuccessfulBookings> getAllSuccessfulBookingDetails() {
        bookingLock.lock();
        try {
            return Collections.unmodifiableList(successfulBookings);
        } finally {
            bookingLock.unlock();
        }
    }

    public boolean processTickets(int ticketsRequested, String username) {
        if (ticketsRequested <= 0) {
            System.out.println("RICHIESTA NON VALIDA: Utente [" + username + "] ha richiesto " + ticketsRequested + " biglietti. Il numero deve essere positivo.");
            return false;
        }

        int originalRequest = ticketsRequested;
        int ticketsToActuallyBook = ticketsRequested;
        bookingLock.lock();
         try {
            if (ticketsToActuallyBook > MAX_TICKETS_PER_BOOKING) {
                System.out.println("INFO: Utente [" + username + "] ha richiesto " + originalRequest +
                                   " biglietti. La richiesta per singola transazione è limitata a " + MAX_TICKETS_PER_BOOKING +
                                   ". Tentativo di prenotare " + MAX_TICKETS_PER_BOOKING + " biglietti.");
                ticketsToActuallyBook = MAX_TICKETS_PER_BOOKING;
            }

            if (availableTickets <= 0) {
                System.out.println("PRENOTAZIONE FALLITA: Utente [" + username + "] (richiesti: " + originalRequest + ") - I biglietti sono esauriti.");
                return false;
            }

            if (availableTickets < ticketsToActuallyBook) {
                System.out.println("INFO: Utente [" + username + "] voleva prenotare " + ticketsToActuallyBook +
                                   " biglietti, ma solo " + availableTickets + " sono disponibili. Prenotazione limitata a " + availableTickets + " biglietti.");
                ticketsToActuallyBook = availableTickets;
            }

            availableTickets -= ticketsToActuallyBook;
            successfulBookings.add(new SuccessfulBookings(username, ticketsToActuallyBook));
            System.out.println("PRENOTAZIONE EFFETTUATA: Utente [" + username + "] ha prenotato " + ticketsToActuallyBook +
                               " biglietti. Biglietti rimanenti: " + availableTickets);
            return true;

        } finally {
            bookingLock.unlock();
        }
    }

}