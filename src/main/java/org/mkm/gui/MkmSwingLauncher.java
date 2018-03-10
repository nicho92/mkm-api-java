package org.mkm.gui;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JFrame;

import org.api.mkm.tools.MkmAPIConfig;

public class MkmSwingLauncher {

	public static void main(String[] args) {
		Properties p = new Properties();
		try {
			File configFile = new File("C:\\Users\\Nicolas\\.magicDeskCompanion\\pricers\\MagicCardMarket.conf");
			p.load(new FileInputStream(configFile));
			MkmAPIConfig.getInstance().init(p.getProperty("APP_ACCESS_TOKEN_SECRET"),
					p.getProperty("APP_ACCESS_TOKEN"),
					p.getProperty("APP_SECRET"),
					p.getProperty("APP_TOKEN"));

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		JFrame f = new JFrame();
		f.setSize(750, 550);
		f.add(new MkmPanel());
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("MKM API - Samples");
		f.setVisible(true);
		
	}
	
}
