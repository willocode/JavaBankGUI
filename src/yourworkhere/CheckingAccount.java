package yourworkhere;

public class CheckingAccount extends Account {
	protected double overdraftFee = 10;
	
	//constructors
	public CheckingAccount(){}
	/*
	 * our variables used when instantiating a new checking account
	 * */
	public CheckingAccount(String accountID, String firstName, String lastName){
		super();
		this.accountID = accountID;
		this.firstName = "jane";
		this.lastName = "doe";
		this.accountType = "checking";

	}
	
	//setters/getters
	public double getOverdraftFee(){
	    return this.overdraftFee;
	}
	//protecting method from malicious outsiders freely setting new fee values
	protected void setOverdraftFee(double overdraftFee){
	    this.overdraftFee = overdraftFee;
	}
	
	// inherited method for managing our checking accounts
	public IAccountManager getAccountManager() {
	    return new CheckingAccountManager(this);
	}
	
	/* context for what we want created with a new Checking Account */
	@Override
    public String toString() { 
        return String.format(
        		"Account ID: " + this.accountID + " ", 
        		"Name: " + this.firstName + " " + this.lastName + " ",
        		"Account type: " + this.accountType + " ",
        		"Current balance: " + this.balance + " ",
        		"Overdraft fee: " + this.overdraftFee);
        
    }
	
	//inherited abstract method for creating new Checking Account
	@Override
	public String getAccountID() {
		return this.accountID;
	} 

}



