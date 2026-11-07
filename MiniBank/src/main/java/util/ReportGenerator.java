package util;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
public final class ReportGenerator {
    private ReportGenerator() {}
    public static String generate(Path logsDir, Path reportFile) throws IOException {
        long deposits=0, withdrawals=0; StringBuilder details=new StringBuilder();
        if (Files.isDirectory(logsDir)) try (DirectoryStream<Path> stream=Files.newDirectoryStream(logsDir,"*.log")) {
            for (Path file:stream) {
                if (Files.size(file)==0) continue;
                try (BufferedReader reader=Files.newBufferedReader(file)) {
                    String line; while((line=reader.readLine())!=null) {
                        String[] p=line.trim().split("\\s+"); if(p.length<3) continue;
                        try { long amount=Long.parseLong(p[2]); if(p[0].equals("DEPOSIT")) deposits+=amount; else if(p[0].equals("WITHDRAW")) withdrawals+=amount; } catch(NumberFormatException ignored) {}
                    }
                }
                BasicFileAttributes a=Files.readAttributes(file,BasicFileAttributes.class);
                details.append(file.getFileName()).append(" | size=").append(a.size()).append(" bytes | modified=").append(a.lastModifiedTime()).append('\n');
            }
        }
        String report="MINI BANK END-OF-DAY REPORT\nTotal deposits: ₹"+deposits+"\nTotal withdrawals: ₹"+withdrawals+"\nNet change: ₹"+(deposits-withdrawals)+"\n\nFILES\n"+details;
        if(reportFile.getParent()!=null) Files.createDirectories(reportFile.getParent());
        Files.writeString(reportFile,report,StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
        return report;
    }
}
