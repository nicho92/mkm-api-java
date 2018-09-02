package org.mkm.gui.modeles;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.beanutils.PropertyUtils;
import org.api.mkm.modele.Order;

public class OrderTableModel extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String[] columns={"idOrder","seller","article","totalValue"};
	
	private List<Order> articles;
	
	public void init(List<Order> articles)
	{
		this.articles=articles;
		fireTableDataChanged();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		
		try {
			return PropertyUtils.getPropertyType(new Order(), columns[columnIndex]);
		} catch (Exception e) {
			return super.getColumnClass(columnIndex);
		}
	
	}
	
	
	@Override
	public String getColumnName(int column) {
		return columns[column];
	}
	
	@Override
	public int getColumnCount() {
		return columns.length;
	}
	
	@Override
	public int getRowCount() {
		if(articles==null)
			return 0;
		
		return articles.size();
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		
		Order a = articles.get(row);
		
		switch(column)
		{
			case 0: return a.getIdOrder();
			case 1 : return a.getSeller();
			case 2: return a.getArticle();
			case 3: return a.getTotalValue();
		default : return 0;
		}
		
	}
	
	
}
