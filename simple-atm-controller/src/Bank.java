import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bank {

    private String name;

    private List<User> users;

    private List<Account> accounts;

    /**
     *  Create a new Bank object with empty list of users and accounts
     * @param name the name of the bank
     */
    public Bank(String name) {
         this.name = name;
         this.users = new ArrayList<>();
         this.accounts = new ArrayList<>();
    }

    /**
     *  Generate a new unique Id for a user
     * @return
     */
    public String getNewUserId() {
        //inits
        String userId;
        Random rnd = new Random();
        int length = 5;
        boolean nonUnique;

        // continue looping until we get a unique ID
        do {
            //generate the number
            userId = "";
            for(int i = 0; i < length; i++) {
                userId += ((Integer)rnd.nextInt(10)).toString();
            }

            // check to make sure it's unique
            nonUnique = false;
            for(User u : this.users) {
                if(userId.compareTo(u.getUserId()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return userId;
    }

    /**
     *  Genereate a new unique Id for an account
     * @return
     */
    public String getNewAccountId() {
        //inits
        String userId;
        Random rnd = new Random();
        int length = 5;
        boolean nonUnique;

        // continue looping until we get a unique ID
        do {
            //generate the number
            userId = "";
            for(int i = 0; i < length; i++) {
                userId += ((Integer)rnd.nextInt(10)).toString();
            }

            // check to make sure it's unique
            nonUnique = false;
            for(Account a : this.accounts) {
                if(userId.compareTo(a.getAccountId()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return userId;
    }

    /**
     * Add savings accounts to User and Bank
     * @param user
     */
    public void addSavingsAccount(User user) {
        //create a savings account for the user and add to User and Bank account lists
        Account newAccount = new Account("Savings", user, this);

        // add to holder and bank lists
        user.addAccount(newAccount);
        this.accounts.add(newAccount);
    }

    /**
     * Add checking accounts to User and Bank
     * @param user
     */
    public void addCheckingAccount(User user) {
        //create a checking account for the user and add to User and Bank account lists
        Account newAccount = new Account("Checking", user, this);

        user.addAccount(newAccount);
        this.accounts.add(newAccount);
    }

    /**
     * Create a new user of the Bank
     * @param firstName
     * @param lastName
     * @param pin
     * @return
     */
    public User addUser(String firstName, String lastName, String pin) {
        // create a new User object and add it to our list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        return newUser;
    }

    /**
     * Get the User object associated with a particular userId and pin, if they are valid
     * @param userId
     * @param pin
     * @return
     */
    public User userLogin(String userId, String pin) {
        // search through list of users
        for(User u : this.users) {
            // check userId is correct
            if(u.getUserId().compareTo(userId) == 0 && u.validatePin(pin)) {
                return u;
            }
        }

        // if we haven't found the user or have an incorrect pin
        return null;
    }

    /**
     * Get the name of the bank
     * @return name of the bank
     */
    public String getName() {
        return this.name;
    }

}
