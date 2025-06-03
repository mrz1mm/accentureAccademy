public class OffState implements LampState {

    public OffState() {
        super();
    }

    @Override
    public void pressSwitch(Lamp lamp) {
        System.out.println("Lampada spenta. (da OffState.pressSwitch)");
        lamp.turnOff();
    }

    @Override
    public String toString() {
        return "Off";
    }

}
