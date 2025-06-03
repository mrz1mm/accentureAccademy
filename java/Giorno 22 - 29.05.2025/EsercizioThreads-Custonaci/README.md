## ASSUNZIONI

1.  **Sistema e Utente Validi:**

    - L'oggetto `TicketReservationSystem` deve essere valido e non nullo.
    - Lo `username` per una prenotazione non deve essere nullo o vuoto.

2.  **Richiesta di Biglietti:**

    - Il numero di biglietti richiesti in una singola operazione deve essere maggiore di zero.

3.  **Gestione dei Biglietti Disponibili:**

    - Il conteggio dei biglietti disponibili nel sistema non può mai scendere sotto lo zero.
    - Una prenotazione può avvenire solo se ci sono biglietti effettivamente disponibili.

4.  **Limiti per Singola Prenotazione:**

    - Un utente può richiedere un massimo di 10 biglietti per ogni singola prenotazione.
    - Se un utente richiede più di 10 biglietti, la richiesta viene automaticamente limitata a 10 biglietti.
    - Se i biglietti disponibili sono inferiori a quelli richiesti (anche se la richiesta è già stata limitata a 10), l'utente acquisterà solo il numero di biglietti effettivamente rimasti disponibili.

5.  **Comportamento degli Utenti e Concorrenza:**
    - Ogni prenotazione eseguita da un thread rappresenta un singolo tentativo di effettuare un acquisto.
    - Un utente può effettuare più tentativi di prenotazione nel tempo.
