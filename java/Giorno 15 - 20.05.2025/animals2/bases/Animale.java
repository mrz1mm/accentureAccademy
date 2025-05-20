package animals2.bases;

public abstract class Animale {

    private String nome;
    private String nomeSpecie;
    private int anni;
    private int durataVitaMassimaAnni;

    public Animale() {
        this.nome = "Animale";
        this.nomeSpecie = "Sconosciuta";
        this.anni = 0;
        this.durataVitaMassimaAnni = 0;
    }

    public Animale(String nome, String nomeSpecie, int anni, int durataVitaMassimaAnni) {
        this.nome = nome;
        this.nomeSpecie = nomeSpecie;
        this.anni = anni;
        this.durataVitaMassimaAnni = durataVitaMassimaAnni;
    }


    // Getters
    public String getNome() { return nome; };
    public String getNomeSpecie() { return nomeSpecie; };
    public int getAnni() { return anni; };
    public int getDurataVitaMassimaAnni() { return durataVitaMassimaAnni; };


    // Setters
    public void setNome(String nome) { this.nome = nome; };
    public void setNomeSpecie(String nomeSpecie) { this.nomeSpecie = nomeSpecie; };
    public void setAnni(int anni) { this.anni = anni; };
    public void setDurataVitaMassimaAnni(int durataVitaMassimaAnni) { this.durataVitaMassimaAnni = durataVitaMassimaAnni; };


    // Metodi astratti
    public abstract String emettiVerso();

    public abstract String muoviti();


    // Metodi concreti
    public String mostraInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Tipo Animale: ").append(this.getClass().getSimpleName()).append("\n");
        info.append("Nome: ").append(this.nome).append("\n");
        info.append("Età: ").append(this.anni).append(" ann").append(this.anni == 1 ? "o" : "i").append("\n");
        info.append("Nome Specie: ").append(this.nomeSpecie).append("\n");
        info.append("Durata Vita Massima: ").append(this.durataVitaMassimaAnni).append(" ann").append(this.durataVitaMassimaAnni == 1 ? "o" : "i").append("\n");
        return info.toString();
    }

    public void invecchia() {
        this.anni++;
    }

    public void invecchia(int anniDaAggiungere) {
        if (anniDaAggiungere <= 0) {
            throw new IllegalArgumentException("Non puoi invecchiare di un numero negativo di anni.");
        }

        if (this.anni + anniDaAggiungere > this.durataVitaMassimaAnni) {
            throw new IllegalArgumentException("L'animale non può vivere più di " + this.durataVitaMassimaAnni + " anni.");
        }

        this.anni += anniDaAggiungere;
    }

}
