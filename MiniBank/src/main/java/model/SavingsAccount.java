package model;
public class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    private final long minBalance;
    public SavingsAccount(String owner, long opening, long minBalance) { super(owner, opening); this.minBalance=minBalance; }
    public SavingsAccount(String owner, long opening) { this(owner, opening, 500); }
    @Override public double interestRate() { return 4.0; }
    @Override protected boolean canWithdraw(long amount) { return getBalance()-amount >= minBalance; }
    public long getMinBalance() { return minBalance; }
}
