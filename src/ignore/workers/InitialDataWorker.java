package ignore.workers;

import java.awt.Component;
import java.awt.Toolkit;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class InitialDataWorker extends SwingWorker<Void, Void> {
	JComponent component;
	
	public InitialDataWorker() {}
	public InitialDataWorker(JComponent component){
		this.component = component;
	}
	

	@Override
	protected Void doInBackground() throws Exception {
		
		// Checks if Account exists
		System.out.println("Testing if Account class exists...");
		try {
			Class c = Class.forName("yourworkhere.SavingsAccount");

			checkFields(c, (JTextField)component);
			
		} catch (ClassNotFoundException e) {
			System.out.println(
					"Class does not exist! Did you create the interface Reporter? Is it in the yourworkhere package?");
		}
		
		this.setProgress(100);
		return null;
	}
	
	private void checkFields(Class c, JTextField component) throws InterruptedException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException {

		try {
			Field field = c.getSuperclass().getDeclaredField("balance");
			field.setAccessible(true);
			double val = field.getDouble(c.getConstructor().newInstance());
			
			System.out.println("balance: " + val);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void done() {
		Component[] components;
		Toolkit.getDefaultToolkit().beep();
	}
}
