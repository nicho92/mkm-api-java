package org.mkm.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.exceptions.MkmException;
import org.api.mkm.tools.MkmAPIConfig;

public class ConfigDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtAppToken;
	private JTextField txtAppSecret;
	private JTextField txtAccessToken;
	private JTextField txtAccessTokenSecret;
	private transient Logger logger = LogManager.getLogger(this.getClass());


	public ConfigDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setModal(true);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gblcontentPanel = new GridBagLayout();
		gblcontentPanel.columnWidths = new int[]{0, 0, 0};
		gblcontentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gblcontentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gblcontentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gblcontentPanel);
		
			JLabel lblAppToken = new JLabel("App token :");
			GridBagConstraints gbclblAppToken = new GridBagConstraints();
			gbclblAppToken.anchor = GridBagConstraints.WEST;
			gbclblAppToken.insets = new Insets(0, 0, 5, 5);
			gbclblAppToken.gridx = 0;
			gbclblAppToken.gridy = 0;
			contentPanel.add(lblAppToken, gbclblAppToken);
		
		
			txtAppToken = new JTextField();
			GridBagConstraints gbctxtAppToken = new GridBagConstraints();
			gbctxtAppToken.insets = new Insets(0, 0, 5, 0);
			gbctxtAppToken.fill = GridBagConstraints.HORIZONTAL;
			gbctxtAppToken.gridx = 1;
			gbctxtAppToken.gridy = 0;
			contentPanel.add(txtAppToken, gbctxtAppToken);
			txtAppToken.setColumns(10);
		
		
			JLabel lblAppSecret = new JLabel("App secret :");
			GridBagConstraints gbclblAppSecret = new GridBagConstraints();
			gbclblAppSecret.anchor = GridBagConstraints.WEST;
			gbclblAppSecret.insets = new Insets(0, 0, 5, 5);
			gbclblAppSecret.gridx = 0;
			gbclblAppSecret.gridy = 1;
			contentPanel.add(lblAppSecret, gbclblAppSecret);
		
		
			txtAppSecret = new JTextField();
			GridBagConstraints gbctxtAppSecret = new GridBagConstraints();
			gbctxtAppSecret.insets = new Insets(0, 0, 5, 0);
			gbctxtAppSecret.fill = GridBagConstraints.HORIZONTAL;
			gbctxtAppSecret.gridx = 1;
			gbctxtAppSecret.gridy = 1;
			contentPanel.add(txtAppSecret, gbctxtAppSecret);
			txtAppSecret.setColumns(10);
		
		
			JLabel lblAccessToken = new JLabel("Access token :");
			GridBagConstraints gbclblAccessToken = new GridBagConstraints();
			gbclblAccessToken.anchor = GridBagConstraints.WEST;
			gbclblAccessToken.insets = new Insets(0, 0, 5, 5);
			gbclblAccessToken.gridx = 0;
			gbclblAccessToken.gridy = 2;
			contentPanel.add(lblAccessToken, gbclblAccessToken);
		
		
			txtAccessToken = new JTextField();
			GridBagConstraints gbctxtAccessToken = new GridBagConstraints();
			gbctxtAccessToken.insets = new Insets(0, 0, 5, 0);
			gbctxtAccessToken.fill = GridBagConstraints.HORIZONTAL;
			gbctxtAccessToken.gridx = 1;
			gbctxtAccessToken.gridy = 2;
			contentPanel.add(txtAccessToken, gbctxtAccessToken);
			txtAccessToken.setColumns(10);
		
		
			JLabel lblAccessTokenSecret = new JLabel("Access token secret :");
			GridBagConstraints gbclblAccessTokenSecret = new GridBagConstraints();
			gbclblAccessTokenSecret.insets = new Insets(0, 0, 0, 5);
			gbclblAccessTokenSecret.anchor = GridBagConstraints.EAST;
			gbclblAccessTokenSecret.gridx = 0;
			gbclblAccessTokenSecret.gridy = 3;
			contentPanel.add(lblAccessTokenSecret, gbclblAccessTokenSecret);
		
		
			txtAccessTokenSecret = new JTextField();
			GridBagConstraints gbctxtAccessTokenSecret = new GridBagConstraints();
			gbctxtAccessTokenSecret.anchor = GridBagConstraints.NORTH;
			gbctxtAccessTokenSecret.fill = GridBagConstraints.HORIZONTAL;
			gbctxtAccessTokenSecret.gridx = 1;
			gbctxtAccessTokenSecret.gridy = 3;
			contentPanel.add(txtAccessTokenSecret, gbctxtAccessTokenSecret);
			txtAccessTokenSecret.setColumns(10);
		
		
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
				JButton okButton = new JButton("OK");
				okButton.addActionListener(ae->{
						try {
							MkmAPIConfig.getInstance().init(txtAccessTokenSecret.getText(),txtAccessToken.getText(),txtAppSecret.getText(),txtAppToken.getText());
							dispose();
							
							JFrame f = new JFrame();
							f.setSize(750, 550);
							f.add(new MkmPanel());
							f.pack();
							f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							f.setTitle("MKM API - Samples");
							f.setVisible(true);
							
						} catch (MkmException e) {
							logger.error(e);
						}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				pack();
		}
	

}
