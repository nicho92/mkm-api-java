package org.mkm.gui;

import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import org.api.mkm.modele.Wantslist;
import org.api.mkm.services.WantsService;

public class JWantListChooser extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient WantsService service;
	private Wantslist selected;
	private JComboBox<Wantslist> cboList;
	
	public JWantListChooser() throws IOException  {
		service = new WantsService();
		setModal(true);
		DefaultComboBoxModel<Wantslist> model = new DefaultComboBoxModel<>();
		
		for(Wantslist l : service.getWantList())
			model.addElement(l);
		
		cboList = new JComboBox<>(model);
		cboList.addItemListener(ie->{
				selected = (Wantslist)cboList.getSelectedItem();
				dispose();
		});
		
		this.getContentPane().add(cboList);
		this.pack();
		
	}
	
	public Wantslist getSelected() {
		return selected;
	}
	
	
}
