package org.mkm.gui.modeles;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.beanutils.PropertyUtils;
import org.api.mkm.modele.Article;

public class ArticlesTableModel extends DefaultTableModel{

	private static final String[] columns={"product","seller","Localization","price","condition","foil","signed","playset","comments"};
	
	List<Article> articles;
	
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
			if(columnIndex==2)
				return String.class;
			
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
			case 1 : return a.getSeller();
			case 2: return a.getSeller().getAddress();
			case 3: return a.getPrice();
			case 4: return a.getCondition();
			case 5 : return a.isFoil();
			case 6 : return a.isSigned();
			case 7 : return a.isPlayset();
			case 8 : return a.getComments();
		default : return 0;
		}
		
	}
	
	
}
