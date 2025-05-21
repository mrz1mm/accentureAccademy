public class GestoreForme {

    private Figura[] figure;
    private int numeroFigure;
    private static final int CAPACITA_DEFAULT = 10;


    public GestoreForme() {
        this(CAPACITA_DEFAULT);
    }

    public GestoreForme(int capacita) {
        super();
        if (capacita <= 0) {
            throw new IllegalArgumentException("La capacità deve essere positiva.");
        } else {
            this.figure = new Figura[capacita];
        }
        this.numeroFigure = 0;
    }


    // Getters
    public Figura[] getFigure() { return figure; }
    public int getNumeroFigure() { return numeroFigure; }


    // Setters
    public void aggiungiFigura(Figura f) {
        if (f == null) {
            throw new IllegalArgumentException("Figura nulla.");
        }

        if (numeroFigure < figure.length) {
            figure[numeroFigure] = f;
            numeroFigure++;
        } else {
            throw new ArrayIndexOutOfBoundsException("Impossibile aggiungere la figura. Capacità massima raggiunta.");
        }
    }


    // Metodi di classe
    public String mostraTutteLeFigure() {
        if (numeroFigure == 0) {
            return "Nessuna figura presente.";
        }

        StringBuilder info = new StringBuilder();
        for (int i = 0; i < numeroFigure; i++) {
            info.append(figure[i].mostraInfo()).append("\n");
            info.append(figure[i].disegna()).append("\n");
        }
        return info.toString();
    }


    public double calcolaAreaTotale() {
        double areaTotale = 0;
        for (int i = 0; i < numeroFigure; i++) {
            areaTotale += figure[i].calcolaArea();
        }
        return areaTotale;
    }

    public Figura trovaFiguraMassima() {
        if (numeroFigure == 0) {
            throw new IllegalStateException("Nessuna figura presente.");
        }

        if (numeroFigure == 1) {
            return figure[0];
        }

        Figura figuraMax = figure[0];
        for (int i = 1; i < numeroFigure; i++) {
            if (figure[i].compara(figuraMax) > 0) {
                figuraMax = figure[i];
            }
        }
        return figuraMax;
    } 

    public void svuotaGestore() {
        for (int i = 0; i < numeroFigure; i++) {
            figure[i] = null;
        }
        numeroFigure = 0;
    }

}
