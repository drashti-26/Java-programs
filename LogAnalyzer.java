import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogAnalyzer {
    public static void main(String[] args) {
        try {
            Path file1 = Paths.get("server1.txt");
            Path file2 = Paths.get("server2.txt");

            BufferedWriter writer1 = Files.newBufferedWriter(file1);
            writer1.write("User login successful");
            writer1.newLine();
            writer1.write("Connection failed");
            writer1.newLine();
            writer1.close();

            BufferedWriter writer2 = Files.newBufferedWriter(file2);
            writer2.write("User login successful");
            writer2.newLine();
            writer2.write("User logout");
            writer2.newLine();
            writer2.close();

            Path[] files = {file1, file2};
            String keyword = "login";
            int totalLines = 0;
            int matchingLines = 0;

            for (Path file : files) {
                BufferedReader reader = Files.newBufferedReader(file);
                String line;

                while ((line = reader.readLine()) != null) {
                    totalLines++;
                    if (line.toLowerCase().contains(keyword.toLowerCase())) {
                        matchingLines++;
                    }
                }
                reader.close();

                System.out.println(file.getFileName() + " size: " +
                        Files.size(file) + " bytes");
            }

            System.out.println("Total lines: " + totalLines);
            System.out.println("Lines containing '" + keyword + "': " + matchingLines);
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}
