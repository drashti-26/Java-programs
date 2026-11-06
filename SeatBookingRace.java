public class SeatBookingRace {
    static class Booking {
        int seatsLeft = 5;
        int successfulBookings = 0;

        void bookWithoutSynchronization() {
            if (seatsLeft > 0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                seatsLeft--;
                successfulBookings++;
            }
        }

        synchronized void bookWithSynchronization() {
            if (seatsLeft > 0) {
                seatsLeft--;
                successfulBookings++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Booking first = new Booking();
        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> first.bookWithoutSynchronization());
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Without synchronization:");
        System.out.println("Successful bookings: " + first.successfulBookings);
        System.out.println("Seats left: " + first.seatsLeft);

        Booking second = new Booking();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> second.bookWithSynchronization());
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("With synchronization:");
        System.out.println("Successful bookings: " + second.successfulBookings);
        System.out.println("Seats left: " + second.seatsLeft);
    }
}
