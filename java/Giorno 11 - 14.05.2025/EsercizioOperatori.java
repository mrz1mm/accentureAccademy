import java.util.Scanner;

/*  
Obiettivo:
Scrivi un programma Java che analizza tre numeri interi recuperati da input utente con Scanner e
poi determina e stampa:
1. Il numero più grande.
2. Se la somma dei primi due numeri è maggiore del terzo.
3. Se almeno uno dei numeri è pari.
Note
• Chiamare la classe EsercizioOperatori
*/

public class EsercizioOperatori {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input dei tre numeri
        System.out.print("Inserisci il primo numero: ");
        int num1 = scanner.nextInt();
        System.out.print("Inserisci il secondo numero: ");
        int num2 = scanner.nextInt();
        System.out.print("Inserisci il terzo numero: ");
        int num3 = scanner.nextInt();

        // 1. Trova il numero più grande
        int maxNum = Math.max(num1, Math.max(num2, num3));
        System.out.println("Il numero più grande è: " + maxNum);

        // 2. Controlla se la somma dei primi due numeri è maggiore del terzo
        boolean isSumGreater = (num1 + num2) > num3;
        System.out.println("La somma dei primi due numeri è maggiore del terzo? " + isSumGreater);

        // 3. Controlla se almeno uno dei numeri è pari
        boolean isAtLeastOneEven = (num1 % 2 == 0) || (num2 % 2 == 0) || (num3 % 2 == 0);
        System.out.println("Almeno uno dei numeri è pari? " + isAtLeastOneEven);

        scanner.close();
    }
    
}


