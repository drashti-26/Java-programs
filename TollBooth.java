
import java.util.Scanner;

public class TollBooth {

    record Vehicle(String number, String type) {}

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int totalToll = 0;
        int bikes = 0;
        int cars = 0;
        int trucks = 0;

        System.out.println("Toll Booth System");

        while (true) {
            System.out.print("Enter vehicle number (or done to stop): ");
            String number = sc.nextLine().trim();

            if (number.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter vehicle type (bike, car, truck): ");
            String type = sc.nextLine().trim().toLowerCase();

            Vehicle vehicle = new Vehicle(number, type);

            int toll = switch (vehicle.type()) {
                case "bike" -> 20;
                case "car" -> 50;
                case "truck" -> 150;
                default -> -1;
            };

            if (toll == -1) {
                System.out.println("Invalid vehicle type.");
                continue;
            }

            totalToll += toll;

            switch (vehicle.type()) {
                case "bike" -> bikes++;
                case "car" -> cars++;
                case "truck" -> trucks++;
            }

            System.out.println("Toll collected: " + toll);
        }

        System.out.println("Total toll: " + totalToll);

        if (bikes == 0 && cars == 0 && trucks == 0) {
            System.out.println("No vehicles entered.");
        } else if (cars >= bikes && cars >= trucks) {
            System.out.println("Most frequent: car");
        } else if (bikes >= trucks) {
            System.out.println("Most frequent: bike");
        } else {
            System.out.println("Most frequent: truck");
        }

        sc.close();
    }
}
