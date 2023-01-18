public class Customer {
    private String name;
    private int PIN;

    public Customer(String name, int PIN) {
        this.name = name;
        this.PIN = PIN;
    }

    public void setPIN(int newPIN) {
        PIN = newPIN;
    }

    public String getName() {
        return name;
    }

    public int getPIN() {
        return PIN;
    }

}
