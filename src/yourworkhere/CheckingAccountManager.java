package yourworkhere;


public class CheckingAccountManager implements IAccountManager {

	private CheckingAccount account;

	//constructor
	public CheckingAccountManager(CheckingAccount account) {
	    this.account = account;
	}

	
	//methods
	
	/* handles deposits into Checking Account.  Must be a positive 
	   deposit amount.  */
	
	public boolean deposit(double amount) {
	    if (amount < 0) {
			System.out.println("you cannot deposit a negative amount");
	        return false;

	    } else {
	    	 account.setBalance(account.getBalance() + amount);
	    	 return true;
	      }
	}
	
	/* handles withdrawals from Checking Account
	   if the balance falls below 0, then an exception is thrown */
	
	public boolean withdraw(double amount) {
	    if (account.getBalance() - amount < 0){
	        throw new OverdraftException();
	        
	    } if (account.getBalance() - amount < 0){
	        account.setBalance(account.getBalance() - amount - account.getOverdraftFee());
	        return true;
	
	    } else {
	        account.setBalance(account.balance - amount);
	        System.out.println("withdrawing " + amount);
	        return true;
	    }
	}
}
