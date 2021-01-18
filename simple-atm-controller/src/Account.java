import java.util.ArrayList;
import java.util.List;

public class Account {

    /*
        The name of the account
     */
    private String accountName;

    /*
        The account ID number
     */
    private String accountId;

    /*
        The User object that owns this account
     */
    private User holder;

    /*
        The list of transactions for this account
     */
    private List<Transaction> transcations;

    /**
     * Create a new Account
     * @param accountName the name of the account
     * @param holder the holder of the account
     * @param bank the bank that issues the account
     */
    public Account(String accountName, User holder, Bank bank) {
        // set the account name and holder
        this.accountName = accountName;
        this.holder = holder;

        // get new accountID
        this.accountId = bank.getNewAccountId();

        // init transactions
        this.transcations = new ArrayList<>();
    }

    /**
     * Get the acocunt ID
     * @return the account ID
     */
    public String getAccountId() {
        return this.accountId;
    }

    /**
     * Get summary line for the account
     * @return the summary
     */
    public String getSummaryLine() {

        // get the accounts' balance
        int balance = this.getBalance();

        // format the summary line, depending on the whether the balance is negative
        if(balance >= 0) {
            return String.format("%s : $%d : %s", this.accountId, balance, this.accountName);
        } else {
            return String.format("%s : $(%d) : %s", this.accountId, balance, this.accountName);
        }
    }

    /**
     *  Get the balance of this account by adding the amounts of the transactions
     * @return the balance
     */
    public int getBalance() {
        int balance = 0;

        for(Transaction t: this.transcations) {
            balance += t.getAmount();
        }

        return balance;
    }

    /**
     * Print the transaction history
     */
    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s\n", this.accountId);
        for(int i = this.transcations.size() -1 ; i >= 0; i--) {
            System.out.println(this.transcations.get(i).getSummaryLine());
        }
        System.out.println();
    }

    /**
     *  Add a new transaction in this account
     * @param amount the amount transacted
     * @param memo the transaction memo
     */
    public void addTransaction(int amount, String memo) {
        //create new transaction object and add it to our list
        Transaction trans = new Transaction(amount, memo, this);
        this.transcations.add(trans);
    }
}
