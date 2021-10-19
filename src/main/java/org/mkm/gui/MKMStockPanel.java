package org.mkm.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.api.mkm.modele.LightArticle;
import org.api.mkm.services.StockService;
import org.api.mkm.tools.MkmConstants;
import org.mkm.gui.modeles.LightArticlesTableModel;

public class MKMStockPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tableArticles;
	private LightArticlesTableModel articlesModel;

	private int page=1;
	
	private void initGUI()
	{
		JButton btnLoadStock;
		JButton btnUpQte;
		JButton btnDownQte;
		JScrollPane panelCenter;
		
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		add(panelNorth, BorderLayout.NORTH);
		
		btnLoadStock = new JButton("Load Stock " + page);
		StockService serv = new StockService();
		
		btnLoadStock.addActionListener(ae->{
				try {
					
					articlesModel.init(serv.getStock(page));
					page = page+100;
					btnLoadStock.setText("Load Stock " + page);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
				} 
		});
		panelNorth.add(btnLoadStock);
		
		btnDownQte = new JButton("-");
		btnDownQte.addActionListener(ae->{
				LightArticle a = (LightArticle)tableArticles.getValueAt(tableArticles.getSelectedRow(), 0);
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
				LightArticle a = (LightArticle)tableArticles.getValueAt(tableArticles.getSelectedRow(), 0);
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
		
		articlesModel = new LightArticlesTableModel();
		tableArticles = new JTable(articlesModel);
		panelCenter.setViewportView(tableArticles);
		
	}

	public MKMStockPanel() {
		initGUI();
			
	}

}
