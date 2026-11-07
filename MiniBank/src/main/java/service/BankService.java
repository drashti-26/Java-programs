package service;
import exception.*;
import model.*;
import util.*;
import static util.Validator.isValidMobile;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class BankService {
    private final Repository<Account> repository=new Repository<>();
    private final AccountStore store=new AccountStore();
    private final Path dataFile=Path.of("data","accounts.dat");
    private final Path logFile=Path.of("data","logs","transactions.log");
    public BankService() {
        try { Account[] loaded=StatePersister.load(dataFile); Account.synchronizeCounter(Arrays.asList(loaded)); for(Account a:loaded) addLoaded(a); }
        catch(IOException|ClassNotFoundException e) { System.err.println("Could not load previous data: "+e.getMessage()); }
    }
    private void addLoaded(Account a) { repository.save(a); store.add(a); }
    public Account openAccount(String owner,String type,long opening) throws BankException {
        if(owner==null || owner.isBlank() || owner.length()>60) throw new InvalidAmountException("Enter a valid account holder name");
        if(opening<0) throw new InvalidAmountException("Opening balance cannot be negative");
        Account a=switch(type.toUpperCase()) {
            case "SAVINGS" -> new SavingsAccount(owner,opening);
            case "CURRENT" -> new CurrentAccount(owner,opening,5000);
            case "FIXED", "FIXED_DEPOSIT" -> new FixedDepositAccount(owner,opening);
            default -> throw new InvalidAmountException("Choose SAVINGS, CURRENT or FIXED");
        };
        repository.save(a); store.add(a); log("OPEN "+a.getAccountNumber()+" "+opening); return a;
    }
    public Account find(String id) throws AccountNotFoundException { Account a=store.get(id); if(a==null) throw new AccountNotFoundException(id); return a; }
    public void deposit(String id,long amount) throws BankException { Account a=find(id); a.deposit(amount); log("DEPOSIT "+id+" "+amount); store.record("DEPOSIT "+id+" "+amount); }
    public void withdraw(String id,long amount) throws BankException { Account a=find(id); a.withdraw(amount); log("WITHDRAW "+id+" "+amount); store.record("WITHDRAW "+id+" "+amount); }
    public void transfer(String fromId,String toId,long amount) throws BankException {
        if(amount<=0) throw new InvalidAmountException("Transfer amount must be positive");
        Account from=find(fromId), to=find(toId); if(from.equals(to)) throw new InvalidAmountException("Source and destination must differ");
        Account first=from.compareTo(to)<0?from:to, second=first==from?to:from;
        synchronized(first) { synchronized(second) { from.withdraw(amount); to.deposit(amount); } }
        log("WITHDRAW "+fromId+" "+amount); log("DEPOSIT "+toId+" "+amount);
        store.record("TRANSFER "+fromId+" "+toId+" "+amount);
    }
    public String statement(String id) throws BankException { return StatementFormatter.buildStatement(find(id)); }
    public ArrayList<Account> listAccounts() { return store.sortedByBalance(); }
    public void save() throws IOException { StatePersister.save(repository.findAll().toArray(Account[]::new),dataFile); }
    public String report() throws IOException { return ReportGenerator.generate(logFile.getParent(),Path.of("data","report.txt")); }
    public void processConcurrentDepositBatch(String id,int threads,int depositsPerThread,long amount) throws BankException {
        Account account=find(id);
        try(TransactionProcessor processor=new TransactionProcessor()) {
            for(int i=0;i<threads;i++) processor.submit(new AccountWorker(account,depositsPerThread,amount));
        }
        log("BATCH_DEPOSIT "+id+" "+((long)threads*depositsPerThread*amount));
    }
    public boolean validateContact(String mobile,String email) { return Validator.isValidMobile(mobile)&&Validator.isValidEmail(email); }
    public WithdrawRule getWithdrawRule() { return (account,amount)->amount>0 && account.getBalance()>=amount; }
    public boolean validateMobileWithStaticImport(String mobile) { return isValidMobile(mobile); }
    public boolean[] demonstrateWithdrawRules(Account account,long amount) {
        WithdrawRule anonymous = new WithdrawRule() {
            @Override public boolean allow(Account a,long value) { return value>0 && a.getBalance()>=value; }
        };
        WithdrawRule lambda = (a,value) -> value>0 && a.getBalance()>=value;
        return new boolean[]{anonymous.allow(account,amount),lambda.allow(account,amount)};
    }
    public void runProducerConsumerDemo() throws InterruptedException {
        TransactionBuffer<String> buffer=new TransactionBuffer<>(2);
        Thread producer=new Thread(() -> { try { for(int i=1;i<=5;i++) buffer.put("TXN-"+i); } catch(InterruptedException e) { Thread.currentThread().interrupt(); } },"Transaction-Producer");
        Thread consumer=new Thread(() -> { try { for(int i=1;i<=5;i++) System.out.println("Processed "+buffer.take()); } catch(InterruptedException e) { Thread.currentThread().interrupt(); } },"Transaction-Consumer");
        producer.start(); consumer.start(); producer.join(); consumer.join();
    }
    public AccountStore getStore() { return store; }
    private void log(String line) { try { TransactionLog.append(logFile,line); } catch(IOException e) { System.err.println("Log warning: "+e.getMessage()); } }
}
