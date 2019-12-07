package org.mkm.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.api.mkm.tools.MkmAPIConfig;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;


public class MkmPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblConnectedas;
	
	
	public MkmPanel()
	{
		MkmSearchPanel pane = new MkmSearchPanel();
		MkmMetaSearchPanel pane4 = new MkmMetaSearchPanel();
		MkmWantListPanel pane2 = new MkmWantListPanel();
		MkmOrderPanel pane3 = new MkmOrderPanel();
		MKMStockPanel pane5 = new MKMStockPanel();
		MkmDevPanel paneDev = new MkmDevPanel();
		setLayout(new BorderLayout());
		
		
		JTabbedPane tpane = new JTabbedPane();
		
		tpane.add("Search", pane);
		tpane.add("Meta", pane4);
		tpane.add("WantList", pane2);
		tpane.add("Order", pane3);
		tpane.add("Stock", pane5);
		tpane.add("Dev",paneDev);
		
		
		
		add(tpane,BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		
		lblConnectedas = new JLabel("Not connected");
		panel.add(lblConnectedas);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(ae-> {
				ConfigDialog diag = new ConfigDialog();
				diag.setVisible(true);
				
				try {
					lblConnectedas.setText("Connected as :" + MkmAPIConfig.getInstance().getAuthenticator().getAuthenticatedUser());
				} catch (IOException e) {
					lblConnectedas.setText(e.getMessage());
				}
			
		});
		panel.add(btnConnect);
		
		
		
	}
	
}
