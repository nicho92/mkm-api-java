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

import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.Product.PRODUCT_ATTS;
import org.api.mkm.services.ArticleService;
import org.api.mkm.services.ProductServices;
import org.mkm.gui.modeles.ArticlesTableModel;
import org.mkm.gui.renderer.ProductListRenderer;

public class MkmMetaSearchPanel extends JPanel {
	private JTextField txtSearch;
	private JPanel panelSouth;
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
	private JComboBox comboBox;
	private JLabel lblOrById;
	private JTextField txtIdMeta;
	
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
		
		comboBox = new JComboBox<String>(new DefaultComboBoxModel<String>(ProductServices.getLangs()));
		panelNorth.add(comboBox);
		panelNorth.add(txtSearch);
		txtSearch.setColumns(15);
		
		lblOrById = new JLabel("or by id : ");
		panelNorth.add(lblOrById);
		
		txtIdMeta = new JTextField();
		txtIdMeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchID(Integer.parseInt(txtIdMeta.getText()));
			}
		});
		panelNorth.add(txtIdMeta);
		txtIdMeta.setColumns(10);
		
		panelSouth = new JPanel();
		add(panelSouth, BorderLayout.SOUTH);
		
		panelWest = new JScrollPane();
		add(panelWest, BorderLayout.WEST);
		productsModel = new DefaultListModel<>();
		listResults = new JList<>(productsModel);
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
		
		panelCenter.setViewportView(tableArticles);
		
	}
	protected void searchID(int id) {
		ProductServices services = new ProductServices();
		try {
			productsModel.removeAllElements();
			
			Product p = services.getMetaProductById(id);
				productsModel.addElement(p);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
		} 
		
	}
	
	protected void search(String text) {
		ProductServices services = new ProductServices();
		Map<PRODUCT_ATTS, String> map = new HashMap<>();
		map.put(PRODUCT_ATTS.idLanguage, ""+(comboBox.getSelectedIndex()+1));
		productsModel.removeAllElements();
		
		try {
			for(Product p : services.findMetaProduct(text, map))
				productsModel.addElement(p);
			
			
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
		Map<ARTICLES_ATT, String> atts = new HashMap<>();
								atts.put(ARTICLES_ATT.start, "0");
								atts.put(ARTICLES_ATT.maxResults, "100");
		try {
			articlesModel.init(service.find(selectedValue, atts));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
