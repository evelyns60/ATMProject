import java.util.Scanner;
public class ATM {
    private int transactionID;
    private Account checkingAcc;
    private Account savingsAcc;
    private Customer customer;

    public ATM() {
        transactionID = 1000;
    }

    Scanner scan = new Scanner(System.in);


    public void start() {

        /* uses user input to create customer object and account objects
         */
        System.out.println(Customer.getWHITE() + "\nWhy, hello. Welcome to the ATM. ");
        System.out.println("We're going to need some information before we start, so follow along.");
        System.out.print("\nType your name: ");
        String name = scan.nextLine();
        System.out.print("Great! Now, type your PIN number that you will use: ");
        int PIN = scan.nextInt();
        scan.nextLine();
        customer = new Customer(name, PIN);
        savingsAcc = new Account("Savings Account", customer, 0.00);
        checkingAcc = new Account("Checking Account", customer, 0.00);


        System.out.println("\nYour checking and savings accounts have been created, " + customer.getName());
        System.out.print("Now, please reenter your PIN number before gaining access to your accounts: ");

        //prompts user to enter correct PIN
        boolean isPin = false;
        while (!isPin) {
            int checkPIN = scan.nextInt();
            scan.nextLine();
            if (checkPIN == customer.getPIN()) {
                isPin = true;
            } else {
                System.out.print(Customer.getRED() + "Incorrect! please try again: " + Customer.getWHITE());
            }
        }

        System.out.println("\nWelcome, " + customer.getName() + "!");
        enterPIN();
        mainMenu();
        System.out.print("Choose an option: ");
        int choice = scan.nextInt();
        scan.nextLine();
        boolean willContinue = true;
        while (choice != 6 && willContinue) {
            if (choice == 1) {                            //Depositing Money
               deposit();
            } else if (choice == 2) {                     //Withdrawing Money
                System.out.print("From which account are you withdrawing from? Type (1) for checking, (2) for savings: ");
                int accountChoice = scan.nextInt();
                scan.nextLine();
                if (accountChoice == 1) {       //Checks which account the user is withdrawing from
                    withdraw(checkingAcc);
                } else {
                    withdraw(savingsAcc);
                }
            } else if (choice == 3) {                     //Transferring Money
                transfer();
            } else if (choice == 4) {                     //Prints Account Balance
                System.out.println(Customer.getBROWN() + "\nChecking Account Balance: $" + Customer.getWHITE() +  checkingAcc.getBalance());
                System.out.println(Customer.getBROWN() + "Savings Account Balance: $" + Customer.getWHITE() + savingsAcc.getBalance() + "\n");
            } else if (choice == 5) {                     //Changing PIN
                System.out.print("Type in what you want your new PIN to be: ");
                int newPIN = scan.nextInt();
                scan.nextLine();
                customer.setPIN(newPIN);
                printReceipt(4, true, 0, null);
            } else {                                      //User enters anything other than 1-6
                System.out.println("Invalid option. Try again.");
            }

            //Repeats loop if the user selects yes.
            System.out.print("Would you like to do anything else? Yes (1) or No (2) ");
            int answer = scan.nextInt();
            scan.nextLine();
            if (answer == 2) {
                willContinue = false;
            } else {
                enterPIN();
                mainMenu();
                System.out.print("Choose an option: ");
                choice = scan.nextInt();
                scan.nextLine();
            }
        }

        //Printed when user selects Choice 6 or chooses No.
        System.out.println("\nThank you for using this machine, " + customer.getName() + ". Have a great day!");
    }


    /* prints out the Main Menu that displays possible actions
     */
    public void mainMenu() {
        borderPattern();
        System.out.println("Main Menu:");
        System.out.println(Customer.getBLUE() + "• Deposit Money (1)");
        System.out.println(Customer.getBRIGHTBLUE() + "• Withdraw Money (2)");
        System.out.println(Customer.getBLUE() + "• Transfer Money (3)");
        System.out.println(Customer.getBRIGHTBLUE() + "• Get Account Balance (4)");
        System.out.println(Customer.getBLUE() + "• Change your PIN Number (5)");
        System.out.println(Customer.getBRIGHTBLUE() + "• Exit (6)" + Customer.getWHITE());
    }


    /* prompts user to enter their PIN; used everytime before the user chooses to do another action
     */
    private void enterPIN() {
        System.out.print("Please enter your PIN to proceed: ");
        int num = scan.nextInt();
        scan.nextLine();

        while (num != customer.getPIN()) {
            System.out.print(Customer.getRED() + "Incorrect. Please try again: " + Customer.getWHITE());
            num = scan.nextInt();
            scan.nextLine();
        }
    }

    private void deposit() {
        System.out.print("\nHow much money will you be depositing? ");
        double money = scan.nextDouble();
        scan.nextLine();
        System.out.print("Which account is this money being deposited in? Type (1) for checking, (2) for savings: ");
        double accountChoice = scan.nextDouble();
        scan.nextLine();
        transactionID++;
        if (accountChoice == 1) {
            checkingAcc.addBalance(money);
            printReceipt(1, true, money, checkingAcc);
        } else {
            savingsAcc.addBalance(money);
            printReceipt(1, true, money, savingsAcc);
        }
    }

