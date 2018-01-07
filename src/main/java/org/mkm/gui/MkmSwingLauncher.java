package org.mkm.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.api.mkm.exceptions.MkmException;
import org.api.mkm.tools.MkmAPIConfig;

public class MkmSwingLauncher {

	public static void main(String[] args) throws Exception {
		Properties p = new Properties();
		p.load(new FileInputStream(new File("C:\\Users\\XXXX\\.magicDeskCompanion\\pricers\\MagicCardMarket.conf")));
		
		MkmAPIConfig.getInstance().init(p.getProperty("APP_ACCESS_TOKEN_SECRET").toString(),
										p.getProperty("APP_ACCESS_TOKEN").toString(),
										p.getProperty("APP_SECRET").toString(),
										p.getProperty("APP_TOKEN").toString());
		MkmPanel f = new MkmPanel();
		f.setVisible(true);
	}
	
}
