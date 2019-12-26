package org.mkm.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.api.mkm.services.DevelopperServices;
import org.api.mkm.tools.MkmConstants;

public class MkmDevPanel extends JPanel {
	private JTextField txtUri;
	private DevelopperServices service;
	
	public MkmDevPanel() {
		setLayout(new BorderLayout(0, 0));
		service = new DevelopperServices();
		JComboBox<String> cboMethod = new JComboBox<>();
		cboMethod.setModel(new DefaultComboBoxModel<>(new String[] {"GET", "POST", "PUT", "DELETE"}));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		JButton launcheBtn = new JButton("Run");
		
		JLabel lblUrl = new JLabel("URL :");
		panel.add(lblUrl);
		
		txtUri = new JTextField(MkmConstants.MKM_API_URL,50);
		panel.add(txtUri);
		panel.add(cboMethod);
		panel.add(launcheBtn);
		JTextArea txtResult = new JTextArea();
		txtResult.setEditable(false);
		add(new JScrollPane(txtResult), BorderLayout.CENTER);
		
		JPanel panel1 = new JPanel();
		add(panel1, BorderLayout.WEST);
		panel1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Post XML content here : ");
		panel1.add(lblNewLabel, BorderLayout.NORTH);
		
		JEditorPane txtContent = new JEditorPane();
		txtContent.setPreferredSize(new Dimension(300, 1));
		panel1.add(new JScrollPane(txtContent), BorderLayout.CENTER);
		
		
		launcheBtn.addActionListener(l->{
			
			try {
				
				String postContent = txtContent.getText();
				
				if(postContent.isEmpty())
					postContent=null;
				
				
				String ret = service.execute(txtUri.getText(),postContent , cboMethod.getSelectedItem().toString());
				txtResult.setText(ret);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		
	}

}
