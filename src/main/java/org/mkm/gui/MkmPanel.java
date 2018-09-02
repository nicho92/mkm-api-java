package org.mkm.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class MkmPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MkmPanel()
	{
		MkmSearchPanel pane = new MkmSearchPanel();
		MkmMetaSearchPanel pane4 = new MkmMetaSearchPanel();
		MkmWantListPanel pane2 = new MkmWantListPanel();
		MkmOrderPanel pane3 = new MkmOrderPanel();
		MKMStockPanel pane5 = new MKMStockPanel();
		setLayout(new BorderLayout());
		
		
		JTabbedPane tpane = new JTabbedPane();
		
		tpane.add("Search", pane);
		tpane.add("Meta", pane4);
		tpane.add("WantList", pane2);
		tpane.add("Order", pane3);
		tpane.add("Stock", pane5);
		
		
		
		
		add(tpane,BorderLayout.CENTER);
		
		
		
	}
	
}
