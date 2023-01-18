public class Account {
    private String accountName;
    private double balance;
    private Customer customer;

    public Account(String name, Customer customer, double balance) {
        this.customer = customer;
        accountName = name;
        this.balance = balance;
    }

    public void addBalance(double money) {
        balance += money;
    }

    public void removeBalance(double money) {
        balance -= money;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountName() {
        return accountName;
    }


}
