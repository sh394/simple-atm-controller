import java.util.InputMismatchException;
import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {

        //init Scanner
        Scanner sc = new Scanner(System.in);

        //init Bank
        Bank bank = new Bank("Bank of Bears");

        // add a user, which also creates a savings account
        User user = bank.addUser("Seokyong", "Hong", "1234");

        bank.addCheckingAccount(user);
        bank.addSavingsAccount(user);

        User curUser;
        while(true) {
            // prompt the start menu
            ATM.startMenu(sc);

            // stay in the login prompt until successful login
            curUser = ATM.mainMenuPrompt(bank, sc);

            // stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);
        }
    }


    public static void startMenu(Scanner sc) {
        int choice;
        System.out.print("Please insert your card!\n");
        System.out.println(" 1) Insert Card ");
        System.out.println(" 2) Quit ");
        System.out.print("Enter Choice: ");

        choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Please wait...");
                break;
            case 2:
                System.out.println("Thank you for using Bank of Bears");
                sc.nextLine();
                System.exit(1);
        }

        sc.nextLine();

    }

    /**
     *  Print the ATM's login menu
     * @param bank the Bank object whose accounts to use
     * @param sc the Scanner object to use for user input
     * @return the authenticated user, if valid;
     */
    public static User mainMenuPrompt(Bank bank, Scanner sc) {

        String userId;
        String pin;
        User authUser;

        // prompt the user for userId/pin until a correct one is reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n", bank.getName());
            System.out.print("Please Enter Your ID: ");
            userId = sc.nextLine();
            System.out.printf("Enter pin: ");
            pin = sc.nextLine();

            // try to get the user object corresponding to the userId and pin
            authUser = bank.userLogin(userId, pin);
            if(authUser == null) {
                System.out.println("Incorrect user ID/pin combination. " + "Please try again");
            }
        } while(authUser == null); // continue looping until successful login

        return authUser;
    }

    public static void printUserMenu(User user, Scanner sc) {

        //print a summary of the user's accounts
        user.printAccountsSummary();

        // init
        int choice = 0;
        boolean validChoice = false;

        // user menu
        do {
            System.out.printf("Welcome %s, what would you like to do?\n", user.getFirstName());
            System.out.println(" 1) Show account transaction history ");
            System.out.println(" 2) Withdraw ");
            System.out.println(" 3) Deposit ");
            System.out.println(" 4) Transfer ");
            System.out.println(" 5) Quit ");
            System.out.println();

            try {
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                if (choice < 1 || choice > 5) {
                    System.out.println("Invalid choice, please try again");
                    validChoice = false;
                }
            } catch(InputMismatchException exception) {
                System.out.println("Invalid choice, please try again");
                validChoice = false;
            }

        } while(validChoice);

        //process the choice
        switch (choice) {
            case 1:
                ATM.showTransHistory(user, sc);
                break;
            case 2:
                ATM.withdrawFunds(user, sc);
                break;
            case 3:
                ATM.depositFunds(user, sc);
                break;
            case 4:
                ATM.transferFunds(user, sc);
                break;
            case 5:
                sc.nextLine();
                break;
        }

        //redisplay this menu unless the user wants to quit
        if (choice != 5) {
            ATM.printUserMenu(user, sc);
        }
    }

    /**
     *  Show the transaction history for an account
     * @param user the logged in user object
     * @param sc the Scanner object used for user input
     */
    public static void showTransHistory(User user, Scanner sc) {
        int numAccount;

        // get account whose transaction history to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "whose transactions you want to see: "
                    , user.numAccounts());
            numAccount = sc.nextInt() - 1;
            if(numAccount < 0 || numAccount >= user.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while(numAccount < 0 || numAccount >= user.numAccounts());

        //print transaction history
        user.printAccountTransHistory(numAccount);
    }

    /**
     *  Process transferring funds from one acconut to anoter
     * @param user the logged in User Object
     * @param sc the Scanner object used for user input
     */
    public static void transferFunds(User user, Scanner sc) {
        //inits
        int fromAccount;
        int toAccount;
        int amount;
        int accountBalance;

        // get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the acocunt\n" + "to transfer from ", user.numAccounts());
            fromAccount = sc.nextInt() -1;
            if(fromAccount < 0 || fromAccount >= user.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while(fromAccount < 0 || fromAccount >= user.numAccounts());

        accountBalance = user.getAccountBalance(fromAccount);

        //get the account to transfer to
        do{
            System.out.printf("Enter the number (1-%d) of the acocunt\n" + "to transfer to ", user.numAccounts());
            toAccount = sc.nextInt() -1;
            if(toAccount < 0 || toAccount >= user.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while(toAccount < 0 || toAccount >= user.numAccounts());

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%d) : $", accountBalance);
            amount = sc.nextInt();
            if(amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > accountBalance) {
                System.out.printf("Account must not be greater than\n " + "balance of $%d.\n", accountBalance);
            }
        } while(amount < 0 || amount > accountBalance);

        user.addAccountTransaction(fromAccount, -1*amount,
                String.format("Transfer to account %s", user.getAccountId(toAccount)));
        user.addAccountTransaction(toAccount, amount,
                String.format("Transfer from account %s", user.getAccountId(fromAccount)));
    }

    /**
     * Process a fuind withdrwa from an account
     * @param user the logged in user object
     * @param sc   the Scanner object user for user input
     */
    public static void withdrawFunds(User user, Scanner sc) {
        //inits
        int fromAccount;
        int amount;
        int accountBalance;
        String memo;

        // get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the acocunt\n" + "to withdraw from ", user.numAccounts());
            fromAccount = sc.nextInt() -1;
            if(fromAccount < 0 || fromAccount >= user.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while(fromAccount < 0 || fromAccount >= user.numAccounts());

        accountBalance = user.getAccountBalance(fromAccount);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to withdraw (max $%d) : $", accountBalance);
            amount = sc.nextInt();
            if(amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > accountBalance) {
                System.out.printf("Account must not be greater than\n " + "balance of $%d.\n", accountBalance);
            }
        } while(amount < 0 || amount > accountBalance);

        sc.nextLine();

        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        // do the withdraw
        user.addAccountTransaction(fromAccount, -1*amount, memo);
    }

    /**
     *  Process a fund deposit to an account
     * @param user    the logged-in user obejct
     * @param sc      the Scanner object used for user input
     */
    public static void depositFunds(User user, Scanner sc) {
        //inits
        int toAccount;
        int amount;
        int accountBalance;
        String memo;

        // get the account to transfer from
        do{
            System.out.printf("Choose (1-%d) of the acocunt " + "to deposit in ", user.numAccounts());
            toAccount = sc.nextInt() -1;
            if(toAccount < 0 || toAccount >= user.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while(toAccount < 0 || toAccount >= user.numAccounts());

        accountBalance = user.getAccountBalance(toAccount);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to deposit (max $%d) : $", accountBalance);
            amount = sc.nextInt();
            if(amount < 0) {
                System.out.println("Amount must be greater than zero");
            }
        } while(amount < 0);

        sc.nextLine();

        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        // do the withdraw
        user.addAccountTransaction(toAccount, amount, memo);
    }

}
