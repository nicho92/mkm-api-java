package org.mkm.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.api.mkm.modele.Product;
import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.api.mkm.modele.Product.PRODUCT_ATTS;
import org.api.mkm.services.ArticleService;
import org.api.mkm.services.AuthenticationServices;
import org.api.mkm.services.ProductServices;
import org.api.mkm.tools.MkmAPIConfig;
import org.magic.api.pricers.impl.MagicCardMarketPricer2;
import org.mkm.gui.modeles.ArticlesTableModel;

public class MkmSearchPanel extends JPanel {
	private JTextField txtSearch;
	private JPanel PanelSouth;
	private JLabel label;
	private JScrollPane panelWest;
	private JList<Product> listResults;
	private JScrollPane panelCenter;
	private JTable tableArticles;
	private DefaultListModel<Product> productsModel;
	private ArticlesTableModel articlesModel;
	private JLabel lblSearchProduct;
	
	private void initGUI()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		add(panelNorth, BorderLayout.NORTH);
		
		txtSearch = new JTextField();
		txtSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				search(txtSearch.getText());
			}
		});
		
		lblSearchProduct = new JLabel("Search product : ");
		panelNorth.add(lblSearchProduct);
		panelNorth.add(txtSearch);
		txtSearch.setColumns(15);
		
		PanelSouth = new JPanel();
		add(PanelSouth, BorderLayout.SOUTH);
		
		label = new JLabel("Connected as : ");
		PanelSouth.add(label);
		
		panelWest = new JScrollPane();
		add(panelWest, BorderLayout.WEST);
		productsModel = new DefaultListModel<Product>();
		listResults = new JList<Product>(productsModel);
		listResults.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				loadArticle(listResults.getSelectedValue());
			}
		});
		panelWest.setViewportView(listResults);
		
		panelCenter = new JScrollPane();
		add(panelCenter, BorderLayout.CENTER);
		
		articlesModel = new ArticlesTableModel();
		tableArticles = new JTable(articlesModel);
		panelCenter.setViewportView(tableArticles);
		
	}
	
	
	protected void search(String text) {
		ProductServices services = new ProductServices();
		Map<PRODUCT_ATTS, String> map = new HashMap<PRODUCT_ATTS,String>();
		
		try {
			List<Product> prods = services.find(text, map);
			for(Product p : prods)
				productsModel.addElement(p);
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
		} 
		
	}


	public MkmSearchPanel() {
		initGUI();
		
		MagicCardMarketPricer2 pricer = new MagicCardMarketPricer2();
		
		MkmAPIConfig.getInstance().init(pricer.getProperty("APP_ACCESS_TOKEN_SECRET").toString(),
										pricer.getProperty("APP_ACCESS_TOKEN").toString(),
										pricer.getProperty("APP_SECRET").toString(),
										pricer.getProperty("APP_TOKEN").toString());
		
		
		
		try {
			label.setText("Connected as " + MkmAPIConfig.getInstance().getAuthenticator().getAuthenticatedUser());
		}  catch (Exception e) {
			label.setText("Not connected");
		}
		
	}

	protected void loadArticle(Product selectedValue) {
		ArticleService service = new ArticleService();
		Map<ARTICLES_ATT, String> atts = new HashMap<ARTICLES_ATT, String>();
								atts.put(ARTICLES_ATT.start, "0");
								atts.put(ARTICLES_ATT.maxResults, "100");
		try {
			articlesModel.init(service.find(selectedValue, atts));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
