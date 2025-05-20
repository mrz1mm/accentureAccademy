package bases;

public abstract class DispositivoIntelligente {

    private String sistemaOperativo;

    public DispositivoIntelligente() {
        this.sistemaOperativo = "N/A";
    }

    public DispositivoIntelligente(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }


    // Getter
    public String getSistemaOperativo() {
        return sistemaOperativo;
    }


    // Setter
    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }


    // Funzioni
    public void aggiornaSoftware() {
        System.out.println("Aggiornamento del software.");
    }

}
