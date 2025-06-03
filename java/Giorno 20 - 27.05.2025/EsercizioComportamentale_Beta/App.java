public class App {

    public static void main(String[] args) {

        // Creazione di una lampada (utilizza la tua nuova Lamp.java)
        Lamp lamp = new Lamp(); // Inizia nello stato OFF

        // Creazione dei comandi
        Command turnOnCommand = new TurnOnCommand(lamp);
        Command turnOffCommand = new TurnOffCommand(lamp);
        Command toggleCommand = new ToggleCommand(lamp);

        // Creazione di un telecomando
        RemoteControl remoteControl = new RemoteControl();
        remoteControl.add("turnOn", turnOnCommand);
        remoteControl.add("turnOff", turnOffCommand);
        remoteControl.add("toggle", toggleCommand);

        // Lo stato iniziale viene ora dalla stringa restituita da lamp.getState()
        System.out.println("Stato iniziale della lampada: " + lamp.getState());

        // Esecuzione dei comandi tramite il telecomando
        System.out.println("\nEseguo TurnOnCommand:");
        remoteControl.execute("turnOn");
        System.out.println("Stato attuale della lampada: " + lamp.getState());

        System.out.println("\nEseguo TurnOffCommand:");
        remoteControl.execute("turnOff");
        System.out.println("Stato attuale della lampada: " + lamp.getState());

        System.out.println("\nEseguo ToggleCommand (da Off a On):");
        remoteControl.execute("toggle");
        System.out.println("Stato attuale della lampada: " + lamp.getState());

        System.out.println("\nEseguo ToggleCommand (da On a Off):");
        remoteControl.execute("toggle");
        System.out.println("Stato attuale della lampada: " + lamp.getState());

        // Test opzionale di LampState.pressSwitch()
        System.out.println("\nTest diretto di LampState.pressSwitch():");
        System.out.println("Stato lampada prima di pressSwitch diretto: " + lamp.getState());

        // Chiamata diretta a pressSwitch() sull'oggetto stato corrente
        LampState currentStateObject = lamp.getCurrentState();
        System.out.println("Chiamo pressSwitch() sull'oggetto stato corrente (" + currentStateObject.toString() + "):");
        currentStateObject.pressSwitch(lamp);

        System.out.println("Stato lampada dopo pressSwitch diretto: " + lamp.getState());

        // Test di esecuzione di un comando non registrato
        System.out.println("\nProvo comando non esistente:");
        try {
            remoteControl.execute("nonExistentCommand");
        } catch (IllegalArgumentException e) {
            System.out.println("Eccezione catturata: " + e.getMessage());
        }

        // Test di esecuzione di un comando con chiave null
        System.out.println("\nProvo comando con chiave null:");
        try {
            remoteControl.execute(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Eccezione catturata: " + e.getMessage());
        }
    }
}