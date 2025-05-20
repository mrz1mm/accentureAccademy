package instruments;

public class StrumentoMusicale {
    private String nome;
    private Tipo tipo;
    private int volume;


    public StrumentoMusicale() {
        super();
        this.nome = "N/A";
        this.tipo = null;
        this.volume = 0;
    }

    public StrumentoMusicale(String nome, Tipo tipo, int volume) {
        super();
        this.nome = nome;
        this.tipo = tipo;
        this.volume = volume;
    }


    // Getter
    public String getNome() {
        return nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public int getVolume() {
        return volume;
    }


    // Setter
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }


    // Metodo che indica l'accordatura dello strumento musicale
    public void accorda() {
        System.out.println("Accordando lo strumento musicale " + nome);
    }

    // Metodo per suonare lo strumento musicale
    public void suona() {
        System.out.println("Suonando lo strumento musicale " + nome);
    }

    // Metodo per impostare il livello del volume
    public void impostaVolume(int volume) {
        this.volume = volume;
        System.out.println("Il volume dello strumento musicale " + nome + " è stato impostato a " + volume);
    }

    // Metodo per mostrare le informazioni sullo strumento musicale
    public String showInfo() {
        return this.toString();
    }

    // Metodo toString() per restituire una rappresentazione testuale dello strumento musicale
    @Override
    public String toString() {
        return "StrumentoMusicale [nome=" + nome + ", tipo=" + tipo + ", volume=" + volume + "]";
    }
}
