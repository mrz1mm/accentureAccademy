public class Rettangolo extends Figura {

    private double base;
    private double altezza;

    public Rettangolo() {
        super();
        this.base = 0;
        this.altezza = 0;
    }

    public Rettangolo(String nome, String colore, double base, double altezza) {
        super(nome, colore);

        if (base <= 0) {
            throw new IllegalArgumentException("La base deve essere maggiore di zero.");
        }
        this.base = base;
        
        if (altezza <= 0) {
            throw new IllegalArgumentException("L'altezza deve essere maggiore di zero.");
        }
        this.altezza = altezza;
    }


    // Getters
    public double getBase() { return base; }
    public double getAltezza() { return altezza; }


    // Setters
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

    
    // Override dei metodi di Figura
    @Override
    public String mostraInfo() {
        StringBuilder info = new StringBuilder(super.mostraInfo());
        info.append("Base: ").append(getBase()).append("\n");
        info.append("Altezza: ").append(getAltezza()).append("\n");
        info.append("Area: ").append(calcolaArea()).append("\n");
        info.append("Perimetro: ").append(calcolaPerimetro()).append("\n");
        return info.toString();
    }


    // Override dei metodi di FormaGeometrica
    @Override
    public double calcolaArea() { return base * altezza; }

    @Override
    public double calcolaPerimetro() { return 2 * (base + altezza); }


    // Override dei metodi di Disegnabile
    @Override
    public String disegna() {
        return "***************\n" +
               "*             *\n" +
               "*             *\n" +
               "***************\n";
    }


    // Override dei metodi di Comparabile
    @Override
    public int compara(Figura altraFigura) {
        return Double.compare(this.calcolaArea(), altraFigura.calcolaArea());
    }

}
