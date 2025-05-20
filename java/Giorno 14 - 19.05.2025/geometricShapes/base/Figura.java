public abstract class Figura {

    private String nome;
    private String colore;

    public Figura() {
        this.nome = null;
        this.colore = null;
    }

    public Figura(String nome, String colore) {
        this.nome = nome;
        this.colore = colore;
    }


    // Getter
    public String getNome() {
        return nome;
    }

    public String getColore() {
        return colore;
    }


    // Setter
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }


    // Funzioni
    public void mostraInfo() {
        System.out.println("Nome: " + getNome());
        System.out.println("Colore: " + getColore());
    }

}
