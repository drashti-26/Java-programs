@FunctionalInterface
interface Notifier {
    void send(String message);
}

interface Urgent { }

public class NotificationDemo {
    public static void main(String[] args) {
        Notifier email = message -> System.out.println("Email: " + message);
        Notifier sms = message -> System.out.println("SMS: " + message);

        Notifier urgentEmail = (Notifier & Urgent)
                (message -> System.out.println("Urgent email: " + message));

        Notifier[] senders = {email, sms, urgentEmail};

        for (Notifier n : senders) {
            n.send("System maintenance at 6 PM");
        }

        for (Notifier n : senders) {
            if (n instanceof Urgent) {
                n.send("URGENT: Server is down");
                n.send("URGENT: Please respond immediately");
            }
        }
    }
}
