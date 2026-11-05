import java.util.Scanner;

public class ChatFilter {
    public static void main(String[] args) {
        String[] logs = {
            "10:05 alice Hello there",
            "10:10 bob Good morning",
            "10:15 carol See you tomorrow",
            "malformed line"
        };

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine().toLowerCase();

        int count = 0;
        StringBuilder report = new StringBuilder();

        for (String line : logs) {
            String[] parts = line.split(" ", 3);
            if (parts.length < 3) continue;

            if (parts[2].toLowerCase().contains(keyword)) {
                count++;
                report.append(parts[0]).append(" ")
                      .append(parts[1]).append(": ")
                      .append(parts[2]).append("\n");
            }
        }

        System.out.println("Matches: " + count);
        System.out.print(report);
    }
}
