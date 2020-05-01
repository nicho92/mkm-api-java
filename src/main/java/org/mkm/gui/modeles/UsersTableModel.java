package org.mkm.gui.modeles;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.api.mkm.modele.User;

public class UsersTableModel extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String[] columns={"userName","adress","avg shipping time","Email","commercial","seller","legal information","% lost","phone","reputation","Risk","sells","solds","vacation"};
	
	private List<User> users;
	
	public void init(List<User> articles)
	{
		this.users=articles;
		fireTableDataChanged();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
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
		if(users==null)
			return 0;
		
		return users.size();
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		
		User a = users.get(row);
		switch(column)
		{
			case 0: return a;
			case 1 : return a.getAddress();
			case 2 : return a.getAvgShippingTime();
			case 3: return a.getEmail();
			case 4: return a.getIsCommercial();
			case 5: return a.getIsSeller();
			case 6 : return a.getLegalInformation();
			case 7 : return a.getLossPercentage();
			case 8 : return a.getPhone();
			case 9 : return a.getReputation();
			case 10 : return a.getRiskGroup();
			case 11 : return a.getSellCount();
			case 12 : return a.getSoldItems();
			case 13 : return a.isOnVacation();
		default : return 0;
		}
		
	}
	
	
}
