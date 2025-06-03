public class OnState implements LampState {

    public OnState() {
        super();
    }

    @Override
    public void pressSwitch(Lamp lamp) {
        System.out.println("Lampada accesa. (da OnState.pressSwitch)");
        lamp.turnOn();
    }

    @Override
    public String toString() {
        return "On";
    }

}
