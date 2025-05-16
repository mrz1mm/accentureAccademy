import java.util.Scanner;

public class LabyrinthGame {
    // Metodo per stampare il labirinto
    public static void printLabyrinth(int[][] mapToPrint, PlayerData player, ExitData exit) {
        System.out.println("\n--- LABIRINTO ---");
        for (int i = 0; i < mapToPrint.length; i++) { // righe
            if (mapToPrint[i] == null) {
                System.out.println();
                continue;
            }
            for (int j = 0; j < mapToPrint[i].length; j++) { // colonne
                if (player != null && i == player.row && j == player.col) {
                    System.out.print("P "); // Giocatore
                } else if (exit != null && i == exit.row && j == exit.col) {
                    System.out.print("U "); // Uscita
                } else {
                    switch (mapToPrint[i][j]) {
                        case MapsElements.empty:
                            System.out.print(". ");
                            break;
                        case MapsElements.wall:
                            System.out.print("# ");
                            break;
                        case MapsElements.player:
                            System.out.print(". ");
                            break;
                        case MapsElements.exit:
                            System.out.print(". ");
                            break;
                        default:
                            System.out.print("? ");
                            break;
                    }
                }
            }
            System.out.println();
        }
        System.out.println("-----------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Definizione del labirinto (0 = percorso libero, 1 = muro, 7 = partenza, 9 = uscita)
        int[][] dungeonMap = {
            {1, 1, 9, 1, 1, 1},
            {1, 0, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 1, 1, 1},
            {1, 0, 1, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 7, 1, 1, 1}
        };

        // Crea la logica del gioco
        LabyrinthLogic gameLogic = new LabyrinthLogic(dungeonMap);

        System.out.println("Benvenuto nel Gioco del Labirinto!");
        printLabyrinth(gameLogic.getDisplayMap(), gameLogic.getPlayerData(), gameLogic.getExitData());

        // Gioco principale
        while (!gameLogic.isGameOver()) {
            System.out.print("Muovi (w=Alto, s=Basso, a=Sinistra, d=Destra, q=Esci): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("q")) {
                System.out.println("Hai scelto di uscire. Arrivederci!");
                break;
            }

            if (input.length() != 1) {
                System.out.println("Input non valido. Inserisci una singola lettera (w,a,s,d) o q.");
                continue; // Salta al prossimo input
            }

            // Ottieni il comando di movimento
            char command = input.charAt(0);
            boolean moveProcessed = gameLogic.movePlayer(command);

            // Stampa il labirinto solo se il movimento è stato elaborato o se il comando non è valido
            if (moveProcessed || (command != 'w' && command != 's' && command != 'a' && command != 'd' && command != 'q')) {
                 printLabyrinth(gameLogic.getDisplayMap(), gameLogic.getPlayerData(), gameLogic.getExitData());
            }
            // Controlla se il gioco è finito
            if (gameLogic.isGameWon()) {
                System.out.println("\n!!! HAI VINTO! !!!");
            }
        }
        scanner.close();
    }
}