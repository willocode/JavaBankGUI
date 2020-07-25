package yourworkhere;

import yourworkhere.SavingsAccount;


public class SavingsAccountManager implements IAccountManager {
	private SavingsAccount account;
	

	//constructor
	public SavingsAccountManager(SavingsAccount account) {
	    this.account = account;
	}
	
	//methods
	
	/* deposit amount cannot be a negative number */
	public boolean deposit(double amount) {
	    if (amount < 0) {
		System.out.println("you cannot deposit a negative amount");
		return false;


	    } else 
	    account.setBalance(account.getBalance() + amount);
	    return true;
	}
	
	/* setting the minimum balance for our account.  also, only allowing 6 withdrawals
	 * per month, based on US Federal Regulation D (withdrawal limits)
	 */
	public boolean withdraw(double amount) {
		account.setMinBalance(30.00);
		account.setMaxMonthlyWithdrawals(6);
		
		if(account.getCurrentMonthlyWithdrawals() >= account.getMaxMonthlyWithdrawals()) {
		 System.out.println("you're over your allowed monthly withdrawals: US Regulation D (withdrawal limits)");
		 return false;

			
		} if (account.getBalance() - amount < account.getMinBalance()){
				System.out.println("account must maintain a minimum balance of: " + account.minBalance);
				return false;

			} else
			account.setBalance(account.getBalance() - amount);
			++account.currentMonthlyWithdrawals;
			return true;
	}
	
}





