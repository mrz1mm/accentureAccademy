import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class appNotes {
    // non c'è bisogno di chiudere BufferedReader, BufferedWriter, e Scanner in quanto il try già gestisce automaticamente la chiusura delle risorse.

    private static final String NOME_FILE = "notes.txt";

    // 1. Aggiungere una nota
    // Assunzioni:
    // 1.1. la nota potrebbe essere vuota --> non salva la nota
    // 1.2. la nota potrebbe essere già presente --> non salva la nota
    // 1.3. la nota potrebbe essere già presente ma vuota --> caso che non sussiste
    // 1.4. la nota potrebbe essere già presente con testo --> caso che non sussiste
    private static void aggiungiNota(Scanner input) {
        System.out.println("Aggiungi una nota:");
        String nota = input.nextLine();

        // Check se la nota è vuota
        if (nota.trim().isEmpty()) {
            System.out.println("Nota vuota. Non posso aggiungerla.");
            return;
        }

        // Check se la nota è già presente
        File file = new File(NOME_FILE);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(NOME_FILE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.equals(nota)) {
                        System.out.println("La nota è già presente.");
                        return;
                    }
                }
            } catch (IOException e) {
                System.err.println("Errore durante la lettura del file " + NOME_FILE + ": " + e.getMessage());
            }
        }

        // Aggiungere la nota al file
        try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(NOME_FILE, true))) {
            bw.write(nota);
            bw.newLine();
            System.out.println("Nota aggiunta con successo.");
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura nel file " + NOME_FILE + ": " + e.getMessage());
        }
    }


    // 2. Leggere le note
    // Considerazioni:
    // 2.1. potrebbero non esserci note --> non stampa nulla
    // 2.2. potrebbero esserci note ma vuote --> stampa "La nota esiste ma è vuota"
    // 2.3. potrebbero esserci note con testo --> stampa le note
    private static void leggiTutteLeNote() {
        System.out.println("Leggo tutte le note...");
        File file = new File(NOME_FILE);

        // Check sull'esistenza delle note
        if (!file.exists() || file.length() == 0) {
            System.out.println("Nessuna nota presente.");
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_FILE))) {
            String line;
            boolean isEmpty = true;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                isEmpty = false;
            }
            if (isEmpty) {
                System.out.println("La nota esiste ma è vuota.");
            }
            
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file " + NOME_FILE + ": " + e.getMessage());
        }
        finally {
            System.out.println("*******************************************");
        }

    }


    public static void main(String[] args) {
        boolean itsover = true;

        try (Scanner input = new Scanner(System.in)) {
            while (itsover) {
            System.out.println("\nMenu");
            System.out.println("1. Aggiungi una nuova nota");
            System.out.println("2. Leggi tutte le note");
            System.out.println("3. Esci");
            System.out.print("Scegli un'opzione: ");

            String scelta = input.nextLine();

            switch (scelta) {
                case "1": aggiungiNota(input);
                    break;
                case "2":leggiTutteLeNote();
                    break;
                case "3":itsover = false;
                    System.out.println("Uscita dall'applicazione.");
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
        } catch (Exception e) {
            System.err.println("Errore durante l'inizializzazione dello scanner: " + e.getMessage());
        }
        finally {
            System.out.println("Applicazione terminata.");
        } 
    }

}