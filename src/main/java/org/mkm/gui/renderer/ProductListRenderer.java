package org.mkm.gui.renderer;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.api.mkm.modele.Product;

public class ProductListRenderer extends ProductListPanel implements ListCellRenderer<Product> {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<? extends Product> list, Product value, int index,boolean isSelected, boolean cellHasFocus) {
		init(value);
		if (isSelected) 
             setBackground(list.getSelectionBackground());
		 else
             setBackground(list.getBackground());

		return this;
	}

}