    private void withdraw(Account account) {
        int money = -1;
        while ((money % 5 != 0 && money % 20 != 0) && money <= account.getBalance()) { //Checks if money input is a multiple of 5 or 20 and if money input is greater than the user's balance
            System.out.print("\nHow much money are you withdrawing? ");
            money = scan.nextInt();
            scan.nextLine();
            if (money > account.getBalance()) {
                System.out.println(Customer.getRED() + "ERROR: Insufficient Funds." + Customer.getWHITE());
                printReceipt(1, false, 0, null);
            } else if (money % 5 != 0 && money % 20 != 0) {
                System.out.println("This ATM can only withdraw " + Customer.getRED() + "$5 or $20 bills" + Customer.getWHITE() + ". Please enter a value that can be distributed among these bills. ");
            }
        }

        if (!(money > account.getBalance())) {
            int num20s = 0;
            int num5s = 0;
            boolean validNum = false;
            while (!validNum) {
                System.out.print("How many $20 bills do you want to receive? (if none, type 0): "); //Gives user the option to customize the bills they receive
                num20s = scan.nextInt();
                scan.nextLine();
                if (num20s * 20 > money) {
                    System.out.println(Customer.getRED() + "This exceeds your withdrawing amount. Try again. " + Customer.getWHITE());
                } else {
                    validNum = true;
                }
                account.removeBalance(money);
            }
            num5s = (money - (num20s * 20)) / 5;  //Uses the remaining money amount to determine how many $5 bills the user receives
            System.out.println("\nSince you asked for " + Customer.getBROWN() + num20s +  " $20 bills " + Customer.getWHITE() + ", you will also receive " + Customer.getBROWN() + num5s + " $5 bills" + Customer.getWHITE() + ".");
            transactionID ++;
            printReceipt(2, true, money, account);
        }
    }

    private void transfer() {
        System.out.print("\nFrom which account are you transferring from? Type (1) for checking, (2) for savings: ");
        int accountChoice = scan.nextInt();
        scan.nextLine();
        int money = 0;
            System.out.print("How much money are you transferring? ");
            money = scan.nextInt();
            scan.nextLine();
        if (accountChoice == 1) {
            if (checkingAcc.getBalance() >= money) { //Checks if money input is greater than the user's account balance
                checkingAcc.removeBalance(money);
                savingsAcc.addBalance(money);
                transactionID ++;
                printReceipt(3, true, money, checkingAcc);
            } else {
                System.out.println(Customer.getRED() + "ERROR: Insufficient funds." + Customer.getWHITE());
                printReceipt(3, false, 0, null);
            }
        } else {
            if (savingsAcc.getBalance() >= money) {
                savingsAcc.removeBalance(money);
                checkingAcc.addBalance(money);
                transactionID ++;
                printReceipt(3, true, money, savingsAcc);
            } else {
                System.out.println(Customer.getRED() + "ERROR: Insufficient funds." + Customer.getWHITE());
                printReceipt(3, false, 0, null);
            }

        }

    }

    /* Prints receipt after each action.
    - transactionType: an integer parameter that contains a number that corresponds to a certain transaction type
    - wasSuccessful: a boolean parameter that lets the program know whether the transaction/action was successful
    - money: a double parameter that lets the program know how much money was involved in the transaction, if any
    - account: an Account parameter that lets the program know which account had money deposited/had money withdrawn/had money transferred from
    */

    private void printReceipt(int transactionType, boolean wasSuccessful, double money, Account account) {
        System.out.println();
        borderPattern();
        System.out.println(Customer.getBROWN() + "Receipt:\n");

        if (wasSuccessful) {
            if (transactionType == 1){                                                            //Deposit Transaction
                System.out.println("• $" + money + " deposited into " + account.getAccountName());
            } else if (transactionType == 2) {                                                    //Withdraw Transaction
                System.out.println("• $" + money + " withdrawn from " + account.getAccountName());
            } else if (transactionType == 3){                                                    //Transfer Transaction
                System.out.print("• $" + money + " transferred from " );
                if (account.getAccountName().equals("Checking Account")) {
                    System.out.println(checkingAcc.getAccountName() + " to " + savingsAcc.getAccountName());
                } else {
                    System.out.println(savingsAcc.getAccountName() + " to " + checkingAcc.getAccountName());
                }
            } else {                                                                              //PIN action
                System.out.println("• PIN changed");
            }
        } else {
            System.out.println("• Transaction failed due to insufficient funds.");
            System.out.println();
        }

        System.out.println("Current Checking Account Balance: " + checkingAcc.getBalance());
        System.out.println("Current Savings Account Balance: " + savingsAcc.getBalance());
        if ((transactionType == 1 || transactionType == 2 || transactionType == 3) && wasSuccessful) {      //Checks if action is a transaction
            System.out.println("• Transaction Status: " + Customer.getPURPLE() + "Successful" + Customer.getBROWN());
            System.out.println("• Transaction ID: " + transactionID);
        } else if (!wasSuccessful) {
            System.out.println("• Transaction Status: " + Customer.getRED() + "Not Successful");
        }
        borderPattern();
    }

    //creates a border design with asterisks and dashes using a for loop
    private void borderPattern() {
        System.out.print(Customer.getBROWN());
        for (int i = 0; i < 40; i ++) {
            if (!(i % 2 == 0)) {
                System.out.print("*");
            } else {
                System.out.print("-");
            }
        }
        System.out.println(Customer.getWHITE());
    }
}