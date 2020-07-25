package ignore.workers;

import java.awt.Toolkit;
import java.lang.reflect.Method;

import javax.swing.SwingWorker;



public class Messenger extends SwingWorker<Void, Void> {
	
    public Messenger() {}
    

	@Override
    protected Void doInBackground() throws Exception {
		System.out.println("***************************************");
		
		try {
			Class c = Class.forName("yourworkhere.Hello");
			Method m = c.getDeclaredMethod("main", String[].class);
			m.setAccessible(true);
			String[] args = null;
			m.invoke(null, (Object)args);
		}catch (ClassNotFoundException e) {
			System.out.println("Welcome");
			System.out.println("Once you complete the first task in this project your custom message will display here");
		}
        
        
        System.out.println("***************************************");
        this.setProgress(100);
        return null;
    }

    @Override
    public void done() {
        //Toolkit.getDefaultToolkit().beep();
    }

}