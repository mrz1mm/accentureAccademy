package instruments;

public class Chitarra extends StrumentoMusicale {

    public Chitarra() {
        super();
    }

    public Chitarra(String nome, Tipo tipo, int volume) {
        super(nome, tipo, volume);
    }


    // Override del metodo suona()
    @Override
    public void suona() {
        System.out.println("Strumming della chitarra " + getNome());
    }
    
    // Metodo che prima accorda lo strumento, poi imposta il volume ed infine suona
    public void suonaPerformance(int volume) {
        accorda();
        setVolume(volume);
        suona();    
    }
}
