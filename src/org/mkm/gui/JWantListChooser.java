package org.mkm.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import org.api.mkm.modele.Wantslist;
import org.api.mkm.services.WantsService;

public class JWantListChooser extends JDialog {

	WantsService service = new WantsService();
	
	Wantslist selected;
	JComboBox<Wantslist> cboList;
	
	public JWantListChooser() throws Exception {
		setModal(true);
		DefaultComboBoxModel<Wantslist> model = new DefaultComboBoxModel<Wantslist>();
		
		for(Wantslist l : service.getWantList())
			model.addElement(l);
		
		cboList = new JComboBox<Wantslist>(model);
		cboList.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				selected = (Wantslist)cboList.getSelectedItem();
				dispose();
			}
		});
		
		this.getContentPane().add(cboList);
		this.pack();
		
	}
	
	public Wantslist getSelected() {
		return selected;
	}
	
	
}
