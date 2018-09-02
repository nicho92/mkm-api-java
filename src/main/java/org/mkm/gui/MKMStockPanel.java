package org.mkm.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.api.mkm.modele.Article;
import org.api.mkm.services.StockService;
import org.api.mkm.tools.MkmConstants;
import org.mkm.gui.modeles.StockTableModel;

public class MKMStockPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tableArticles;
	private StockTableModel articlesModel;

	private void initGUI()
	{
		JButton btnLoadStock;
		JButton btnUpQte;
		JButton btnDownQte;
		JScrollPane panelCenter;

		setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		add(panelNorth, BorderLayout.NORTH);
		
		btnLoadStock = new JButton("Load Stock");
		btnLoadStock.addActionListener(ae->{
				StockService serv = new StockService();
				try {
					articlesModel.init(serv.getStock());
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
				} 
		});
		panelNorth.add(btnLoadStock);
		
		btnDownQte = new JButton("-");
		btnDownQte.addActionListener(ae->{
				Article a = (Article)tableArticles.getValueAt(tableArticles.getSelectedRow(), 0);
				
				StockService serv = new StockService();
				try {
						serv.changeQte(a, -1);
						articlesModel.fireTableDataChanged();
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
				} 
		});
		panelNorth.add(btnDownQte);
		
		btnUpQte = new JButton("+");
		btnUpQte.addActionListener(ae->{
				Article a = (Article)tableArticles.getValueAt(tableArticles.getSelectedRow(), 0);
				StockService serv = new StockService();
				try {
						serv.changeQte(a, 1);
						articlesModel.fireTableDataChanged();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
				} 
		});
		panelNorth.add(btnUpQte);
		
		panelCenter = new JScrollPane();
		add(panelCenter, BorderLayout.CENTER);
		
		articlesModel = new StockTableModel();
		tableArticles = new JTable(articlesModel);
		panelCenter.setViewportView(tableArticles);
		
	}

	public MKMStockPanel() {
		initGUI();
			
	}

}
