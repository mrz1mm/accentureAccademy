# Test su Java vs C# per i Generics

    Prof, ho fatto un paio di test, perché ricordavo che C# fosse l'unico in grado di distinguere i generics a runtime, e spoiler, ricordavo bene.

## TESI

    1.  Cosa succede a compile-time;
    2.  Cosa succede a runtime.

## OUTPUT CONSOLE

    --- INFORMAZIONI A RUNTIME (Java) ---
    listString.getClass(): class java.util.ArrayList
    listInteger.getClass(): class java.util.ArrayList
    => Sono istanze della stessa classe: ArrayList

    --- INFORMAZIONI A RUNTIME (C#) ---
    listString.GetType(): System.Collections.Generic.List`1[System.String]
    listInteger.GetType(): System.Collections.Generic.List`1[System.Int32]
    => Le classi sono DIVERSE a runtime: List`1 e List`1

## RISULTATO A COMPILE-TIME

    In entrambi i casi, il compilatore è "intelligente":
    *   **Distingue** correttamente che una `List<String>` deve contenere solo stringhe e una `List<Integer>` solo numeri.
    *   **Impedisce** di aggiungere tipi sbagliati (es. un numero in una lista di stringhe). Questa è la type safety che ci aspettiamo!

## RISULTATO A RUNTIME

    *   **Java usa il Type Erasure:** le info `<TipoSpecifico>` vengono "cancellate" prima del via, e a runtime le liste generiche sono un po' tutte uguali nella loro forma base (tipo `ArrayList`). La JVM, guardando la classe della lista, non sa più se era per Stringhe o Interi.
    *   **C# usa la Reification:** le info `<TipoSpecifico>` se le tiene strette anche quando il programma è in esecuzione, quindi `List<string>` e `List<int>` rimangono tipi distinti e riconoscibili.
