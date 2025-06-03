
import java.util.HashMap;
import java.util.Map;

public class RemoteControl {

    private Map<String, Command> commands;

    public RemoteControl() {
        super();
        commands = new HashMap<>();
    }

    public void add(String key, Command c) {
        if (key == null || c == null) {
            throw new IllegalArgumentException("Key e comando non possono essere nulli");
        }
        commands.put(key, c);
    }

    public void execute(String s) {
        if (s == null || !commands.containsKey(s)) {
            throw new IllegalArgumentException("Chiave del comando non può essere nulla");
        }
        
        if (commands.containsKey(s)) commands.get(s).execute();
        else throw new IllegalArgumentException("Comando non trovato: " + s);
    }

}
