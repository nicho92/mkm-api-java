package org.mkm.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.api.mkm.modele.User;
import org.api.mkm.services.ArticleService;
import org.api.mkm.services.UserService;
import org.api.mkm.tools.MkmConstants;
import org.mkm.gui.modeles.ArticlesTableModel;
import org.mkm.gui.modeles.UsersTableModel;

public class MkmUsersPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tableUsers;
	private UsersTableModel usersModel;
	private ArticlesTableModel articlesModel;
	private transient Logger logger = LogManager.getLogger(this.getClass());
	private JTable tableArticles;

	private void initGUI()
	{
		setLayout(new BorderLayout(0, 0));
		JTextField txtSearch;
		JLabel lblSearchUser;
		JPanel panelNorth = new JPanel();
		txtSearch = new JTextField(15);
		
		
		add(panelNorth, BorderLayout.NORTH);
		
		txtSearch.addActionListener(ae->search(txtSearch.getText()));
		
		lblSearchUser = new JLabel("Search user : ");
		panelNorth.add(lblSearchUser);
		panelNorth.add(txtSearch);
		
		articlesModel = new ArticlesTableModel();
		usersModel = new UsersTableModel();
		
		tableUsers = new JTable(usersModel);
		tableArticles = new JTable(articlesModel);

		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(new JScrollPane(tableUsers));
		splitPane.setRightComponent(new JScrollPane(tableArticles));
		
		add(splitPane, BorderLayout.CENTER);
		
		
		tableUsers.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Map<ARTICLES_ATT, String> map = new EnumMap<>(ARTICLES_ATT.class);
				User o = (User)tableUsers.getValueAt(tableUsers.getSelectedRow(), 0);
				
				map.put(ARTICLES_ATT.start, "0");
				map.put(ARTICLES_ATT.maxResults, "100");
				
				try {
					List<Article> artts = new ArticleService().find(o,map);
					articlesModel.init(artts);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
	}
	
	
	protected void search(String text) {
		UserService services = new UserService();
		
		try {
			usersModel.init(services.findUsers(text));
			
			
		} catch (Exception e) {
			logger.error("error searching",e);
			JOptionPane.showMessageDialog(this, e.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
		} 
		
	}

	public MkmUsersPanel() {
		initGUI();
			
	
		
	}

}
