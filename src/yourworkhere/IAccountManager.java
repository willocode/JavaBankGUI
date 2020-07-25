package yourworkhere;

/* our account manager handling deposits and withdrawals into 
 * Checking accounts and Savings accounts */
public interface IAccountManager {
	public boolean withdraw(double amount);
	public boolean deposit(double amount);

}
