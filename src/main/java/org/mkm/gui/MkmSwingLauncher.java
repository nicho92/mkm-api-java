package org.mkm.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.api.mkm.services.DevelopperServices;
import org.api.mkm.tools.MkmAPIConfig;

public class MkmSwingLauncher {

	public static void main(String[] args) throws IOException {
		
		if(new File("C:\\Users\\Nicolas\\.magicDeskCompanion\\pricers\\MagicCardMarket.conf").exists())
			MkmAPIConfig.getInstance().init(new File("C:\\Users\\Nicolas\\.magicDeskCompanion\\pricers\\MagicCardMarket.conf"));
		
		new MkmSwingLauncher();
	}

	public MkmSwingLauncher() {
		JFrame f = new JFrame();
		f.setSize(750, 550);
		f.add(new MkmPanel());
		f.pack();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setTitle("MKM API - Samples");
		f.setVisible(true);
		
	}
	
}
