package tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class closeWindowListener implements ActionListener{

	private JFrame _frame ;
	
	public closeWindowListener(JFrame frame){
		_frame = frame;
	}
	
    @Override
	public void actionPerformed(ActionEvent e) {
		_frame.dispose();
	}

}
