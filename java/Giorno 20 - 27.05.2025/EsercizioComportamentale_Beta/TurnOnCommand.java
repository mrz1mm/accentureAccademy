public class TurnOnCommand implements Command {

    private Lamp lamp;

    public TurnOnCommand(Lamp lamp) {
        this.lamp = lamp;
    }

    @Override
    public void execute() {
        lamp.turnOn();
        System.out.println("La lampada è ora accesa. (da TurnOnCommand)");
    }

}
