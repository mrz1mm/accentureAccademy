public class Triangolo extends Figura {

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

        // Validazione base
        if (latoA <= 0 || latoB <= 0 || latoC <= 0) {
            throw new IllegalArgumentException("Tutti i lati del triangolo devono essere maggiori di zero.");
        }

        // Validazione disuguaglianza triangolare
        if (latoA + latoB <= latoC || latoA + latoC <= latoB || latoB + latoC <= latoA) {
            throw new IllegalArgumentException("La somma di due lati deve essere maggiore del terzo.");
        }

        // Validazione triangolo scaleno
        if (latoA == latoB || latoA == latoC || latoB == latoC) {
            throw new IllegalArgumentException("Il triangolo deve essere scaleno, i lati devono essere diversi.");
        }

        this.latoA = latoA;
        this.latoB = latoB;
        this.latoC = latoC;
    }


    // Getters
    public double getLatoA() { return latoA; }
    public double getLatoB() { return latoB; }
    public double getLatoC() { return latoC; }

    
    // Setters
    // Il triangolo deve essere scaleno, quindi i lati devono essere diversi
    public void setLatoA(double latoA) {
        if (latoA <= 0) {
            throw new IllegalArgumentException("Il lato A deve essere maggiore di zero.");
        }
        // Verifica se la modifica mantiene la validità del triangolo e la condizione di scaleno
        if (!isTriangoloValido(latoA, this.latoB, this.latoC)) {
            throw new IllegalArgumentException("La modifica del lato A rende il triangolo non valido.");
        }
        if (latoA == this.latoB || latoA == this.latoC) { // Controllo scaleno
            throw new IllegalArgumentException("Il lato A deve essere diverso dagli altri lati per un triangolo scaleno.");
        }
        this.latoA = latoA;
    }

    public void setLatoB(double latoB) {
        if (latoB <= 0) {
            throw new IllegalArgumentException("Il lato B deve essere maggiore di zero.");
        }
        // Verifica se la modifica mantiene la validità del triangolo e la condizione di scaleno
        if (!isTriangoloValido(this.latoA, latoB, this.latoC)) {
            throw new IllegalArgumentException("La modifica del lato B rende il triangolo non valido.");
        }
        if (latoB == this.latoA || latoB == this.latoC) { // Controllo scaleno
            throw new IllegalArgumentException("Il lato B deve essere diverso dagli altri lati per un triangolo scaleno.");
        }
        this.latoB = latoB;
    }

    public void setLatoC(double latoC) {
        if (latoC <= 0) {
            throw new IllegalArgumentException("Il lato C deve essere maggiore di zero.");
        }
        // Verifica se la modifica mantiene la validità del triangolo e la condizione di scaleno
        if (!isTriangoloValido(this.latoA, this.latoB, latoC)) {
            throw new IllegalArgumentException("La modifica del lato C rende il triangolo non valido.");
        }
        if (latoC == this.latoA || latoC == this.latoB) { // Controllo scaleno
            throw new IllegalArgumentException("Il lato C deve essere diverso dagli altri lati per un triangolo scaleno.");
        }
        this.latoC = latoC;
    }


    // Helper
    // Metodo per la disuguaglianza triangolare
    private boolean isTriangoloValido(double a, double b, double c) {
        return (a + b > c) && (a + c > b) && (b + c > a);
    }


    // Override dei metodi di Figura
    @Override
    public String mostraInfo() {
        StringBuilder info = new StringBuilder(super.mostraInfo());
        info.append("Lato A: ").append(getLatoA()).append("\n");
        info.append("Lato B: ").append(getLatoB()).append("\n");
        info.append("Lato C: ").append(getLatoC()).append("\n");
        info.append("Area: ").append(calcolaArea()).append("\n");
        info.append("Perimetro: ").append(calcolaPerimetro()).append("\n");
        return info.toString();
    }


    // Override dei metodi di FormaGeometrica
    public double calcolaArea() {
        double s = calcolaPerimetro() / 2;
        return Math.sqrt(s * (s - latoA) * (s - latoB) * (s - latoC));
    }

    public double calcolaPerimetro() { return latoA + latoB + latoC; }


    // Override dei metodi di Disegnabile
    @Override
    public String disegna() {
        return "   *   \n" +
               "  * *  \n" +
               " *   * \n" +
               "*     *\n" +
               "*******\n";
    }


    // Override dei metodi di Comparabile
    @Override
    public int compara(Figura altraFigura) {
        return Double.compare(this.calcolaArea(), altraFigura.calcolaArea());
    }

}
