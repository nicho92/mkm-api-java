package org.mkm.gui;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.api.mkm.tools.MkmAPIConfig;

public class MkmSwingLauncher {

	public static void main(String[] args) throws IOException {
		
		if(new File(args[0]).exists())
			MkmAPIConfig.getInstance().init(new File(args[0]));
		
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
