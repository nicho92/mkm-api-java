package org.mkm.gui.modeles;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Product;

public class ArticlesTableModel extends DefaultTableModel{

	private static final String[] columns={"product","seller","price","condition","foil","signed","playset","comments"};
	
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
		
		try {
			return BeanUtils.describe(a).get(columns[column]);
		} catch (Exception e) {
			return "";
		} 
		
	}
	
	
}
