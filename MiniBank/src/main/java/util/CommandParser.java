package util;
import model.*;
public final class CommandParser {
    private CommandParser() {}
    public static Command parse(String line) {
        if (line==null) throw new IllegalArgumentException("Command cannot be empty");
        String[] p=line.trim().split("\\s+");
        if (p.length!=3) throw new IllegalArgumentException("Use: TYPE ACCOUNT_NUMBER AMOUNT");
        TransactionType type=TransactionType.valueOf(p[0].toUpperCase());
        long amount=Long.parseLong(p[2]);
        if (amount<=0) throw new IllegalArgumentException("Amount must be positive");
        return new Command(type,p[1].toUpperCase(),amount);
    }
}
