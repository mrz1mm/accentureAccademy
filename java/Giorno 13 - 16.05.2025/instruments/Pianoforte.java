package instruments;

public class Pianoforte extends StrumentoMusicale {
    
    public Pianoforte() {
        super();
    }
    
    public Pianoforte(String nome, Tipo tipo, int volume) {
        super(nome, tipo, volume);
    }
    
    
    // Override del metodo suona()
    @Override
    public void suona() {
        System.out.println("Esecuzione di un arpeggio " + getNome());
    }

     // Metodo che prima accorda lo strumento, poi imposta il volume ed infine suona
    public void suonaPerformance(int volume) {
        accorda();
        setVolume(volume);
        suona();    
    }    
}
