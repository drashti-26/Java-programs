package util;
import java.io.*;
import java.nio.file.*;
import model.Account;
public final class StatePersister {
    private StatePersister() {}
    public static void save(Account[] accounts, Path file) throws IOException {
        if (file.getParent()!=null) Files.createDirectories(file.getParent());
        try (ObjectOutputStream out=new ObjectOutputStream(Files.newOutputStream(file))) { out.writeObject(accounts); }
    }
    public static Account[] load(Path file) throws IOException, ClassNotFoundException {
        if (!Files.exists(file)) return new Account[0];
        try (ObjectInputStream in=new ObjectInputStream(Files.newInputStream(file))) { return (Account[])in.readObject(); }
    }
}
