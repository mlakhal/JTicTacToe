package tictactoe;

import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class ToolBar extends JPanel {

	public JMenuBar menuBar;
	public JMenu file;
	public JMenuItem fermer;
	private JFrame _frame;
	
	
	public ToolBar( JFrame frame) {
		_frame = frame;
		menuBar = new JMenuBar();
		// Add a JMenu
		file = new JMenu("Fichier");
		fermer = new JMenuItem("Fermer");
		fermer.addActionListener(new closeWindowListener(_frame));
		fermer.setAccelerator(KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_F4, 
		        java.awt.Event.ALT_MASK));
		fermer.setPreferredSize(new Dimension(100, 30));
		file.add(fermer);
		menuBar.add(file);
	}
}