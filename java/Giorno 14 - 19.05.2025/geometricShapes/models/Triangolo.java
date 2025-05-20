public class Triangolo extends Figura implements FormaGeometrica {

    private double latoA;
    private double latoB;
    private double latoC;

    public Triangolo() {
        super();
        this.latoA = 0;
        this.latoB = 0;
        this.latoC = 0;
    }

    public Triangolo(String nome, String colore, double latoA, double latoB, double latoC) {
        super(nome, colore);
        this.latoA = latoA;
        this.latoB = latoB;
        this.latoC = latoC;
    }

    public double calcolaArea() {
        double s = calcolaPerimetro() / 2;
        return Math.sqrt(s * (s - latoA) * (s - latoB) * (s - latoC));
    }

    public double calcolaPerimetro() {
        return latoA + latoB + latoC;
    }


    // Getter
    public double getLatoA() {
        return latoA;
    }

    public double getLatoB() {
        return latoB;
    }

    public double getLatoC() {
        return latoC;
    }


    // Il triangolo deve essere scaleno, quindi i lati devono essere diversi
    // Setter
    public void setLatoA(double latoA) {
        if (latoA <= 0) {
            throw new IllegalArgumentException("Il lato A deve essere maggiore di zero.");
        }
        if (latoA == latoB || latoA == latoC) {
            throw new IllegalArgumentException("Il lato A deve essere diverso dagli altri lati.");
        }
        this.latoA = latoA;
    }

    public void setLatoB(double latoB) {
        if (latoB <= 0) {
            throw new IllegalArgumentException("Il lato B deve essere maggiore di zero.");
        }
        if (latoB == latoA || latoB == latoC) {
            throw new IllegalArgumentException("Il lato B deve essere diverso dagli altri lati.");
        }
        this.latoB = latoB;
    }

    public void setLatoC(double latoC) {
        if (latoC <= 0) {
            throw new IllegalArgumentException("Il lato C deve essere maggiore di zero.");
        }
        if (latoC == latoA || latoC == latoB) {
            throw new IllegalArgumentException("Il lato C deve essere diverso dagli altri lati.");
        }
        this.latoC = latoC;
    }


    @Override
    public void mostraInfo() {
        super.mostraInfo();
        System.out.println("Lato A: " + getLatoA());
        System.out.println("Lato B: " + getLatoB());
        System.out.println("Lato C: " + getLatoC());
        System.out.println("Area: " + calcolaArea());
        System.out.println("Perimetro: " + calcolaPerimetro());
    }

}
