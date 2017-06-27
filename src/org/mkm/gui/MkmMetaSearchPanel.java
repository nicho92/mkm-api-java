package org.mkm.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.api.mkm.modele.Article;
import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.api.mkm.modele.Metaproduct;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.Product.PRODUCT_ATTS;
import org.api.mkm.services.ArticleService;
import org.api.mkm.services.ProductServices;
import org.mkm.gui.modeles.ArticlesTableModel;
import org.mkm.gui.renderer.ProductListRenderer;

public class MkmMetaSearchPanel extends JPanel {
	private JTextField txtSearch;
	private JPanel PanelSouth;
	private JScrollPane panelWest;
	private JList<Product> listResults;
	private JScrollPane panelCenter;
	private JTable tableArticles;
	private DefaultListModel<Product> productsModel;
	private ArticlesTableModel articlesModel;
	
	private JLabel lblSearchProduct;
	private JPanel panelEast;
	private JLabel lblPics;
	
	private Product selectedProduct;
	private Article selectedArticle;
	private JComboBox comboBox;
	
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
		
		lblSearchProduct = new JLabel("Search meta product : ");
		panelNorth.add(lblSearchProduct);
		
		comboBox = new JComboBox<String>(new DefaultComboBoxModel<String>(Metaproduct.getLangs()));
		panelNorth.add(comboBox);
		panelNorth.add(txtSearch);
		txtSearch.setColumns(15);
		
		PanelSouth = new JPanel();
		add(PanelSouth, BorderLayout.SOUTH);
		
		panelWest = new JScrollPane();
		add(panelWest, BorderLayout.WEST);
		productsModel = new DefaultListModel<Product>();
		listResults = new JList<Product>(productsModel);
		listResults.setCellRenderer(new ProductListRenderer());
		listResults.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				loadArticle(listResults.getSelectedValue());
			}
		});
		panelWest.setViewportView(listResults);
		
		panelCenter = new JScrollPane();
		add(panelCenter, BorderLayout.CENTER);
		
		articlesModel = new ArticlesTableModel();
		tableArticles = new JTable(articlesModel);
		tableArticles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				selectedArticle = (Article)articlesModel.getValueAt(tableArticles.getSelectedRow(), 0);
			}
		});
		panelCenter.setViewportView(tableArticles);
		
	}
	
	
	protected void search(String text) {
		ProductServices services = new ProductServices();
		Map<PRODUCT_ATTS, String> map = new HashMap<PRODUCT_ATTS,String>();
		map.put(PRODUCT_ATTS.idLanguage, ""+(comboBox.getSelectedIndex()+1));
		
		
		try {
			Metaproduct prods = services.findMetaProduct(text, map);
			
			System.out.println(prods.getIdProduct());
			
			for(Integer p : prods.getIdProduct())
				productsModel.addElement(services.getProductById(p));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
		} 
		
	}


	public MkmMetaSearchPanel() {
		initGUI();
			
		try {
			
			panelEast = new JPanel();
			add(panelEast, BorderLayout.EAST);
			
			lblPics = new JLabel("");
			panelEast.add(lblPics);
		}  catch (Exception e) {
			
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
