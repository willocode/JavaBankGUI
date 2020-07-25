package ignore.gui;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class JOutput extends OutputStream{
	
	private JTextArea textControl;
	
	public JOutput( JTextArea control ) {
        textControl = control;
    }
	

	@Override
	public void write(int arg0) throws IOException {
		
		textControl.append( String.valueOf( ( char )arg0 ));
	}	
}
