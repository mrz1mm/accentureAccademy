public class Cerchio extends Figura {

    private double raggio;

    public Cerchio() {
        super();
        this.raggio = 0;
    }

    public Cerchio(String nome, String colore, double raggio) {
        super(nome, colore);
        if (raggio <= 0) {
            throw new IllegalArgumentException("Il raggio deve essere maggiore di zero.");
        }
        this.raggio = raggio;
    }


    // Getters
    public double getRaggio() { return raggio; }

    
    // Setters
    public void setRaggio(double raggio) {
        if (raggio <= 0) {
            throw new IllegalArgumentException("Il raggio deve essere maggiore di zero.");
        }
        this.raggio = raggio;
    }


    // Override dei metodi di Figura
    @Override
    public String mostraInfo() {
        StringBuilder info = new StringBuilder(super.mostraInfo());
        info.append("Raggio: ").append(getRaggio()).append("\n");
        info.append("Area: ").append(calcolaArea()).append("\n");
        info.append("Perimetro: ").append(calcolaPerimetro()).append("\n");
        return info.toString();
    }


    // Override dei metodi di FormaGeometrica
    @Override
    public double calcolaArea() { return Math.PI * raggio * raggio; }

    @Override
    public double calcolaPerimetro() { return 2 * Math.PI * raggio; }


    // Override dei metodi di Disegnabile
    @Override
    public String disegna() {
    return "    ****    \n" +
           "  **    **  \n" +
           " *        * \n" +
           " *        * \n" +
           "  **    **  \n" +
           "    ****    \n";
    }


    // Override dei metodi di Comparabile
    @Override
    public int compara(Figura altraFigura) {
        return Double.compare(this.calcolaArea(), altraFigura.calcolaArea());
    }

}
