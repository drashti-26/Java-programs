package model;
public class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;
    private final long overdraftLimit;
    public CurrentAccount(String owner, long opening, long overdraftLimit) { super(owner, opening); this.overdraftLimit=overdraftLimit; }
    @Override public double interestRate() { return 0.0; }
    @Override protected boolean canWithdraw(long amount) { return getBalance()-amount >= -overdraftLimit; }
    public long getOverdraftLimit() { return overdraftLimit; }
}
