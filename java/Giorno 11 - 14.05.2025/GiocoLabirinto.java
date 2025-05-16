import java.util.Scanner;

/*
Esercizio: Gioco del Labirinto
Obiettivo: Creare un gioco del labirinto in cui l'utente deve navigare da un punto iniziale a un punto di uscita in un labirinto rappresentato da un array bidimensionale.

Istruzioni per lo svolgimento:
1. Il labirinto è rappresentato da una griglia (array bidimensionale), dove alcuni elementi sono muri (1) e altri sono percorsi liberi (0).
2. L'utente inizia da un punto prefissato e deve raggiungere l'uscita.
3. L'utente può muoversi in alto, in basso, a sinistra e a destra, ma non può attraversare i muri e non può uscire dal labirinto.
4. La posizione del giocatore è identificata dal (7) mentre quella dell'uscita dal (9).
5. Quando il giocatore raggiunge la posizione di uscita, il gioco termina con successo.
6. Per il movimento, utilizzare lo Scanner di input utilizzando comandi specifici, ad esempio utilizzare i seguenti comandi per gli input: w Alto, s Basso, a Sinistra, d Destra
7. Dopo ogni input, se l'input è corretto, allora mostrare la mappa aggiornata con la nuova posizione, altrimenti un messaggio di errore specifico.
8. Separare la logica dei tasti da quella del labirinto, idealmente il labirinto potrebbe funzionare a prescindere del dispositivo utilizzato (esempio un joystick che dà le informazioni alto/basso/sx/dx).
*/

public class GiocoLabirinto {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Definizione del labirinto (0 = percorso libero, 1 = muro)
        int[][] labirinto = {
            {1, 1, 1, 1, 1, 1},
            {1, 0, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 1},
            {1, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 9},
            {1, 1, 1, 1, 1, 1}
        };

        // Posizione iniziale del giocatore
        int playerRow = 3;
        int playerCol = 2;

        // Posizione di uscita
        int exitRow = 4;
        int exitCol = 5;

        // Gioco principale
        while (true) {
            // Mostra la mappa
            mostraMappa(labirinto, playerRow, playerCol);

            // Controlla se il giocatore ha raggiunto l'uscita
            if (playerRow == exitRow && playerCol == exitCol) {
                System.out.println("Hai raggiunto l'uscita! Hai vinto!");
                break;
            }

            // Input dell'utente
            System.out.print("Inserisci il comando (w/a/s/d): ");
            String comando = scanner.nextLine();

            // Muovi il giocatore in base al comando
            switch (comando) {
                case "w":
                    if (puoiMuovere(labirinto, playerRow - 1, playerCol)) {
                        playerRow--;
                    } else {
                        System.out.println("Movimento non valido! C'è un muro.");
                    }
                    break;
                case "s":
                    if (puoiMuovere(labirinto, playerRow + 1, playerCol)) {
                        playerRow++;
                    } else {
                        System.out.println("Movimento non valido! C'è un muro.");
                    }
                    break;
                case "a":
                    if (puoiMuovere(labirinto, playerRow, playerCol - 1)) {
                        playerCol--;
                    } else {    
                        System.out.println("Movimento non valido! C'è un muro.");
                    }   
                    break;
                case "d":
                    if (puoiMuovere(labirinto, playerRow, playerCol + 1)) {
                        playerCol++;
                    } else {
                        System.out.println("Movimento non valido! C'è un muro.");
                    }
                    break;
                default:
                    System.out.println("Comando non valido! Usa w/a/s/d per muoverti.");
                    break;
            }
        }
        scanner.close();