public class Lamp {

    public static final LampState ON_STATE = new OnState();
    public static final LampState OFF_STATE = new OffState();

    private LampState currentState;

    public Lamp() {
        super();
        this.currentState = OFF_STATE;
    }

    public Lamp(LampState state) {
        super();
        this.currentState = state;
    }


    // Getters
    public LampState getCurrentState() {
        return this.currentState;
    }

    public String getState() {
        if (currentState == ON_STATE) {
            return "On";
        } else if (currentState == OFF_STATE) {
            return "Off";
        }
        return "Unknown";
    }


    // Setters
    public void setState(LampState state) { this.currentState = state;  }


    // Methods
    public void turnOn() {
        setState(ON_STATE);
        System.out.println("Lampada accesa.");
    }

    public void turnOff() {
        setState(OFF_STATE);
        System.out.println("Lampada spenta.");
    }

    public void toggleTurnOnOff() {
        if (this.currentState == ON_STATE) {
            turnOff();
        } else {
            turnOn();
        }
    }

}
