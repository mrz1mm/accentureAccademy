package instruments;

public enum Tipo {
    CORDE("A corde"),
    FIATO("A fiato"),
    PERCUSSIONE("A percussione");
    
    private final String tipo;

    Tipo(String tipo) {
        this.tipo = tipo;
    }

    // Getter
    public String getTipo() {
        return tipo;
    }

    // Nessun setter, in quanto il tipo è immutabile

    
    // Metodo toString() per restituire una rappresentazione testuale del tipo
    @Override
    public String toString() {
        return tipo;
    }
}
