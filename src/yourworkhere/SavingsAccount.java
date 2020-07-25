package yourworkhere;

public class SavingsAccount extends Account {
	protected double minBalance;
	protected int currentMonthlyWithdrawals;
	protected int maxMonthlyWithdrawals;
	
	//constructors
	public SavingsAccount(){}
	
	/* inherited variables from our abstract superclass "Account", combined with
	the subclass variables */
	public SavingsAccount(String accountID, String firstName, String lastName){
		super();
		this.accountID = accountID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountType = "savings";
		this.currentMonthlyWithdrawals = 0;
	}
	

	//setters/getters
	public double getMinBalance(){
	    return this.minBalance;
	}
	
	/* protecting method so malicious outsiders can't prevent someone from 
	 *withdrawing funds by manually setting a high minBalance */
	protected void setMinBalance(double minBalance){
	    this.minBalance = minBalance;
	}
	
	/* retrieves current monthly withdrawals */
	public int getCurrentMonthlyWithdrawals(){
	    return this.currentMonthlyWithdrawals;
	}
	
	/* protecting method so malicious outsiders can't prevent someone from 
	 * withdrawing their own funds by manually setting a current monthly withdrawal
	 */
	protected void setCurrentMonthlyWithdrawals(int currentMonthlyWithdrawals){
	    this.currentMonthlyWithdrawals = currentMonthlyWithdrawals;
	}
	
	/* retrieves maximum monthly withdrawals set*/
	public int getMaxMonthlyWithdrawals(){
	    return this.maxMonthlyWithdrawals;
	}
	
	/* protecting method so malicious outsiders can't prevent someone from
	 *  withdrawing their own funds by manually setting a low max monthly withdrawal
	 */
	public void setMaxMonthlyWithdrawals(int maxMonthlyWithdrawals){
	    this.maxMonthlyWithdrawals = maxMonthlyWithdrawals;
	}
	
	/* manages all of the instantiated savings accounts */
	public IAccountManager getAccountManager() {
	    return new SavingsAccountManager(this);
	}
	
	@Override
    public String toString() { 
        return String.format(
        		"Account ID: " + this.accountID + " ", 
        		"Name: " + this.firstName + " " + this.lastName + " ",
        		"Account type: " + this.accountType + " ",
        		"Current balance: " + this.balance);
        
    }

	@Override
	public String getAccountID() {
		return this.accountID;
	} 
}


