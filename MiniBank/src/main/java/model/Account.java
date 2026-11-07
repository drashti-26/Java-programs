package model;
import exception.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import model.annotation.Id;
import model.annotation.Positive;

public abstract class Account implements Transactable, InterestBearing, Serializable, Comparable<Account> {
    private static final long serialVersionUID = 1L;
    private static final AtomicLong COUNTER = new AtomicLong(0);
    @Id private final String accountNumber;
    private final String ownerName;
    @Positive private long balance;
    private boolean active = true;
    protected Account(String ownerName, long openingBalance) {
        this("AC" + String.format("%04d", COUNTER.incrementAndGet()), ownerName, openingBalance);
    }
    protected Account(String accountNumber, String ownerName, long openingBalance) {
        this.accountNumber=Objects.requireNonNull(accountNumber); this.ownerName=Objects.requireNonNull(ownerName);
        this.balance=openingBalance; updateCounter(accountNumber);
    }
    private static void updateCounter(String number) {
        try { COUNTER.accumulateAndGet(Long.parseLong(number.substring(2)), Math::max); } catch (RuntimeException ignored) { }
    }
    public static void synchronizeCounter(Iterable<Account> accounts) { for (Account a: accounts) updateCounter(a.accountNumber); }
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    @Override public synchronized long getBalance() { return balance; }
    public synchronized boolean isActive() { return active; }
    public synchronized void closeAccount() { active=false; }
    protected abstract boolean canWithdraw(long amount);
    public abstract double interestRate();
    @Override public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Deposit amount must be positive");
        if (!active) throw new InvalidAmountException("Account is inactive");
        balance = Math.addExact(balance, amount);
    }
    @Override public synchronized boolean withdraw(long amount) throws BankException {
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be positive");
        if (!active) throw new InvalidAmountException("Account is inactive");
        if (!canWithdraw(amount)) throw new InsufficientFundsException("Withdrawal not permitted or insufficient funds", Math.max(0, amount - Math.max(0, balance)));
        balance -= amount; return true;
    }
    protected synchronized void changeBalanceForTransfer(long delta) { balance += delta; }
    @Override public int compareTo(Account other) { return accountNumber.compareTo(other.accountNumber); }
    @Override public String toString() { return accountNumber + " | " + ownerName + " | ₹" + getBalance() + " | " + (isActive()?"Active":"Inactive"); }
    @Override public boolean equals(Object o) { return this==o || o instanceof Account a && accountNumber.equals(a.accountNumber); }
    @Override public int hashCode() { return Objects.hash(accountNumber); }
}
