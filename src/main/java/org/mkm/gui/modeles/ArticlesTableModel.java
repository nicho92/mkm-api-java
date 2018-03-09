package org.mkm.gui.modeles;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.api.mkm.modele.Article;

public class ArticlesTableModel extends DefaultTableModel{

	private static final String[] columns={"product","expansion","seller","Localization","price","condition","foil","signed","playset","altered","comments"};
	
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
			return super.getColumnClass(columnIndex);
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
			case 1 : return a.getProduct().getExpansionName();
			case 2 : return a.getSeller();
			case 3: return (a.getSeller()!=null)? a.getSeller().getAddress() : null;
			case 4: return a.getPrice();
			case 5: return a.getCondition();
			case 6 : return a.isFoil();
			case 7 : return a.isSigned();
			case 8 : return a.isPlayset();
			case 9 : return a.isAltered();
			case 10 : return a.getComments();
		default : return 0;
		}
		
	}
	
	
}
