package org.mkm.gui.modeles;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.api.mkm.modele.LightArticle;

public class LightArticlesTableModel extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String[] columns={"product","expansion","price","condition","foil","signed","playset","altered","comments","count","LocationName","idArticle","idProduct",};
	
	private List<LightArticle> articles;
	
	public void init(List<LightArticle> articles)
	{
		this.articles=articles;
		fireTableDataChanged();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

//	@Override
//	public Class<?> getColumnClass(int columnIndex) {
//		
//		try {
//			return PropertyUtils.getPropertyType(new Article(), columns[columnIndex]);
//		} catch (Exception e) {
//			return super.getColumnClass(columnIndex);
//		}
//	
//	}
	
	
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
		
		LightArticle a = articles.get(row);
		
		switch(column)
		{
			case 0: return a;
			case 1: return a.getProduct().getExpansion();
			case 2: return a.getPrice();
			case 3: return a.getCondition();
			case 4 : return a.isFoil();
			case 5 : return a.isSigned();
			case 6 : return a.isPlayset();
			case 7 : return a.isAltered();
			case 8 : return a.getComments();
			case 9 : return a.getCount();
			case 10: return a.getProduct().getLocName();
			case 11: return a.getIdArticle();
			case 12: return a.getIdProduct();
		default : return 0;
		}
		
	}
	
	
}
