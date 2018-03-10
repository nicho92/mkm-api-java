package org.mkm.gui.modeles;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.beanutils.PropertyUtils;
import org.api.mkm.modele.Article;

public class StockTableModel extends DefaultTableModel{

	private static final String[] columns={"product","price","condition","foil","signed","playset","altered","comments","count"};
	
	private List<Article> articles;
	
	public void init(List<Article> articles)
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
			return PropertyUtils.getPropertyType(new Article(), columns[columnIndex]);
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
		
		Article a = articles.get(row);
		
		switch(column)
		{
			case 0: return a;
			case 1: return a.getPrice();
			case 2: return a.getCondition();
			case 3 : return a.isFoil();
			case 4 : return a.isSigned();
			case 5 : return a.isPlayset();
			case 6 : return a.isAltered();
			case 7 : return a.getComments();
			case 8 : return a.getCount();
		default : return 0;
		}
		
	}
	
	
}
