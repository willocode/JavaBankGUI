package yourworkhere;

import java.util.*;


public class Ledger {

	HashMap<String, Account> hm = new HashMap<String, Account>();

	// the single instance of Ledger, so we don't have duplicate Ledgers
	private static final Ledger instance = new Ledger();

	//private constructor
	private Ledger() {}



	// method to return the single instance
	public static Ledger getInstance(){
	    return instance;
	}

	/* our method to store an account after creation.  if a duplicate ID exists
	 * an error is thrown */
	public boolean store(Account accounts){

        if (hm.containsKey(accounts.getAccountID())) {
            System.out.println("account already exists");
            return false;
        } else {
            hm.put(accounts.getAccountID(), accounts);
            return true;
        }
    }

	/* retrieves account ID */
	public Account retrieve(String accountID){
	    return hm.get(accountID);
	}

	/* creates new account. Checks which account type,
	 *  and constructs a new account based on constructor for that class */
	public Account createAccount(String accountType, String firstName, String lastName) {
		Account accounts = null;

		if ("savings".equals(accountType)) {
			accounts = new SavingsAccount(getNextAccountID(), firstName, lastName);
			return accounts;
		}
		if("checking".equals(accountType)) {
		    accounts = new CheckingAccount(getNextAccountID(), firstName, lastName);
		}
		return accounts;

	}


	/* generates/increments the next numerical ID in the database */
	public String getNextAccountID(){
		Integer next = hm.size() + 1;
			    return next.toString();

	}

	/* returns all accounts in the hashmap */
	public List<Account> getAllAccounts() {
	    List<Account> list = new ArrayList<Account>(hm.values());
	    return list;
	}

}

