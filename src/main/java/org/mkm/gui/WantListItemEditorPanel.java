package org.mkm.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.modele.MkmBoolean;
import org.api.mkm.modele.WantItem;

public class WantListItemEditorPanel extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WantItem wantItem;
	private JTextField txtCount;
	
	JComboBox<MkmBoolean> cboAltered;
	JComboBox<MkmBoolean> cboFoil ;
	JComboBox<MkmBoolean> cboPlaySet; 
	JComboBox<MkmBoolean> cboMailAlert; 
	JComboBox<String> cboMinCondition ;
	JComboBox<MkmBoolean> cboSigned;
	private transient Logger logger = LogManager.getLogger(this.getClass());

	private MkmBoolean[] values = {new MkmBoolean(""),new MkmBoolean(true),new MkmBoolean(false)};
	private JTextField txtWishPrice;
	
	public WantListItemEditorPanel(WantItem wl) {
		try {
			this.wantItem=wl;
		} catch (Exception e) {
			logger.error(e);
		}
		
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
		GridBagConstraints gbclblCount = new GridBagConstraints();
		gbclblCount.anchor = GridBagConstraints.EAST;
		gbclblCount.insets = new Insets(0, 0, 5, 5);
		gbclblCount.gridx = 0;
		gbclblCount.gridy = 0;
		getContentPane().add(lblCount, gbclblCount);
		
		txtCount = new JTextField(""+wantItem.getCount());
		GridBagConstraints gbctextField = new GridBagConstraints();
		gbctextField.insets = new Insets(0, 0, 5, 0);
		gbctextField.fill = GridBagConstraints.HORIZONTAL;
		gbctextField.gridx = 1;
		gbctextField.gridy = 0;
		getContentPane().add(txtCount, gbctextField);
		txtCount.setColumns(10);
		
		JLabel lblWantPrice = new JLabel("Want Price");
		GridBagConstraints gbclblWantPrice = new GridBagConstraints();
		gbclblWantPrice.anchor = GridBagConstraints.EAST;
		gbclblWantPrice.insets = new Insets(0, 0, 5, 5);
		gbclblWantPrice.gridx = 0;
		gbclblWantPrice.gridy = 1;
		getContentPane().add(lblWantPrice, gbclblWantPrice);
		
		txtWishPrice = new JTextField(""+wantItem.getWishPrice());
		GridBagConstraints gbctxtWishPrice = new GridBagConstraints();
		gbctxtWishPrice.insets = new Insets(0, 0, 5, 0);
		gbctxtWishPrice.fill = GridBagConstraints.HORIZONTAL;
		gbctxtWishPrice.gridx = 1;
		gbctxtWishPrice.gridy = 1;
		getContentPane().add(txtWishPrice, gbctxtWishPrice);
		txtWishPrice.setColumns(10);
		
		JLabel lblAltered = new JLabel("Altered");
		GridBagConstraints gbclblAltered = new GridBagConstraints();
		gbclblAltered.anchor = GridBagConstraints.EAST;
		gbclblAltered.insets = new Insets(0, 0, 5, 5);
		gbclblAltered.gridx = 0;
		gbclblAltered.gridy = 2;
		getContentPane().add(lblAltered, gbclblAltered);
		
		cboAltered = new JComboBox<>(new DefaultComboBoxModel<MkmBoolean>(values));
		cboAltered.setSelectedItem(new MkmBoolean(wantItem.isAltered()));
		
		GridBagConstraints gbccboAltered = new GridBagConstraints();
		gbccboAltered.insets = new Insets(0, 0, 5, 0);
		gbccboAltered.fill = GridBagConstraints.HORIZONTAL;
		gbccboAltered.gridx = 1;
		gbccboAltered.gridy = 2;
		getContentPane().add(cboAltered, gbccboAltered);
		
		JLabel lblFoil = new JLabel("Foil ");
		GridBagConstraints gbclblFoil = new GridBagConstraints();
		gbclblFoil.anchor = GridBagConstraints.EAST;
		gbclblFoil.insets = new Insets(0, 0, 5, 5);
		gbclblFoil.gridx = 0;
		gbclblFoil.gridy = 3;
		getContentPane().add(lblFoil, gbclblFoil);
		
		cboFoil = new JComboBox<>(new DefaultComboBoxModel<MkmBoolean>(values));
		cboFoil.setSelectedItem(new MkmBoolean(wantItem.isFoil()));
		GridBagConstraints gbccboFoil = new GridBagConstraints();
		gbccboFoil.insets = new Insets(0, 0, 5, 0);
		gbccboFoil.fill = GridBagConstraints.HORIZONTAL;
		gbccboFoil.gridx = 1;
		gbccboFoil.gridy = 3;
		getContentPane().add(cboFoil, gbccboFoil);
		
		JLabel lblPlayset = new JLabel("Playset");
		GridBagConstraints gbclblPlayset = new GridBagConstraints();
		gbclblPlayset.anchor = GridBagConstraints.EAST;
		gbclblPlayset.insets = new Insets(0, 0, 5, 5);
		gbclblPlayset.gridx = 0;
		gbclblPlayset.gridy = 4;
		getContentPane().add(lblPlayset, gbclblPlayset);
		
		cboPlaySet = new JComboBox<>(new DefaultComboBoxModel<MkmBoolean>(values));
		cboPlaySet.setSelectedItem(new MkmBoolean(wantItem.isPlayset()));
		GridBagConstraints gbccboPlaySet = new GridBagConstraints();
		gbccboPlaySet.insets = new Insets(0, 0, 5, 0);
		gbccboPlaySet.fill = GridBagConstraints.HORIZONTAL;
		gbccboPlaySet.gridx = 1;
		gbccboPlaySet.gridy = 4;
		getContentPane().add(cboPlaySet, gbccboPlaySet);
		
		JLabel lblMailAlert = new JLabel("Mail Alert ");
		GridBagConstraints gbclblMailAlert = new GridBagConstraints();
		gbclblMailAlert.anchor = GridBagConstraints.EAST;
		gbclblMailAlert.insets = new Insets(0, 0, 5, 5);
		gbclblMailAlert.gridx = 0;
		gbclblMailAlert.gridy = 5;
		getContentPane().add(lblMailAlert, gbclblMailAlert);

		cboMailAlert = new JComboBox<>(new DefaultComboBoxModel<MkmBoolean>(values));
		cboMailAlert.setSelectedItem(new MkmBoolean(wantItem.isMailAlert()));
		GridBagConstraints gbccboMailAlert = new GridBagConstraints();
		gbccboMailAlert.insets = new Insets(0, 0, 5, 0);
		gbccboMailAlert.fill = GridBagConstraints.HORIZONTAL;
		gbccboMailAlert.gridx = 1;
		gbccboMailAlert.gridy = 5;
		getContentPane().add(cboMailAlert, gbccboMailAlert);
		
		JLabel lblMinCondition = new JLabel("Min Condition");
		GridBagConstraints gbclblMinCondition = new GridBagConstraints();
		gbclblMinCondition.anchor = GridBagConstraints.EAST;
		gbclblMinCondition.insets = new Insets(0, 0, 5, 5);
		gbclblMinCondition.gridx = 0;
		gbclblMinCondition.gridy = 6;
		getContentPane().add(lblMinCondition, gbclblMinCondition);

		cboMinCondition = new JComboBox<>(new DefaultComboBoxModel<String>(WantItem.CONDITIONS));
		cboMinCondition.setSelectedItem(wantItem.getMinCondition());
		GridBagConstraints gbccboMinCondition = new GridBagConstraints();
		gbccboMinCondition.insets = new Insets(0, 0, 5, 0);
		gbccboMinCondition.fill = GridBagConstraints.HORIZONTAL;
		gbccboMinCondition.gridx = 1;
		gbccboMinCondition.gridy = 6;
		getContentPane().add(cboMinCondition, gbccboMinCondition);
		
		JLabel lblSigned = new JLabel("Signed");
		GridBagConstraints gbclblSigned = new GridBagConstraints();
		gbclblSigned.anchor = GridBagConstraints.EAST;
		gbclblSigned.insets = new Insets(0, 0, 5, 5);
		gbclblSigned.gridx = 0;
		gbclblSigned.gridy = 7;
		getContentPane().add(lblSigned, gbclblSigned);
		
		cboSigned = new JComboBox<>(new DefaultComboBoxModel<MkmBoolean>(values));
		cboSigned.setSelectedItem(new MkmBoolean(wantItem.isSigned()));
		GridBagConstraints gbccboSigned = new GridBagConstraints();
		gbccboSigned.insets = new Insets(0, 0, 5, 0);
		gbccboSigned.fill = GridBagConstraints.HORIZONTAL;
		gbccboSigned.gridx = 1;
		gbccboSigned.gridy = 7;
		getContentPane().add(cboSigned, gbccboSigned);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbcpanel = new GridBagConstraints();
		gbcpanel.gridwidth = 2;
		gbcpanel.fill = GridBagConstraints.BOTH;
		gbcpanel.gridx = 0;
		gbcpanel.gridy = 8;
		getContentPane().add(panel, gbcpanel);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(paramActionEvent->{
				save();
				dispose();
		});
		panel.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(paramActionEvent->{
				wantItem=null;
				dispose();
		});
		panel.add(btnCancel);
	}
		
	
	public void save() {
		try{
			wantItem.setCount(Integer.parseInt(txtCount.getText()));
		}catch(Exception e)
		{ logger.error(e);}
		
		try{
			wantItem.setWishPrice(Double.parseDouble(this.txtWishPrice.getText()));
		}catch(Exception e)
		{logger.error(e); }
		
		wantItem.getIdLanguage().remove(0);
		wantItem.getIdLanguage().add(1);
		wantItem.setAltered((MkmBoolean)cboAltered.getSelectedItem());
		wantItem.setFoil((MkmBoolean)cboFoil.getSelectedItem());
		wantItem.setMailAlert((MkmBoolean)cboMailAlert.getSelectedItem());
		wantItem.setPlayset((MkmBoolean)cboPlaySet.getSelectedItem());
		wantItem.setSigned((MkmBoolean)cboSigned.getSelectedItem());
		
		wantItem.setMinCondition(String.valueOf(cboMinCondition.getSelectedItem()));
	}

	public WantItem getItem() {
		return wantItem;
	}
	
	
	
	
	
	
}
