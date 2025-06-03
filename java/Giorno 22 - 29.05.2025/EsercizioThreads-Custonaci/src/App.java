import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    // Impostazioni per la simulazione
    private static final int INITIAL_TOTAL_TICKETS = 20;
    private static final int NUMBER_OF_CONCURRENT_USERS = 50;

    public static void main(String[] args) {
        System.out.println("########## INIZIO SIMULAZIONE ##########");       
        // Inizializzazione del sistema di prenotazione
        TicketReservationSystem trs = new TicketReservationSystem(INITIAL_TOTAL_TICKETS);

        // Creazione di una lista per tenere traccia delle richieste degli utenti
        List<UserTicketRequest> userRequests = new ArrayList<>();

        // Generazione di richieste casuali per gli utenti
        Random random = new Random();
        for (int i = 0; i < NUMBER_OF_CONCURRENT_USERS; i++) {
            String username = "User" + (i + 1);
            int ticketsRequested = random.nextInt(1, 20);
            UserTicketRequest request = new UserTicketRequest(trs, username, ticketsRequested);
            userRequests.add(request);
        }

        // Creazione e avvio dei thread per le richieste degli utenti
        List<Thread> threads = new ArrayList<>();
        for (UserTicketRequest request : userRequests) {
            Thread thread = new Thread(request);
            threads.add(thread);
        }

        // Avvio dei thread
        for (Thread thread : threads) {
            thread.start();
        }

        // Attesa del completamento di tutti i thread
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Errore durante l'attesa del thread: " + e.getMessage());
            }
        }

        // Stampa dei risultati finali
        System.out.println("\n--- Risultati Finali ---");
        System.out.println(trs.getAvailableTickets());
        System.out.println("Utenti con prenotazioni riuscite: " + trs.getUsersWithSuccessfulBookings());
        System.out.println("Dettagli delle prenotazioni riuscite:");
        for (SuccessfulBookings booking : trs.getAllSuccessfulBookingDetails()) {
            System.out.println(booking);
        }
        System.out.println("########## FINE SIMULAZIONE ##########");           
    }

}