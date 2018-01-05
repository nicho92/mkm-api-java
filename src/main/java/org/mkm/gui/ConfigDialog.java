package org.mkm.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.api.mkm.exceptions.MkmException;
import org.api.mkm.tools.MkmAPIConfig;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConfigDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtAppToken;
	private JTextField txtAppSecret;
	private JTextField txtAccessToken;
	private JTextField txtAccessTokenSecret;


	public ConfigDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setModal(true);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblAppToken = new JLabel("App token :");
			GridBagConstraints gbc_lblAppToken = new GridBagConstraints();
			gbc_lblAppToken.anchor = GridBagConstraints.WEST;
			gbc_lblAppToken.insets = new Insets(0, 0, 5, 5);
			gbc_lblAppToken.gridx = 0;
			gbc_lblAppToken.gridy = 0;
			contentPanel.add(lblAppToken, gbc_lblAppToken);
		}
		{
			txtAppToken = new JTextField();
			GridBagConstraints gbc_txtAppToken = new GridBagConstraints();
			gbc_txtAppToken.insets = new Insets(0, 0, 5, 0);
			gbc_txtAppToken.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtAppToken.gridx = 1;
			gbc_txtAppToken.gridy = 0;
			contentPanel.add(txtAppToken, gbc_txtAppToken);
			txtAppToken.setColumns(10);
		}
		{
			JLabel lblAppSecret = new JLabel("App secret :");
			GridBagConstraints gbc_lblAppSecret = new GridBagConstraints();
			gbc_lblAppSecret.anchor = GridBagConstraints.WEST;
			gbc_lblAppSecret.insets = new Insets(0, 0, 5, 5);
			gbc_lblAppSecret.gridx = 0;
			gbc_lblAppSecret.gridy = 1;
			contentPanel.add(lblAppSecret, gbc_lblAppSecret);
		}
		{
			txtAppSecret = new JTextField();
			GridBagConstraints gbc_txtAppSecret = new GridBagConstraints();
			gbc_txtAppSecret.insets = new Insets(0, 0, 5, 0);
			gbc_txtAppSecret.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtAppSecret.gridx = 1;
			gbc_txtAppSecret.gridy = 1;
			contentPanel.add(txtAppSecret, gbc_txtAppSecret);
			txtAppSecret.setColumns(10);
		}
		{
			JLabel lblAccessToken = new JLabel("Access token :");
			GridBagConstraints gbc_lblAccessToken = new GridBagConstraints();
			gbc_lblAccessToken.anchor = GridBagConstraints.WEST;
			gbc_lblAccessToken.insets = new Insets(0, 0, 5, 5);
			gbc_lblAccessToken.gridx = 0;
			gbc_lblAccessToken.gridy = 2;
			contentPanel.add(lblAccessToken, gbc_lblAccessToken);
		}
		{
			txtAccessToken = new JTextField();
			GridBagConstraints gbc_txtAccessToken = new GridBagConstraints();
			gbc_txtAccessToken.insets = new Insets(0, 0, 5, 0);
			gbc_txtAccessToken.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtAccessToken.gridx = 1;
			gbc_txtAccessToken.gridy = 2;
			contentPanel.add(txtAccessToken, gbc_txtAccessToken);
			txtAccessToken.setColumns(10);
		}
		{
			JLabel lblAccessTokenSecret = new JLabel("Access token secret :");
			GridBagConstraints gbc_lblAccessTokenSecret = new GridBagConstraints();
			gbc_lblAccessTokenSecret.insets = new Insets(0, 0, 0, 5);
			gbc_lblAccessTokenSecret.anchor = GridBagConstraints.EAST;
			gbc_lblAccessTokenSecret.gridx = 0;
			gbc_lblAccessTokenSecret.gridy = 3;
			contentPanel.add(lblAccessTokenSecret, gbc_lblAccessTokenSecret);
		}
		{
			txtAccessTokenSecret = new JTextField();
			GridBagConstraints gbc_txtAccessTokenSecret = new GridBagConstraints();
			gbc_txtAccessTokenSecret.anchor = GridBagConstraints.NORTH;
			gbc_txtAccessTokenSecret.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtAccessTokenSecret.gridx = 1;
			gbc_txtAccessTokenSecret.gridy = 3;
			contentPanel.add(txtAccessTokenSecret, gbc_txtAccessTokenSecret);
			txtAccessTokenSecret.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							MkmAPIConfig.getInstance().init(txtAccessTokenSecret.getText(),txtAccessToken.getText(),txtAppSecret.getText(),txtAppToken.getText());
							dispose();
							new MkmFrame();
						} catch (MkmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				pack();
		}
	}

}
