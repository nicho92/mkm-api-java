package org.mkm.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MkmFrame extends JFrame{

	public MkmFrame()
	{
		MkmSearchPanel pane = new MkmSearchPanel();
		MkmWantListPanel pane2 = new MkmWantListPanel();
		
		JTabbedPane tpane = new JTabbedPane();
		
		tpane.add("Search", pane);
		tpane.add("WantList", pane2);
		getContentPane().add(tpane);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new MkmFrame();
	}

}
