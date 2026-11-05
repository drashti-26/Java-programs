public class PasswordChecker {
    public static String strength(String pw) {
        int score = 0;
        boolean length = pw.length() >= 8;
        boolean upper = pw.matches(".*[A-Z].*");
        boolean digit = pw.matches(".*[0-9].*");
        boolean special = pw.matches(".*[^a-zA-Z0-9].*");

        System.out.println("Password: " + pw);
        System.out.println("Length >= 8: " + length);
        System.out.println("Uppercase: " + upper);
        System.out.println("Digit: " + digit);
        System.out.println("Special character: " + special);

        if (length) score++;
        if (upper) score++;
        if (digit) score++;
        if (special) score++;

        if (score <= 1) return "Weak";
        if (score <= 3) return "Medium";
        return "Strong";
    }
}
