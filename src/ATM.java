import java.util.Scanner;
public class ATM {

    public ATM() {

    }

    public void start() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Why, hello. Welcome to the ATM. ");
        System.out.println("We're going to need some information before we start, so follow along.");
        System.out.print("\nType your name: ");
        String name = scan.nextLine();
        System.out.print("Great! Now, type your PIN number that you will use: ");
        int PIN = scan.nextInt();
        scan.nextLine();
        Customer customer = new Customer(name, PIN);
        Account savingsAcc = new Account("Savings Account", customer, 0.00);
        Account checkingAcc = new Account("Checking Account", customer, 0.00);

        System.out.println("Your checking and savings accounts have been created, " + customer.getName());
        System.out.print("Now, please reenter your PIN number before gaining access to your accounts: ");

        boolean isPin = false;
        while (isPin == false) {
            int checkPIN = scan.nextInt();
            scan.nextLine();
            if (checkPIN == customer.getPIN()) {
                isPin = true;
            } else {
                System.out.print("Incorrect! please try again: ");
            }
        }

        System.out.println("Welcome!");
        mainMenu();
        System.out.print("Choose an option: ");
        int choice = scan.nextInt();
        scan.nextLine();
        while (choice != 6) {
            if (choice == 1) {
                
            }


        }

        System.out.println("Thank you for using this machine, " + customer.getName() + ". Have a great day!");
    }

    public void mainMenu() {
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Main Menu:");
        System.out.println("Deposit Money (1)");
        System.out.println("Withdraw Money (2)");
        System.out.println("Transfer Money (3)");
        System.out.println("Get Account Balance (4)");
        System.out.println("Change your PIN Number (5)");
        System.out.println("Exit (6)");

    }

}
