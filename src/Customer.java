public class Customer {
    private static final String BLUE = "\u001B[36m";
    private static final String RED = "\u001B[31m";
    private static final String BROWN = "\u001B[33m";
    private static final String WHITE = "\u001B[37m";
    private static final String BLACK = "\u001B[30m";
    private static final String CYAN = "\033[0;34m";
    private static final String BRIGHT_BLUE = "\033[0;94m";
    private static final String PURPLE = "\033[0;35m";
    private String name;
    private int PIN;

    public Customer(String name, int PIN) {
        this.name = CYAN + name + WHITE;
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

    public static String getWHITE() { return WHITE; }

    public static String getRED() { return RED; }

    public static String getBRIGHTBLUE() {return BRIGHT_BLUE;}

    public static String getBLUE() {return BLUE;}

    public static String getPURPLE() {return PURPLE; }

    public static String getBROWN() {return BROWN;}

}