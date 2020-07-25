package yourworkhere;

import java.util.*; //our collections import, so we can use List<Account>

public class Reporter {

	private Ledger ledger;
	
	/* constructor for using ledger in reporter class */
	public Reporter(Ledger ledger) {
		super();
		this.ledger = ledger;
		
	}

	// gets/sets
	public Ledger getLedger() {
		return ledger;
	}

	public void setLedger(Ledger ledger) {
		this.ledger = ledger;
	}
	
	// methods 
	
	/* get the total number of accounts handled by the ledger */
	public int getNumAccounts() {
		return ledger.getAllAccounts().size();
	   
	}
	
	/* get accounts based on an amount, and outputs them into a new array list */
	public List<Account> getAccountsWithMinimum(double amount) {
	    List<Account> accounts = ledger.getAllAccounts();
	    List<Account> output = new ArrayList<>(); 
			
	    for (int x = 0; x < accounts.size(); x++) {
	        if (accounts.get(x).getBalance() >= amount) {
	            output.add(accounts.get(x));
	        }		
	    }
		return output;
	}
	
	/* prints all accounts from the ledger */
	public void printAllAccounts() {
		List<Account> all = ledger.getAllAccounts();
		System.out.println(all);
	}
	
	/* return accounts based on their type.  checks if getAccount is equal to 
	 * the account types in the ledger.  Then returns the matches */
	public int getNumAccountsByType(String accountType) {
	    List<Account> i = ledger.getAllAccounts();
	    int num = 0;
	    
	    for (int x = 0; x < i.size(); x++) {
	        if (i.get(x).getAccountType().equals(accountType)) { 
	            num++;
	         }
	    }
	    return num;
	}
	
}

