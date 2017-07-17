package org.mkm.gui;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.api.mkm.modele.User;
import org.api.mkm.services.ProductServices;
import org.api.mkm.services.UserService;
import org.api.mkm.tools.MkmAPIConfig;

public class MkmFrame extends JFrame{

	public MkmFrame() throws Exception
	{
		
		Properties pricer = new Properties();
		pricer.load(new FileInputStream(new File("C:\\Users\\Pihen\\magicDeskCompanion\\pricers\\Magic Card Market 2.conf")));
		
		MkmAPIConfig.getInstance().init(pricer.getProperty("APP_ACCESS_TOKEN_SECRET").toString(),
										pricer.getProperty("APP_ACCESS_TOKEN").toString(),
										pricer.getProperty("APP_SECRET").toString(),
										pricer.getProperty("APP_TOKEN").toString());
		
		MkmSearchPanel pane = new MkmSearchPanel();
		MkmMetaSearchPanel pane4 = new MkmMetaSearchPanel();
		MkmWantListPanel pane2 = new MkmWantListPanel();
		MkmOrderPanel pane3 = new MkmOrderPanel();
		
		JTabbedPane tpane = new JTabbedPane();
		
		tpane.add("Search", pane);
		tpane.add("Meta", pane4);
		tpane.add("WantList", pane2);
		tpane.add("Order", pane3);

		getContentPane().add(tpane);
		setSize(750, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		
		
	}
	
	public static void main(String[] args) throws Exception {
		new MkmFrame();
	}

}
