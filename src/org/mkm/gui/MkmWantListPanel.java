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
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.api.mkm.modele.Product;
import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.api.mkm.modele.Product.PRODUCT_ATTS;
import org.api.mkm.modele.Wantslist;
import org.api.mkm.services.ArticleService;
import org.api.mkm.services.AuthenticationServices;
import org.api.mkm.services.ProductServices;
import org.api.mkm.services.WantsService;
import org.api.mkm.tools.MkmAPIConfig;
import org.magic.api.pricers.impl.MagicCardMarketPricer2;
import org.mkm.gui.modeles.ArticlesTableModel;
import org.mkm.gui.modeles.WantListTableModel;

import javax.swing.JButton;

public class MkmWantListPanel extends JPanel {
	private JPanel PanelSouth;
	private JScrollPane panelWest;
	private JList<Wantslist> listResults;
	private JScrollPane panelCenter;
	private JTable tableItemWl;
	private JTable tableArticles;
	private DefaultListModel<Wantslist> wantListModel;
	private WantListTableModel itemsTableModel;
	private ArticlesTableModel articlesTableModel;
	private JButton btnLoadWantlist;
	private JSplitPane splitCenterPanel;
	private JScrollPane scrollPane;
	
	private void initGUI()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		add(panelNorth, BorderLayout.NORTH);
		
		btnLoadWantlist = new JButton("Load WantList");
		btnLoadWantlist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadWantList();
			}
		});
		panelNorth.add(btnLoadWantlist);
		
		PanelSouth = new JPanel();
		add(PanelSouth, BorderLayout.SOUTH);
		
		panelWest = new JScrollPane();
		add(panelWest, BorderLayout.WEST);
		wantListModel = new DefaultListModel<Wantslist>();
		listResults = new JList<Wantslist>(wantListModel);
		listResults.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				loadWantList(listResults.getSelectedValue());
			}
		});
		panelWest.setViewportView(listResults);
		
		itemsTableModel = new WantListTableModel();
		
		splitCenterPanel = new JSplitPane();
		splitCenterPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitCenterPanel, BorderLayout.CENTER);
		
		panelCenter = new JScrollPane();
		
		splitCenterPanel.setLeftComponent(panelCenter);
		tableItemWl = new JTable(itemsTableModel);
		tableItemWl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				loadArticle((Product)itemsTableModel.getValueAt(tableItemWl.getSelectedRow(), 0));
			}
		});
		panelCenter.setViewportView(tableItemWl);
		
		scrollPane = new JScrollPane();
		splitCenterPanel.setRightComponent(scrollPane);
		articlesTableModel = new ArticlesTableModel();
		tableArticles = new JTable(articlesTableModel);
		scrollPane.setViewportView(tableArticles);
	}


	protected void loadArticle(Product valueAt) {
		ArticleService service = new ArticleService();
		Map<ARTICLES_ATT, String> atts = new HashMap<ARTICLES_ATT, String>();
								atts.put(ARTICLES_ATT.start, "0");
								atts.put(ARTICLES_ATT.maxResults, "100");
		try {
			articlesTableModel.init(service.find(valueAt, atts));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
		}
		
	}


	protected void loadWantList() {
		WantsService service = new WantsService();
		List<Wantslist> lists;
		try {
			lists = service.getWantList();

			for(Wantslist l : lists)
				wantListModel.addElement(l);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
		
		}
		
	}


	public MkmWantListPanel() {
		initGUI();
	
	}

	protected void loadWantList(final Wantslist selectedValue) {
		new Thread(new Runnable() {
			public void run() {
				WantsService service = new WantsService();
				try {
					service.loadItems(selectedValue);
					itemsTableModel.init(selectedValue);
				}
				catch (Exception e) 
				{
					JOptionPane.showMessageDialog(null, e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}
		}).start();
	}
}
