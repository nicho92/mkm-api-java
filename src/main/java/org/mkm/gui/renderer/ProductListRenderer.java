package org.mkm.gui.renderer;

import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.api.mkm.modele.Product;

public class ProductListRenderer implements ListCellRenderer<Product> {

	DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	ProductListPanel render;
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Product> list, Product value, int index,boolean isSelected, boolean cellHasFocus) {
		render =new ProductListPanel(value); 
		 if (isSelected) {
             render.setBackground(SystemColor.inactiveCaption);
         }
		
		return render;
	}

}
