import java.util.HashSet;
import java.util.LinkedHashSet;

public class UniqueVisitors {
    public static void main(String[] args) {
        String[] visitors = {
                "riya", "aman", "riya", "neha", "karan", "aman", "isha"
        };

        HashSet<String> uniqueSet = new HashSet<>();
        LinkedHashSet<String> orderedSet = new LinkedHashSet<>();

        for (String name : visitors) {
            uniqueSet.add(name);
            orderedSet.add(name);
        }

        System.out.println("Unique visitor count: " + uniqueSet.size());
        System.out.println("First three distinct visitors:");

        int count = 0;
        for (String name : orderedSet) {
            if (count == 3) {
                break;
            }
            System.out.println(name);
            count++;
        }
    }
}
