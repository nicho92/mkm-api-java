package org.mkm.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MkmFrame extends JFrame{

	public MkmFrame()
	{
		MkmSearchPanel pane = new MkmSearchPanel();
		MkmWantListPanel pane2 = new MkmWantListPanel();
		MkmOrderPanel pane3 = new MkmOrderPanel();
		
		JTabbedPane tpane = new JTabbedPane();
		
		tpane.add("Search", pane);
		tpane.add("WantList", pane2);
		tpane.add("Cart", pane3);
		
		getContentPane().add(tpane);
		setSize(750, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new MkmFrame();
	}

}
