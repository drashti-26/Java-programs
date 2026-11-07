import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import exception.*;
import model.Account;
import service.BankService;

class BankServiceTest {
    @Test void depositIncreasesBalance() throws Exception {
        BankService bank=new BankService(); Account a=bank.openAccount("JUnit User","CURRENT",1000);
        bank.deposit(a.getAccountNumber(),500); assertEquals(1500,a.getBalance());
    }
    @Test void withdrawalBeyondAvailableLimitThrows() throws Exception {
        BankService bank=new BankService(); Account a=bank.openAccount("JUnit User","SAVINGS",1000);
        assertThrows(InsufficientFundsException.class,()->bank.withdraw(a.getAccountNumber(),900));
    }
    @Test void concurrentDepositsKeepBalanceCorrect() throws Exception {
        BankService bank=new BankService(); Account a=bank.openAccount("JUnit User","CURRENT",0);
        bank.processConcurrentDepositBatch(a.getAccountNumber(),10,1000,1); assertEquals(10000,a.getBalance());
    }
}
