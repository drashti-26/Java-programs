package service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import model.Account;
public class AccountStore {
    private final ConcurrentHashMap<String,Account> accounts=new ConcurrentHashMap<>();
    private final List<String> transactionHistory=Collections.synchronizedList(new ArrayList<>());
    public void add(Account a) { accounts.put(a.getAccountNumber(),a); }
    public Account get(String id) { return accounts.get(id); }
    public Collection<Account> getAll() { return new ArrayList<>(accounts.values()); }
    public void record(String line) { transactionHistory.add(line); }
    public List<String> getTransactionHistory() { synchronized(transactionHistory) { return new ArrayList<>(transactionHistory); } }
    public ArrayList<Account> sortedByBalance() {
        ArrayList<Account> list=new ArrayList<>(accounts.values());
        list.sort(Comparator.comparingLong(Account::getBalance).reversed()); return list;
    }
    public ConcurrentHashMap<String,Account> concurrentMap() { return accounts; }
}
