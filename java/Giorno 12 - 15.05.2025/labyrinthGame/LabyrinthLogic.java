public class LabyrinthLogic {
    private int[][] internalMap;
    private PlayerData playerData;
    private ExitData exitData;
    private boolean gameOver = false;
    private boolean gameWon = false;
    

    public LabyrinthLogic(int[][] initialMap) {
        this.internalMap = initialMap;
        this.gameOver = false;
        this.gameWon = false;
        
        try {
            validateMapStructure();
        } catch (IllegalArgumentException e) {
            System.err.println("Errore inizializzazione - Mappa: " + e.getMessage());
            this.gameOver = true;
        }
    }


    private void validateMapStructure() throws IllegalArgumentException {
        // Per la validazione della mappa:
        // deve avere un solo giocatore (7), che può trovarsi in qualsiasi cella;
        // deve avere una sola uscita (9), che può trovarsi in qualsiasi cella;
        // il giocatore (7) e l'uscita (9) non possono essere sulla stessa cella;
        // il resto della mappa deve essere libero (0) o muro (1);
        // la mappa deve essere almeno nxm dove n>=3 e m>=3 --> una mappa 1x1 o nx2 o 2xm è tutta muri;
        // la mappa può avere qualsiasi forma;
        // deve essere circondata da muri (1), --> ovvero le celle della prima e ultima riga devono essere muro/giocatore/uscita & la prima e l'ultima cella di ogni riga devono essere muro/giocatore/uscita.

        int rows = this.internalMap.length;
        int cols = this.internalMap[0].length;
        int playerCount = 0;
        int exitCount = 0;
        PlayerData player = null;
        ExitData exit = null;

        // Controllo che la mappa non sia nulla o vuota
        if (this.internalMap == null || rows == 0 || cols == 0) {
            throw new IllegalArgumentException("La mappa fornita non è valida (nulla o senza righe).");
        }

        // Controllo sulla dimensione minima della mappa
        if (rows < 3 || cols < 3) {
            throw new IllegalArgumentException("La mappa deve essere almeno 3x3.");
        }

        for (int i = 0; i < rows; i++) { // righe
        if (this.internalMap[i] == null || this.internalMap[i].length == 0) {
            throw new IllegalArgumentException("La mappa contiene una riga nulla o vuota all'indice " + i + " (ogni riga deve avere almeno 1 colonna).");
        }
            int currentRowLength = this.internalMap[i].length;

            for (int j = 0; j < currentRowLength; j++) { // colonne
                int cellValue = this.internalMap[i][j];

                // Una cella è sul perimetro se: è nella prima riga (i == 0) o ultima riga (i == rows - 1) della mappa complessiva
                // oppure è nella prima colonna (j == 0) o ultima colonna (j == currentRowLength - 1) di quella riga.
                boolean isOnDefinedPerimeter = (i == 0 || i == rows - 1 || j == 0 || j == currentRowLength - 1);

                if (isOnDefinedPerimeter) {
                    // Sul perimetro definito sono ammessi solo muri (1), il giocatore (7) o l'uscita (9).
                    if (cellValue != MapsElements.wall &&
                        cellValue != MapsElements.player &&
                        cellValue != MapsElements.exit) {
                        throw new IllegalArgumentException("Cella del perimetro definito in [" + i + "," + j + "] " + "non è un muro (1), né un giocatore (7), né un'uscita (9). Trovato: " + cellValue);
                    }
                }

                // Conteggio e memorizzazione temporanea di giocatore/uscita
                if (cellValue == MapsElements.player) {
                    playerCount++;
                    if (playerCount > 1) throw new IllegalArgumentException("Trovato più di un giocatore (7).");
                    player = new PlayerData(i, j);
                } else if (cellValue == MapsElements.exit) {
                    exitCount++;
                    if (exitCount > 1) throw new IllegalArgumentException("Trovata più di un'uscita (9).");
                    exit = new ExitData(i, j);
                }
                // Il resto della mappa deve essere vuoto (0) o muro (1)
                else if (cellValue != MapsElements.empty && cellValue != MapsElements.wall) {
                    throw new IllegalArgumentException("La mappa contiene un valore non valido: " + cellValue +
                            " alla posizione [" + i + "," + j + "]. Sono permessi solo 0, 1, 7, 9.");
                }
            }
        }

        // Controllo che la mappa abbia esattamente un giocatore
        if (playerCount != 1) {
            throw new IllegalArgumentException("La mappa deve contenere esattamente un giocatore (7). Trovati: " + playerCount);
        }
        // Controllo che la mappa abbia esattamente un'uscita
        if (exitCount != 1) {
            throw new IllegalArgumentException("La mappa deve contenere esattamente un'uscita (9). Trovate: " + exitCount);
        }
        // Controllo che il giocatore e l'uscita non siano sulla stessa cella
        if (player.row == exit.row && player.col == exit.col) {
            throw new IllegalArgumentException("Il giocatore (7) e l'uscita (9) non possono essere sulla stessa cella.");
        }

        // Memorizza i dati del giocatore e dell'uscita
        this.playerData = player;
        this.exitData = exit;
    }

    
    public int[][] getDisplayMap() {
        if (this.internalMap == null) return new int[0][0];
        int[][] mapCopy = new int[this.internalMap.length][];
        for (int i = 0; i < this.internalMap.length; i++) {
                mapCopy[i] = new int[this.internalMap[i].length];
                for (int j = 0; j < this.internalMap[i].length; j++) {
                    mapCopy[i][j] = this.internalMap[i][j];
                }
        }
        return mapCopy;
    }

    public PlayerData getPlayerData() {
        return this.playerData;
    }

    public ExitData getExitData() {
        return this.exitData;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public boolean isGameWon() {
        return this.gameWon;
    }

    public boolean movePlayer(char command) {
         // Non puoi muoverti se hai perso
        if (this.gameOver) return true;

        if (this.playerData == null || this.exitData == null) {
            System.err.println("ERROR: Dati del giocatore o dell'uscita non inizializzati!");
            this.gameOver = true;
            return true;
        }

        int oldRow = this.playerData.row;
        int oldCol = this.playerData.col;
        int newRow = this.playerData.row;
        int newCol = this.playerData.col;

        switch (command) {
            case 'w': newRow--; break;
            case 's': newRow++; break;
            case 'a': newCol--; break; 
            case 'd': newCol++; break;
            default: 
                System.out.println("Comando non valido. Utilizza 'w', 'a', 's', 'd' per muoverti.");
                return false; 
        }

        // Mossa non valida: sei uscito dai limiti della mappa
        if (newRow < 0 || newRow >= this.internalMap.length ||
            this.internalMap[newRow] == null || newCol < 0 || newCol >= this.internalMap[newRow].length) {
            System.out.println("Movimento non valido: sei uscito dai limiti della mappa!");
            return true;
        }

        // Mossa non valida: hai colpito un muro
        if (this.internalMap[newRow][newCol] == MapsElements.wall) {
            System.out.println("Movimento non valido: hai colpito un muro!");
            return true;
        }

        // Mossa valida: aggiorna la posizione del giocatore
        this.internalMap[oldRow][oldCol] = MapsElements.empty;
        this.playerData.row = newRow;
        this.playerData.col = newCol;

        // Controllo se il giocatore ha raggiunto l'uscita
        if (this.playerData.row == this.exitData.row && this.playerData.col == this.exitData.col) {
            this.gameWon = true;
            this.gameOver = true;
        } else {
            this.internalMap[newRow][newCol] = MapsElements.player;
        }
        return true;
    }
}