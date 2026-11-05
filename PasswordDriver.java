public class PasswordDriver {
    public static void main(String[] args) {
        String[] passwords = {"abc", "Abcd1234!", "hello123", "PASSWORD"};
        for (String pw : passwords) {
            System.out.println("Strength: " + PasswordChecker.strength(pw));
            System.out.println("--------------------");
        }
    }
}
