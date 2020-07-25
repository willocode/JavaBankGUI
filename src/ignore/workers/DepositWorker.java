package ignore.workers;

import java.awt.Component;
import java.awt.Toolkit;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.SwingWorker;

public class DepositWorker extends SwingWorker<Void, Void>{

	String value;
	String accountID;
	
	public DepositWorker(String value, String accountID) {
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

		System.out.println("attempting to deposit: " + val);
		Thread.sleep(500);
        
        //get the field amount from SAM
        try {
            runDepositMethod("deposit", val);
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
        
        
       
        System.out.println("***************************************");
        this.setProgress(100);
        return null;
    }
	
	private void runDepositMethod(String method, double amount) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
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
			
			//invoke the deposit method
			Class[] param = new Class[1];	
			param[0] = Double.TYPE;
			Method withdrawMethod = accountManager.getClass().getMethod("deposit", param);
			withdrawMethod.setAccessible(true);
			Object result = withdrawMethod.invoke(accountManager, amount);

			result = (Boolean)result;
			System.out.println("The returned value is: " + result);
			
			balance = getBalance.invoke(Account);
			System.out.println("Ending balance: " + balance);
			
		}catch (ClassNotFoundException e) {
			if (e.getMessage().contains("OverdraftException")) {
        		System.out.println("The OverdraftException class is not yet created. Please create it");
        	}else if (e.getMessage().contains("SavingsAccountManager") || e.getMessage().contains("CheckingAccountManager")) {
        		System.out.println("The SavingsAccountManager or CheckingAccountManager class is not yet created. Please create it");
        	}else if (e.getMessage().contains("SavingsAccount")  || e.getMessage().contains("CheckingAccount")) {
        		System.out.println("The SavingsAccount or CheckingAccount class is not yet created. Please create it");
        	}else if (e.getMessage().contains("Ledger")) {
        		System.out.println("The Ledger class is not yet created. Please create it");
        	}else {
            	e.printStackTrace();
        	}
		}catch (NoSuchMethodException e) {
			System.out.println("The method does not yet exist.");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void runDepositMethodArchived(Class c, Class savingsClass, String method) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Class[] param = new Class[1];	
		param[0] = Double.TYPE;
		Method m = c.getDeclaredMethod(method, param);
		m.setAccessible(true);
		
		System.out.println(m.getReturnType());
		double cash = 1000.00;
		Constructor constructor = c.getConstructor(savingsClass);
		constructor.setAccessible(true);
		
		Object result = m.invoke(constructor.newInstance(savingsClass.getConstructor(String.class, String.class, String.class).newInstance("1", null, null)), cash);
		result = (Boolean)result;
		System.out.println("The value returned is: " + result);
	}
	
	@Override
    public void done() {
        Component[] components;
        Toolkit.getDefaultToolkit().beep();
    }
}
