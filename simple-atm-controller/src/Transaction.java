import java.util.Date;

public class Transaction {

    /*
        The amount of this transaction
     */
    private int amount;

    /*
        The time and date of this transaction
     */
    private Date timestamp;

    /*
        A memo for this transaction
     */
    private String memo;

    /*
        The account in which the transaction was performed
     */
    private Account inAccount;

    /**
     * Create a new transaction
     * @param amount the amount transacted
     * @param inAccount the account the transaction belongs to
     */
    public Transaction(int amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    /**
     *  Create a new transaction with memo
     * @param amount the amounot transacted
     * @param memo the memo for the transaction
     * @param inAccount the account the transaction belongs to
     */
    public Transaction(int amount, String memo, Account inAccount) {
        this(amount, inAccount);

        this.timestamp = new Date();
        this.memo = memo;
    }

    /**
     * Get the amount of the transaction
     * @return the amount
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Get a string summary of the transaction
     * @return the summary
     */
    public String getSummaryLine() {
        if(this.amount >= 0) {
            return String.format("%s : $%d : %s", this.timestamp.toString(), this.amount, this.memo);
        } else {
            return String.format("%s : $(%d) : %s", this.timestamp.toString(), this.amount, this.memo);
        }
    }
}
