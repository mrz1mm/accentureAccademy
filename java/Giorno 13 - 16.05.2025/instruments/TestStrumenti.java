package instruments;

public class TestStrumenti {
    public static void main(String[] args) {
        StrumentoMusicale strumentoMusicale = new StrumentoMusicale("Strumento", Tipo.FIATO, 5);
        Chitarra chitarra = new Chitarra("Chitarra", Tipo.CORDE, 7);
        Pianoforte pianoforte = new Pianoforte("Pianoforte", Tipo.PERCUSSIONE, 10);


        // Stampa di StrumentoMusicale, dei suoi getter e setter e dei suoi metodi
        System.out.println(strumentoMusicale);

        System.out.println("Nome: " + strumentoMusicale.getNome());
        System.out.println("Tipo: " + strumentoMusicale.getTipo());
        System.out.println("Volume: " + strumentoMusicale.getVolume());

        strumentoMusicale.setNome("Strumento Aggiornato");
        strumentoMusicale.setTipo(Tipo.CORDE);
        strumentoMusicale.setVolume(8);

        strumentoMusicale.accorda();
        strumentoMusicale.suona();


        // Stampa di Chitarra, dei suoi getter e setter e dei suoi metodi
        System.out.println(chitarra);

        System.out.println("Nome: " + chitarra.getNome());
        System.out.println("Tipo: " + chitarra.getTipo());
        System.out.println("Volume: " + chitarra.getVolume());

        chitarra.setNome("Chitarra Aggiornata");
        chitarra.setTipo(Tipo.FIATO);
        chitarra.setVolume(9);

        chitarra.accorda();
        chitarra.suona();
        chitarra.suonaPerformance(10);


        // Stampa di Pianoforte, dei suoi getter e setter e dei suoi metodi
        System.out.println(pianoforte);

        System.out.println("Nome: " + pianoforte.getNome());
        System.out.println("Tipo: " + pianoforte.getTipo());
        System.out.println("Volume: " + pianoforte.getVolume());

        pianoforte.setNome("Pianoforte Aggiornato");
        pianoforte.setTipo(Tipo.CORDE);
        pianoforte.setVolume(12);

        pianoforte.accorda();
        pianoforte.suona();
        pianoforte.suonaPerformance(15);
    }
}
