interface Switchable {
    void on();
    void off();

    default void toggle() {
        on();
        off();
    }
}

class Fan implements Switchable {
    public void on() { System.out.println("Fan is ON"); }
    public void off() { System.out.println("Fan is OFF"); }
}

class Light implements Switchable {
    public void on() { System.out.println("Light is ON"); }
    public void off() { System.out.println("Light is OFF"); }
}

@FunctionalInterface
interface SwitchRule {
    boolean maySwitchOn(String device, int hour);
}

public class RemoteControl {
    public static void main(String[] args) {
        Switchable[] devices = {new Fan(), new Light()};
        for (Switchable d : devices) d.toggle();

        SwitchRule rule1 = new SwitchRule() {
            public boolean maySwitchOn(String device, int hour) {
                return hour >= 6 && hour <= 22;
            }
        };

        SwitchRule rule2 = (device, hour) -> hour >= 6 && hour <= 22;

        System.out.println("Anonymous class: " + rule1.maySwitchOn("Fan", 20));
        System.out.println("Lambda: " + rule2.maySwitchOn("Light", 23));
    }
}
