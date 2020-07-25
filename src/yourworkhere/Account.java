package yourworkhere;

//abstract to allow method implementation and override to all of the account subclasses
//cannot be instanciated as a new object
public abstract class Account {
	
	// protecting variables from being changed
	protected String accountID;
	protected double balance;
	public String accountType; 
	protected String firstName;
	protected String lastName;
	
	public abstract String getAccountID();
	//{ return this.accountID;}
	
	public double getBalance(){
		return this.balance;
		}
	
	public String getAccountType()
	{return this.accountType;}

	public String getFirstName()
	{return this.firstName;}
	
	public String getLastName()
    {return this.lastName;}
	
	//protecting method from malicious outsiders changing the account ID
	protected void setAccountID(String accountID)
	{this.accountID = accountID;}
	
	//protecting method from malicious outsiders freely setting new balance values
	protected void setBalance(Double balance)
	{this.balance = balance;}
	
	public void setAccountType(String accountType)
	{this.accountType = accountType;}
	
	//protecting name methods from malicious outsiders freely setting new name values
	protected void setFirstName(String firstName)
	{this.firstName = firstName;}
	
	protected void setLastName(String lastName)
	{this.lastName = lastName;}
	
	public abstract IAccountManager getAccountManager();


}


