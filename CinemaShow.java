
public class CinemaShow {
    private String title;
    private int seatsAvailable;
    private final int capacity;

    private static int totalBooked = 0;

    CinemaShow(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        this.seatsAvailable = capacity;
    }

    CinemaShow(String title) {
        this(title, 100);
    }

    public boolean book(int n) {
        if (n > 0 && n <= seatsAvailable) {
            seatsAvailable -= n;
            totalBooked += n;
            return true;
        }

        return false;
    }

    public void cancel(int n) {
        if (n > 0) {
            seatsAvailable = Math.min(capacity, seatsAvailable + n);
        }
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public static int getTotalBooked() {
        return totalBooked;
    }

    public static void main(String[] args) {
        CinemaShow show = new CinemaShow("Avengers", 100);

        System.out.println("Movie: " + show.title);
        System.out.println("Available Seats: " +
                show.getSeatsAvailable());

        System.out.println("Book 40 seats: " + show.book(40));
        System.out.println("Available Seats: " +
                show.getSeatsAvailable());

        System.out.println("Book 50 seats: " + show.book(50));
        System.out.println("Available Seats: " +
                show.getSeatsAvailable());

        System.out.println("Book 20 seats: " + show.book(20));
        System.out.println("Available Seats: " +
                show.getSeatsAvailable());

        show.cancel(10);
        System.out.println("After cancellation: " +
                show.getSeatsAvailable());

        System.out.println("Total Booked: " +
                CinemaShow.getTotalBooked());
    }
}
