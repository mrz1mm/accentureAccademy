package animals2.interfaces;

public interface Volante {

    public String vola();
    public String vola(int distanza);
    
    public String atterra();
    public String atterra(String luogo);
    
    public String decolla();
    public String decolla(String luogo);
    
    public String plana();
    public String plana(int distanza);

}
