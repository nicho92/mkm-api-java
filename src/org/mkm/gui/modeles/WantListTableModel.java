package org.mkm.gui.modeles;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.WantItem;
import org.api.mkm.modele.Wantslist;

public class WantListTableModel extends DefaultTableModel{

	private static final String[] columns={"product","wishPrice","minCondition","foil","signed","playset","mailAlert","idLanguage"};
	
	List<WantItem> articles;
	
	public void init(Wantslist selectedValue)
	{
		this.articles=selectedValue.getItem();
		fireTableDataChanged();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		
		try {
			return PropertyUtils.getPropertyType(new WantItem(), columns[columnIndex]);
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
		
		WantItem a = articles.get(row);
			switch(column)
			{
				case 0: return a;
				case 1 : return a.getWishPrice();
				case 2: return a.getMinCondition();
				case 3 : return a.isFoil();
				case 4 : return a.isSigned();
				case 5 : return a.isPlayset();
				case 6 : return a.isMailAlert();
				case 7 : return a.getIdLanguage();
			default : return 0;
			}
	}
	
	
}
