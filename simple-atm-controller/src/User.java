import sun.plugin2.message.Message;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;

public class User {

    /*
        The first name of the user
     */
    private String firstName;

    /*
        The latName name of the user
     */
    private String lastName;

    /*
        The ID of the user
     */
    private String userId;

    /*
        The hash of the user's pin number
     */
    private byte pinHash[];

    /*
        The lis of accounts for this user
     */
    private List<Account> accounts;

    /**
     * Create a new user
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param pin the user's account pin number
     * @param bank the Bank object that the user is a customer of
     */
    public User(String firstName, String lastName, String pin, Bank bank) {

        // set user's name
        this.firstName = firstName;
        this.lastName = lastName;

        // store the pin's hash for security reason
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException, please type a valid hashing algorithm");
            e.printStackTrace();
            System.exit(1);
        }

        // get a new unique id for the user
        this.userId = bank.getNewUserId();

        // create empty list of accounts
        this.accounts = new ArrayList<>();

        // print log message
        System.out.printf("New user %s, %s with ID %s created. \n", lastName, firstName, this.userId);
    }

    /**
     * Add an account for the user
     * @param account the account to add
     */
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    /**
     * Return the user's Id
     * @return
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * Check whether a given pin matches the true User pin
     * @param pin the pin to check
     * @return whether the pin is valid or not
     */
    public boolean validatePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException, please type a valid hashing algorithm");
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }

    /**
     *  Return the user's first name
     * @return the first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Print summaries for the accounts of this user.
     */
    public void printAccountsSummary() {
        System.out.printf("\n\n%s's accounts summary", this.firstName);
        for(int i = 0; i < this.accounts.size(); i++) {
            System.out.printf("\n%d) %s\n", i+1, this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }

    /**
     *  Get the number of accounts of the user
     * @return the number of accounts
     */
    public int numAccounts() {
        return this.accounts.size();
    }

    /**
     * Print transaction history for a particular account.
     * @param numAccount the num of the account to use
     */
    public void printAccountTransHistory(int numAccount) {
        this.accounts.get(numAccount).printTransHistory();
    }

    /**
     * Get the balance of a particular account
     * @param numAccount the num of the account to use
     * @return the balance of the account
     */
    public int getAccountBalance(int numAccount) {
        return this.accounts.get(numAccount).getBalance();
    }

    /**
     *  Get the ID of a particular account
     * @param numAccount the num of account to use
     * @return the id of the account
     */
    public String getAccountId(int numAccount) {
        return this.accounts.get(numAccount).getAccountId();
    }

    /**
     * Add a transaction to a particular account
     * @param numAccount the index of the acocunt
     * @param amount the amount of the transaction
     * @param memo the memo of the transaction
     */
    public void addAccountTransaction(int numAccount, int amount, String memo) {
        this.accounts.get(numAccount).addTransaction(amount, memo);
    }
}
