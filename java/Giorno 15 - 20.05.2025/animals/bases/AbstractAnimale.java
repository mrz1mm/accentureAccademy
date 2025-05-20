package animals.bases;
import animals.interfaces.Animale;

public abstract class AbstractAnimale implements Animale {

    private String nome;
    private int eta;
    
    public AbstractAnimale() {
        this.nome = "Animale";
        this.eta = 0;
    }

    public AbstractAnimale(String nome, int eta) {
        this.nome = nome;
        this.eta = eta;
    }


    // Getters
    public String getNome() {
        return nome;
    }

    public int getEta() {
        return eta;
    }


    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }


    // Metodi astratti
    @Override
    public abstract String emettiVerso();

    @Override
    public abstract String muoviti();

    // Metodo per mostrare le informazioni dell'animale
    public String mostraInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Tipo Animale: ").append(this.getClass().getSimpleName()).append("\n");
        info.append("Nome: ").append(this.nome).append("\n");
        // se volessi che se anni = 1 scrivesse "anno" invece di "anni"
        info.append("Età: ").append(this.eta).append(" ann").append(this.eta == 1 ? "o" : "i").append("\n");
        return info.toString();
    }

}