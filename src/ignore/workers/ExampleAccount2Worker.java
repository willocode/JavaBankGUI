package ignore.workers;

import java.awt.Toolkit;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.SwingWorker;

public class ExampleAccount2Worker extends SwingWorker<Void, Void> {

	@Override
	protected Void doInBackground() throws Exception {
		System.out.println("***************************************");
		System.out.println("Running ExampleAccount2...");
		System.out.print(".");
		Thread.sleep(500);
		System.out.print(".");
		Thread.sleep(500);
		
		try {
			Class c = Class.forName("yourworkhere.ExampleAccount2");
			System.out.print(".");
			Thread.sleep(500);
			System.out.print(".");
			Thread.sleep(500);
			System.out.println("Checking that you've created the correct fields in your class..");
			
			runMainMethod(c);
			//checkFields(c);
			
		}catch(ClassNotFoundException e) {
			System.out.println("The class doesn't exist yet. Please complete step 2 to finish this exercise.");
		}
		
		
		
        System.out.println("***************************************");
		this.setProgress(100);
		return null;
	}
	
	private void runMainMethod(Class c) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method m = c.getDeclaredMethod("main", String[].class);
		m.setAccessible(true);
		String[] args = null;
		m.invoke(null, (Object)args);
	}
	
	private void checkFields(Class c) throws InterruptedException {

		Field[] fields = c.getDeclaredFields();
		
		if (fields.length == 0) {
			System.out.println("No fields present! Please add the required variables to the class");
		}
		
		int count = 0;
		
        for(Field f : fields){
            Class t = f.getType();
            System.out.println("field name : " + f.getName() + " , type : " + t);
            
            switch (f.getName()) {
            	case "maxMonthlyWithdrawals" :
	            	if (t.getTypeName().equals("int")) {
	            		System.out.println("Correct! int is the recommended type for maxMonthlyWithdrawals!");
	            	}else {
	            		System.out.println("Incorrect! there is a better type that you can use.");
	            	}
	            	count++;
	            	break;
	            	
            	case "currentMonthlyWithdrawals" : 
            		if (t.getTypeName().equals("int")) {
            			System.out.println("Correct!");
            		}else {
	            		System.out.println("Incorrect! there is a better type that you can use.");
	            	}
            		count++;
	            	break;
	            	
            	case "minBalance" : 
            		if (t.getTypeName().equals("double")) {
            			System.out.println("Correct!");
            		}else {
	            		System.out.println("Incorrect! there is a better type that you can use.");
	            	}
            		count++;
	            	break;
            
            	default: 
            		System.out.println("You have either created an additional field or have mispelled your field name");
            		System.out.println("You wrote: " + f.getName());
            		System.out.println("We were expecting the following:");
            		System.out.println("maxMonthlyWithdrawals");
            		System.out.println("currentMonthlyWithdrawals");
            		System.out.println("minBalance");
            		break;
            }
            
            Thread.sleep(600);
        }
        
        if (count < 3) {
        	System.out.println("You are missing one or more fields. Did you declare them all?");
        	System.out.println("We were expecting the following:");
    		System.out.println("maxMonthlyWithdrawals");
    		System.out.println("currentMonthlyWithdrawals");
    		System.out.println("minBalance");
        }
	}

	@Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
    }
}

