public class TurnOffCommand implements Command {

    private Lamp lamp;

    public TurnOffCommand(Lamp lamp) {
        this.lamp = lamp;
    }

    @Override
    public void execute() {
        lamp.turnOff();
        System.out.println("La lampada è ora spenta. (da TurnOffCommand)");
    }

}
