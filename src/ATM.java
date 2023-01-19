import java.util.Scanner;
public class ATM {
    private int transactionID;

    public ATM() {
        transactionID = 1000;
    }

    Scanner scan = new Scanner(System.in);

    public void start() {
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
        while (!isPin) {
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
                System.out.print("How much money will you be depositing? ");
                double money = scan.nextDouble();
                scan.nextLine();
                System.out.print("Which account is this money being deposited in? Type (1) for checking, (2) for savings: ");
                double accountChoice = scan.nextDouble();
                scan.nextLine();
                if (accountChoice == 1) {
                    checkingAcc.addBalance(money);
                } else {
                    savingsAcc.addBalance(money);
                }
                transactionID++;
                printReceipt(1, true);
            } else if (choice == 2) {
                System.out.print("From which account are you withdrawing from? Type (1) for checking, (2) for savings: ");
                int accountChoice = scan.nextInt();
                scan.nextLine();
                if (accountChoice == 1) {
                    withdraw(checkingAcc);
                } else {
                    withdraw(savingsAcc);
                }
            } else if (choice == 3) {
                System.out.print("From which account are you transferring from? Type (1) for checking, (2) for savings: ");
                int accountChoice = scan.nextInt();
                scan.nextLine();
                boolean isValid = false;
                int money = 0;
                while (!isValid) {
                    System.out.print("How much money are you transferring? ");
                    money = scan.nextInt();
                    scan.nextLine();
                    if (accountChoice == 1) {
                        isValid = checkingAcc.getBalance() >= money;
                    } else {
                        isValid = savingsAcc.getBalance() >= money;
                    }
                    if (!isValid) {
                        System.out.println("ERROR: Insufficient Funds. Please try again. ");
                    }
                }

                if (accountChoice == 1) {
                    checkingAcc.removeBalance(money);
                    savingsAcc.addBalance(money);
                } else {
                    savingsAcc.removeBalance(money);
                    checkingAcc.addBalance(money);
                }
            } else if (choice == 4) {
                System.out.println("Checking Account Balance: $" + checkingAcc.getBalance());
                System.out.println("Savings Account Balance: $" + savingsAcc.getBalance());
            } else if (choice == 5) {
                System.out.print("Type in what you want your new PIN to be: ");
                int newPIN = scan.nextInt();
                scan.nextLine();
                customer.setPIN(newPIN);
            } else {
                System.out.println("Invalid option. Try again.");
            }

            mainMenu();
            System.out.print("Choose an option: ");
            choice = scan.nextInt();
            scan.nextLine();
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

    public void withdraw(Account account) {
        int money = -1;
        while ((money % 5 != 0 || money % 20 != 0) && money <= account.getBalance()) {
            System.out.print("How much money are you withdrawing? ");
            money = scan.nextInt();
            scan.nextLine();
            if (money > account.getBalance()) {
                System.out.println("ERROR: Insufficient Funds.");
                printReceipt(2, false);
            } else if (money % 5 != 0 && money % 20 != 0) {
                System.out.println("This ATM can only withdraw $5 or $20 bills. Please enter a value that can be distributed among these bills. ");
            }
        }

        if (!(money > account.getBalance())) {
            int num20s = 0;
            int num5s = 0;
            boolean validNum = false;
            while (!validNum) {
                System.out.print("How many $20 bills do you want to receive? (if none, type 0)");
                num20s = scan.nextInt();
                scan.nextLine();
                if (num20s * 20 > money) {
                    System.out.println("This exceeds your withdrawing amount. Try again. ");
                } else {
                    validNum = true;
                }
            }
            num5s = (money - (num20s * 20)) / 5;
            System.out.println("Since you asked for " + num20s + " $20 bills, you will also receive " + num5s + " $5 bills.");
            printReceipt(2, true);
        }
    }

    public void printReceipt(int transactionType, boolean wasSuccessful) {

    }
}