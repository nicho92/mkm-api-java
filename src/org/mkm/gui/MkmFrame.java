package org.mkm.gui;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.apache.commons.beanutils.BeanUtils;
import org.api.mkm.modele.WantItem;

public class MkmFrame extends JFrame{

	public MkmFrame() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		MkmSearchPanel pane = new MkmSearchPanel();
		MkmWantListPanel pane2 = new MkmWantListPanel();
		MkmOrderPanel pane3 = new MkmOrderPanel();
		
		JTabbedPane tpane = new JTabbedPane();
		
		tpane.add("Search", pane);
		tpane.add("WantList", pane2);
		tpane.add("Order", pane3);
		
		
		
		getContentPane().add(tpane);
		setSize(750, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		
		
	}
	
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		new MkmFrame();
	}

}
