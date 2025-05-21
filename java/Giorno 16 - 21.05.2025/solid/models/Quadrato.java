public class Quadrato extends Figura {

    private double lato;

    public Quadrato() {
        super();
        this.lato = 0;
    }

    public Quadrato(String nome, String colore, double lato) {
        super(nome, colore);
        if (lato <= 0) {
            throw new IllegalArgumentException("Il lato deve essere maggiore di zero.");
        }
        this.lato = lato;
    }


    // Getters
    public double getLato() { return lato; }


    // Setters
    public void setLato(double lato) {
        if (lato <= 0) {
            throw new IllegalArgumentException("Il lato deve essere maggiore di zero.");
        }
        this.lato = lato;
    }


    // Override dei metodi di Figura
    @Override
    public String mostraInfo() {
        StringBuilder info = new StringBuilder(super.mostraInfo());
        info.append("Lato: ").append(getLato()).append("\n");
        info.append("Area: ").append(calcolaArea()).append("\n");
        info.append("Perimetro: ").append(calcolaPerimetro()).append("\n");
        return info.toString();
    }


    // Override dei metodi di FormaGeometrica
    @Override
    public double calcolaArea() { return lato * lato; }

    @Override
    public double calcolaPerimetro() { return 4 * lato; }


    // Override dei metodi di Disegnabile
    @Override
    public String disegna() { 
        return "*******\n" +
               "*     *\n" +
               "*     *\n" +
               "*******\n";
    }


    // Override dei metodi di Comparabile
    @Override
    public int compara(Figura altraFigura) {
        return Double.compare(this.calcolaArea(), altraFigura.calcolaArea());
    }

}
