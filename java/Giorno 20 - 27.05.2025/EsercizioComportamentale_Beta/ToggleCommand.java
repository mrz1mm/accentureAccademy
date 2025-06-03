public class ToggleCommand implements Command {

    private Lamp lamp;

    public ToggleCommand(Lamp lamp) {
        this.lamp = lamp;
    }

    @Override
    public void execute() {
        lamp.toggleTurnOnOff();
        if ("On".equals(lamp.getState())) {
            System.out.println("La lampada è ora accesa. (da ToggleCommand)");
        } else {
            System.out.println("La lampada è ora spenta. (da ToggleCommand)");
        }
    }

}
