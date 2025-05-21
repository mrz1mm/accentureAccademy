public abstract class Figura implements FormaGeometrica, Disegnabile, Comparabile {

    private String nome;
    private String colore;

    public Figura() {
        super();
        this.nome = null;
        this.colore = null;
    }

    public Figura(String nome, String colore) {
        super();
        this.nome = nome;
        this.colore = colore;
    }


    // Getters
    public String getNome() { return nome; }
    public String getColore() { return colore; }


    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setColore(String colore) { this.colore = colore; }


    // Metodi astratti
    // Implementazione di FormaGeometrica
    public abstract double calcolaArea();
    public abstract double calcolaPerimetro();

    // Implementazione di Disegnabile
    public abstract String disegna();

    // Implementazione di Comparabile
    public abstract int compara(Figura altraFigura);


    // Metodi concreti
    public String mostraInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Nome: ").append(getNome()).append("\n");
        info.append("Colore: ").append(getColore()).append("\n");
        return info.toString();
    }

}
