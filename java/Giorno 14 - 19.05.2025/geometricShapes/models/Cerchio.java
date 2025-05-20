public class Cerchio extends Figura implements FormaGeometrica {

    private double raggio;

    public Cerchio() {
        super();
        this.raggio = 0;
    }

    public Cerchio(String nome, String colore, double raggio) {
        super(nome, colore);
        this.raggio = raggio;
    }


    // Getter
    public double getRaggio() {
        return raggio;
    }

    // Setter
    public void setRaggio(double raggio) {
        if (raggio <= 0) {
            throw new IllegalArgumentException("Il raggio deve essere maggiore di zero.");
        }
        this.raggio = raggio;
    }


    // Funzioni
    @Override
    public void mostraInfo() {
        super.mostraInfo();
        System.out.println("Raggio: " + getRaggio());
        System.out.println("Area: " + calcolaArea());
        System.out.println("Perimetro: " + calcolaPerimetro());
        System.out.println("------------------------");
    }

    @Override
        public double calcolaArea() {
        return Math.PI * raggio * raggio;
    }

    @Override
    public double calcolaPerimetro() {
        return 2 * Math.PI * raggio;
    }

}
