package org.mkm.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.Product.PRODUCT_ATTS;
import org.api.mkm.modele.WantItem;
import org.api.mkm.modele.Wantslist;
import org.api.mkm.services.ArticleService;
import org.api.mkm.services.CartServices;
import org.api.mkm.services.ProductServices;
import org.api.mkm.services.WantsService;
import org.api.mkm.tools.MkmAPIConfig;
import org.api.mkm.tools.MkmConstants;
import org.mkm.gui.modeles.ArticlesTableModel;
import org.mkm.gui.renderer.ProductListRenderer;

public class MkmSearchPanel extends JPanel {
	private JLabel label;
	private JList<Product> listResults;
	private JTable tableArticles;
	private DefaultListModel<Product> productsModel;
	private ArticlesTableModel articlesModel;
	private transient Logger logger = LogManager.getLogger(this.getClass());

	private JPanel panelEast;
	private JLabel lblPics;
	private JButton btnAddWantlist;
	private JButton btnBasket;
	
	private Product selectedProduct;
	private Article selectedArticle;

	
	private void initGUI()
	{
		JTextField txtSearch;
		JTextField txtID;
		JButton btnExportPriceGuid;
		JScrollPane panelWest;
		JScrollPane panelCenter;
		JPanel panelSouth;
		JLabel lblSearchProduct;

		setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		add(panelNorth, BorderLayout.NORTH);
		
		txtSearch = new JTextField();
		txtSearch.addActionListener(ae->search(txtSearch.getText()));
		
		lblSearchProduct = new JLabel("Search product : ");
		panelNorth.add(lblSearchProduct);
		panelNorth.add(txtSearch);
		txtSearch.setColumns(15);
		
		btnAddWantlist = new JButton("Add WantList");
		btnAddWantlist.addActionListener(ae-> 
			{
				JWantListChooser choose;
				try {
					choose = new JWantListChooser();
					choose.setVisible(true);
					
					Wantslist l = choose.getSelected();
					
					WantsService service = new WantsService();
					
					WantItem it = new WantItem();
						it.setProduct(selectedProduct);
						it.setCount(1);
					
					service.addItem(l, it);
					
					
				} catch (Exception e1) {
					logger.error(e1);
				} 
		});
		panelNorth.add(new JLabel("or by id :"));
		
		txtID = new JTextField();
		txtID.addActionListener(ae->search(Integer.parseInt(txtID.getText())));
		panelNorth.add(txtID);
		txtID.setColumns(10);
		btnAddWantlist.setEnabled(false);
		panelNorth.add(btnAddWantlist);
		
		btnBasket = new JButton("add to Basket");
		btnBasket.addActionListener(ae->{
				CartServices serv = new CartServices();
				try {
					serv.addArticle(selectedArticle);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
				} 
		});
		btnBasket.setEnabled(false);
		panelNorth.add(btnBasket);
		
		btnExportPriceGuid = new JButton("Export PriceGuide");
		btnExportPriceGuid.addActionListener(ae->{
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Choose Location");   
				 
				int userSelection = fileChooser.showSaveDialog(null);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) 
				{
				    File fileToSave = fileChooser.getSelectedFile();
				    ProductServices services = new ProductServices();
				    try {
						services.exportPriceGuide(fileToSave,1);
					} 
				    catch (Exception e) 
				    {
						JOptionPane.showMessageDialog(null, e, MkmConstants.MKM_ERROR, JOptionPane.ERROR_MESSAGE);
					}
				    
				}
		});
		panelNorth.add(btnExportPriceGuid);
		
		panelSouth = new JPanel();
		add(panelSouth, BorderLayout.SOUTH);
		
		label = new JLabel("Connected as : ");
		panelSouth.add(new JLabel("Connected as : "));
		
		panelWest = new JScrollPane();
		add(panelWest, BorderLayout.WEST);
		productsModel = new DefaultListModel<>();
		listResults = new JList<>(productsModel);
		listResults.setCellRenderer(new ProductListRenderer());
		listResults.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				btnAddWantlist.setEnabled(true);
				
				loadArticle(listResults.getSelectedValue());
				
				try{
					
					selectedProduct = listResults.getSelectedValue();
					URL url = new URL("https://fr.magiccardmarket.eu/"+selectedProduct.getImage());
					BufferedImage im = ImageIO.read(url);
					lblPics.setIcon(new ImageIcon(im));
					
					}
					catch(Exception e)
					{
						logger.error(e);
					}
				
				
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
				btnBasket.setEnabled(true);
				selectedArticle = (Article)articlesModel.getValueAt(tableArticles.getSelectedRow(), 0);
			}
		});
		panelCenter.setViewportView(tableArticles);
		
	}
	
	
	protected void search(String text) {
		ProductServices services = new ProductServices();
		Map<PRODUCT_ATTS, String> map = new EnumMap<>(PRODUCT_ATTS.class);
		
		try {
			productsModel.removeAllElements();
			List<Product> prods = services.findProduct(text, map);
			for(Product p : prods)
				productsModel.addElement(p);
			
			
		} catch (Exception e) {
			logger.error("error searching",e);
			JOptionPane.showMessageDialog(this, e.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
		} 
		
	}

	protected void search(int id) {
		ProductServices services = new ProductServices();
		try {
			productsModel.removeAllElements();
			Product p = services.getProductById(id);
			productsModel.addElement(p);
			
		} catch (Exception e) {
			logger.error(e);
			JOptionPane.showMessageDialog(this, e.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
		} 
		
	}

	public MkmSearchPanel() {
		initGUI();
			
		try {
			label.setText("Connected as " + MkmAPIConfig.getInstance().getAuthenticator().getAuthenticatedUser());
			
			panelEast = new JPanel();
			add(panelEast, BorderLayout.EAST);
			
			lblPics = new JLabel("");
			panelEast.add(lblPics);
		}  catch (Exception e) {
			label.setText("Not connected");
		}
		
	}

	protected void loadArticle(Product selectedValue) {
		ArticleService service = new ArticleService();
		Map<ARTICLES_ATT, String> atts = new EnumMap<>(ARTICLES_ATT.class);
								atts.put(ARTICLES_ATT.start, "0");
								atts.put(ARTICLES_ATT.maxResults, "100");
		try {
			articlesModel.init(service.find(selectedValue, atts));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
