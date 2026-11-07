import exception.*;
import model.*;
import service.BankService;
import util.*;
import java.io.IOException;
import java.util.*;

public class MiniBank {
    private static final Scanner SC=new Scanner(System.in);
    private static final BankInfo BANK=new BankInfo("MiniBank", "Main Branch");
    private static final BankService SERVICE=new BankService();
    private static final String MENU="\n1. Open Account\n2. Deposit\n3. Withdraw\n4. Transfer\n5. View Statement\n6. List Accounts\n7. End-of-day Report\n8. Concurrent Deposit Demo\n9. Producer-Consumer Demo\n10. Exit";
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(BANK.name()+" | "+BANK.branch());
        System.out.println("========================================");
        try (BankSession session = new BankSession()) { runMenu(); }
    }
    private static void runMenu() {
        boolean running=true;
        while(running) {
            System.out.println(MENU); int choice=readInt("Choose option: ");
            try {
                switch(choice) {
                    case 1 -> openAccount();
                    case 2 -> { String id=read("Account number: "); long amount=readLong("Deposit amount: "); SERVICE.deposit(id,amount); System.out.println("Deposit successful."); }
                    case 3 -> { String id=read("Account number: "); long amount=readLong("Withdrawal amount: "); SERVICE.withdraw(id,amount); System.out.println("Withdrawal successful."); }
                    case 4 -> { String from=read("From account: "),to=read("To account: "); long amount=readLong("Transfer amount: "); SERVICE.transfer(from,to,amount); System.out.println("Transfer successful."); }
                    case 5 -> System.out.println(SERVICE.statement(read("Account number: ")));
                    case 6 -> { var accounts=SERVICE.listAccounts(); if(accounts.isEmpty()) System.out.println("No accounts found."); else accounts.forEach(System.out::println); }
                    case 7 -> System.out.println(SERVICE.report());
                    case 8 -> { String id=read("Account number: "); SERVICE.processConcurrentDepositBatch(id,10,1000,1); System.out.println("Batch completed. New balance: ₹"+SERVICE.find(id).getBalance()); }
                    case 9 -> SERVICE.runProducerConsumerDemo();
                    case 10 -> { SERVICE.save(); System.out.println("Thank you for banking with MiniBank. Goodbye!"); running=false; }
                    default -> System.out.println("Invalid choice. Enter a number from 1 to 10.");
                }
            } catch(InsufficientFundsException e) { System.out.println("Transaction failed: "+e.getMessage()+". Shortfall: ₹"+e.getShortfall()); }
              catch(BankException e) { System.out.println("Transaction failed: "+e.getMessage()); }
              catch(IOException e) { System.out.println("File operation failed: "+e.getMessage()); }
              catch(InterruptedException e) { Thread.currentThread().interrupt(); System.out.println("Operation interrupted."); }
              catch(IllegalArgumentException e) { System.out.println("Invalid input: "+e.getMessage()); }
              finally { /* Transaction attempt is complete; return to menu. */ }
        }
    }
    private static void openAccount() throws BankException {
        String owner=read("Account holder name: "); String type=read("Account type (SAVINGS/CURRENT/FIXED): ");
        long opening=readLong("Opening balance (₹): "); Account a=SERVICE.openAccount(owner,type,opening);
        System.out.println("Account created successfully: "+a);
    }
    private static String read(String prompt) { System.out.print(prompt); return SC.nextLine().trim(); }
    private static int readInt(String prompt) { while(true) try { return Integer.parseInt(read(prompt)); } catch(NumberFormatException e) { System.out.println("Please enter a valid whole number."); } }
    private static long readLong(String prompt) { while(true) try { return Long.parseLong(read(prompt)); } catch(NumberFormatException e) { System.out.println("Please enter a valid amount."); } }
}
