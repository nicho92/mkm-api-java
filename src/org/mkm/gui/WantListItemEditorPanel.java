package org.mkm.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.api.mkm.modele.MkmBoolean;
import org.api.mkm.modele.WantItem;

public class WantListItemEditorPanel extends JDialog {

	private WantItem wantItem;
	private JTextField txtCount;
	
	JComboBox<MkmBoolean> cboAltered;
	JComboBox<MkmBoolean> cboFoil ;
	JComboBox<MkmBoolean> cboPlaySet; 
	JComboBox<MkmBoolean> cboMailAlert; 
	JComboBox<String> cboMinCondition ;
	JComboBox<MkmBoolean> cboSigned;
	
	private MkmBoolean[] values = {new MkmBoolean(""),new MkmBoolean(true),new MkmBoolean(false)};
	private JTextField txtWishPrice;
	
	public WantListItemEditorPanel(WantItem wl) {
		this.wantItem=wl;
		initgui();
		pack();
		setTitle(wl.getProduct().toString());
		setModal(true);
	}
	
	private void initgui()
	{

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{94, 124, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblCount = new JLabel("Count");
		GridBagConstraints gbc_lblCount = new GridBagConstraints();
		gbc_lblCount.anchor = GridBagConstraints.EAST;
		gbc_lblCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblCount.gridx = 0;
		gbc_lblCount.gridy = 0;
		getContentPane().add(lblCount, gbc_lblCount);
		
		txtCount = new JTextField(wantItem.getCount());
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		getContentPane().add(txtCount, gbc_textField);
		txtCount.setColumns(10);
		
		JLabel lblWantPrice = new JLabel("Want Price");
		GridBagConstraints gbc_lblWantPrice = new GridBagConstraints();
		gbc_lblWantPrice.anchor = GridBagConstraints.EAST;
		gbc_lblWantPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblWantPrice.gridx = 0;
		gbc_lblWantPrice.gridy = 1;
		getContentPane().add(lblWantPrice, gbc_lblWantPrice);
		
		txtWishPrice = new JTextField();
		GridBagConstraints gbc_txtWishPrice = new GridBagConstraints();
		gbc_txtWishPrice.insets = new Insets(0, 0, 5, 0);
		gbc_txtWishPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtWishPrice.gridx = 1;
		gbc_txtWishPrice.gridy = 1;
		getContentPane().add(txtWishPrice, gbc_txtWishPrice);
		txtWishPrice.setColumns(10);
		
		JLabel lblAltered = new JLabel("Altered");
		GridBagConstraints gbc_lblAltered = new GridBagConstraints();
		gbc_lblAltered.anchor = GridBagConstraints.EAST;
		gbc_lblAltered.insets = new Insets(0, 0, 5, 5);
		gbc_lblAltered.gridx = 0;
		gbc_lblAltered.gridy = 2;
		getContentPane().add(lblAltered, gbc_lblAltered);
		
		cboAltered = new JComboBox<MkmBoolean>(new DefaultComboBoxModel<MkmBoolean>(values));
		GridBagConstraints gbc_cboAltered = new GridBagConstraints();
		gbc_cboAltered.insets = new Insets(0, 0, 5, 0);
		gbc_cboAltered.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboAltered.gridx = 1;
		gbc_cboAltered.gridy = 2;
		getContentPane().add(cboAltered, gbc_cboAltered);
		
		JLabel lblFoil = new JLabel("Foil ");
		GridBagConstraints gbc_lblFoil = new GridBagConstraints();
		gbc_lblFoil.anchor = GridBagConstraints.EAST;
		gbc_lblFoil.insets = new Insets(0, 0, 5, 5);
		gbc_lblFoil.gridx = 0;
		gbc_lblFoil.gridy = 3;
		getContentPane().add(lblFoil, gbc_lblFoil);
		
		cboFoil = new JComboBox<MkmBoolean>(new DefaultComboBoxModel<MkmBoolean>(values));
		GridBagConstraints gbc_cboFoil = new GridBagConstraints();
		gbc_cboFoil.insets = new Insets(0, 0, 5, 0);
		gbc_cboFoil.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboFoil.gridx = 1;
		gbc_cboFoil.gridy = 3;
		getContentPane().add(cboFoil, gbc_cboFoil);
		
		JLabel lblPlayset = new JLabel("Playset");
		GridBagConstraints gbc_lblPlayset = new GridBagConstraints();
		gbc_lblPlayset.anchor = GridBagConstraints.EAST;
		gbc_lblPlayset.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayset.gridx = 0;
		gbc_lblPlayset.gridy = 4;
		getContentPane().add(lblPlayset, gbc_lblPlayset);
		
		cboPlaySet = new JComboBox<MkmBoolean>(new DefaultComboBoxModel<MkmBoolean>(values));
		GridBagConstraints gbc_cboPlaySet = new GridBagConstraints();
		gbc_cboPlaySet.insets = new Insets(0, 0, 5, 0);
		gbc_cboPlaySet.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboPlaySet.gridx = 1;
		gbc_cboPlaySet.gridy = 4;
		getContentPane().add(cboPlaySet, gbc_cboPlaySet);
		
		JLabel lblMailAlert = new JLabel("Mail Alert ");
		GridBagConstraints gbc_lblMailAlert = new GridBagConstraints();
		gbc_lblMailAlert.anchor = GridBagConstraints.EAST;
		gbc_lblMailAlert.insets = new Insets(0, 0, 5, 5);
		gbc_lblMailAlert.gridx = 0;
		gbc_lblMailAlert.gridy = 5;
		getContentPane().add(lblMailAlert, gbc_lblMailAlert);

		cboMailAlert = new JComboBox<MkmBoolean>(new DefaultComboBoxModel<MkmBoolean>(values));
		GridBagConstraints gbc_cboMailAlert = new GridBagConstraints();
		gbc_cboMailAlert.insets = new Insets(0, 0, 5, 0);
		gbc_cboMailAlert.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboMailAlert.gridx = 1;
		gbc_cboMailAlert.gridy = 5;
		getContentPane().add(cboMailAlert, gbc_cboMailAlert);
		
		JLabel lblMinCondition = new JLabel("Min Condition");
		GridBagConstraints gbc_lblMinCondition = new GridBagConstraints();
		gbc_lblMinCondition.anchor = GridBagConstraints.EAST;
		gbc_lblMinCondition.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinCondition.gridx = 0;
		gbc_lblMinCondition.gridy = 6;
		getContentPane().add(lblMinCondition, gbc_lblMinCondition);

		cboMinCondition = new JComboBox<String>(new DefaultComboBoxModel<String>(WantItem.CONDITIONS));
		GridBagConstraints gbc_cboMinCondition = new GridBagConstraints();
		gbc_cboMinCondition.insets = new Insets(0, 0, 5, 0);
		gbc_cboMinCondition.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboMinCondition.gridx = 1;
		gbc_cboMinCondition.gridy = 6;
		getContentPane().add(cboMinCondition, gbc_cboMinCondition);
		
		JLabel lblSigned = new JLabel("Signed");
		GridBagConstraints gbc_lblSigned = new GridBagConstraints();
		gbc_lblSigned.anchor = GridBagConstraints.EAST;
		gbc_lblSigned.insets = new Insets(0, 0, 5, 5);
		gbc_lblSigned.gridx = 0;
		gbc_lblSigned.gridy = 7;
		getContentPane().add(lblSigned, gbc_lblSigned);
		
		cboSigned = new JComboBox<MkmBoolean>(new DefaultComboBoxModel<MkmBoolean>(values));
		GridBagConstraints gbc_cboSigned = new GridBagConstraints();
		gbc_cboSigned.insets = new Insets(0, 0, 5, 0);
		gbc_cboSigned.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboSigned.gridx = 1;
		gbc_cboSigned.gridy = 7;
		getContentPane().add(cboSigned, gbc_cboSigned);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 8;
		getContentPane().add(panel, gbc_panel);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramActionEvent) {
				save();
				dispose();
			}
		});
		panel.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramActionEvent) {
				dispose();
			}
		});
		panel.add(btnCancel);
	}
		
	
	public void save() {
		try{
			wantItem.setCount(Integer.parseInt(this.txtCount.getText()));
		}catch(Exception e)
		{ }
		
		try{
			wantItem.setWishPrice(Double.parseDouble(this.txtWishPrice.getText()));
		}catch(Exception e)
		{ }
		
		
		wantItem.setAltered((MkmBoolean)cboAltered.getSelectedItem());
		wantItem.setFoil((MkmBoolean)cboFoil.getSelectedItem());
		wantItem.setMailAlert((MkmBoolean)cboMailAlert.getSelectedItem());
		wantItem.setPlayset((MkmBoolean)cboPlaySet.getSelectedItem());
		wantItem.setSigned((MkmBoolean)cboSigned.getSelectedItem());
	}

	public WantItem getItem() {
		return wantItem;
	}
	
	
	
	
	
	
}
