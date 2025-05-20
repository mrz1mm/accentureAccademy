public class Rettangolo extends Figura implements FormaGeometrica {

    private double base;
    private double altezza;

    public Rettangolo() {
        super();
        this.base = 0;
        this.altezza = 0;
    }

    public Rettangolo(String nome, String colore, double base, double altezza) {
        super(nome, colore);
        this.base = base;
        this.altezza = altezza;
    }


    // Getter
    public double getBase() {
        return base;
    }

    public double getAltezza() {
        return altezza;
    }


    // Setter
    public void setBase(double base) {
        if (base <= 0) {
            throw new IllegalArgumentException("La base deve essere maggiore di zero.");
        }
        this.base = base;
    }

    public void setAltezza(double altezza) {
        if (altezza <= 0) {
            throw new IllegalArgumentException("L'altezza deve essere maggiore di zero.");
        }
        this.altezza = altezza;
    }

    
    // Funzioni
    @Override
    public void mostraInfo() {
        super.mostraInfo();
        System.out.println("Base: " + getBase());
        System.out.println("Altezza: " + getAltezza());
        System.out.println("Area: " + calcolaArea());
        System.out.println("Perimetro: " + calcolaPerimetro());
    }

    @Override
        public double calcolaArea() {
        return base * altezza;
    }

    @Override
    public double calcolaPerimetro() {
        return 2 * (base + altezza);
    }

}
