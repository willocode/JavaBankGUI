package ignore.workers;

import java.awt.Component;
import java.awt.Toolkit;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.SwingWorker;

public class WithdrawWorker extends SwingWorker<Void, Void>{

	String value;
	String accountID;
	
	public WithdrawWorker(String value, String accountID) {
		this.value = value;
		this.accountID = accountID;
	}
	
	@Override
    protected Void doInBackground() throws Exception {
		System.out.println("***************************************");
		
		Double val = null;
		
		try {
			val = Double.parseDouble(value);
		}catch (NumberFormatException e) {
			System.out.println("Please enter only numeric values");
			return null;
		}
		
		System.out.println("attempting to withdraw: " + val);
		Thread.sleep(500);
		
        try {
            //invoke the deposit method
            runWithdrawMethod("withdraw", val);
            
        }catch(Exception e) {
        	String cause = e.getCause().initCause(null).toString();
        	
        	if (cause.contains("OverdraftException")) {
        		System.out.println(cause);
        	}else {
        		e.printStackTrace();
        	}
        }
        
        
       
        System.out.println("***************************************");
        this.setProgress(100);
        return null;
    }
	
	private void runWithdrawMethod(String method, double cash) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		
		try {
			//Get Ledger definition
			Class ledger = Class.forName("yourworkhere.Ledger");
			
			//Get current instance of the ledger
			Method m = ledger.getMethod("getInstance");
			m.setAccessible(true);
			Object ledgerInstance = m.invoke(null);
			
			//get the account based on the passed in value
			Method retrieveMethod = ledger.getMethod("retrieve", String.class);
			retrieveMethod.setAccessible(true);
			Object Account = retrieveMethod.invoke(ledgerInstance, this.accountID);
			
			//TODO remove below
			Method getAccountID = Account.getClass().getMethod("getAccountID");
			getAccountID.setAccessible(true);
			Object id = getAccountID.invoke(Account);
			System.out.println("Account id: " + id);
			//TODO remove above
			
			//TODO remove below
			Method getBalance = Account.getClass().getMethod("getBalance");
			getBalance.setAccessible(true);
			Object balance = getBalance.invoke(Account);
			System.out.println("Starting balance: " + balance);
			//TODO remove above
			
			//get the AccountManager by invoking getAccountManager
			Method getAccountManager = Account.getClass().getMethod("getAccountManager");
			getAccountManager.setAccessible(true);
			Object accountManager = getAccountManager.invoke(Account);
			
			//invoke the withdraw method
			Class[] param = new Class[1];	
			param[0] = Double.TYPE;
			Method withdrawMethod = accountManager.getClass().getMethod("withdraw", param);
			withdrawMethod.setAccessible(true);
			Object result = withdrawMethod.invoke(accountManager, cash);

			result = (Boolean)result;
			System.out.println("The returned value is: " + result);
			
			balance = getBalance.invoke(Account);
			System.out.println("Ending balance: " + balance);
			
		}catch (ClassNotFoundException e) {
			//System.out.println("The SavingsAccountManager does not exist. Did you create it yet?");
        	if (e.getMessage().contains("OverdraftException")) {
        		System.out.println("The OverdraftException class is not yet created. Please create it");
        	}else if (e.getMessage().contains("Ledger")) {
        		System.out.println("The Ledger class is not yet created. Please create it");
        	}else if (e.getMessage().contains("SavingsAccountManager") || e.getMessage().contains("CheckingAccountManager")) {
        		System.out.println("The SavingsAccountManager or CheckingAccountManager class is not yet created. Please create it");
        	}else if (e.getMessage().contains("SavingsAccount")  || e.getMessage().contains("CheckingAccount")) {
        		System.out.println("The SavingsAccount or CheckingAccount class is not yet created. Please create it");
        	}else {
            	e.printStackTrace();
        	}
		}catch (NoSuchMethodException e) {
			System.out.println("The method does not yet exist on Ledger.");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
    }
}