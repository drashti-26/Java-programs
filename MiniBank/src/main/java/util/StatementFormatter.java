package util;
import model.Account;
public final class StatementFormatter {
    private StatementFormatter() {}
    public static String buildStatement(Account a) {
        return new StringBuilder("========== MINI BANK STATEMENT ==========\n")
            .append("Account Number : ").append(a.getAccountNumber()).append('\n')
            .append("Account Holder : ").append(a.getOwnerName()).append('\n')
            .append("Balance        : ₹").append(a.getBalance()).append('\n')
            .append("Interest Rate  : ").append(a.interestRate()).append("% p.a.\n")
            .append("Status         : ").append(a.isActive()?"Active":"Inactive").append('\n')
            .append("=========================================\n").toString();
    }
}
