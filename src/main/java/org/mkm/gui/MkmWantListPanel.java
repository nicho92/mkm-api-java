package org.mkm.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.modele.WantItem;
import org.api.mkm.modele.Wantslist;
import org.api.mkm.services.ArticleService;
import org.api.mkm.services.WantsService;
import org.api.mkm.tools.MkmConstants;
import org.mkm.gui.modeles.ArticlesTableModel;
import org.mkm.gui.modeles.WantListTableModel;

public class MkmWantListPanel extends JPanel {
	private JList<Wantslist> listResults;
	private JTable tableItemWl;
	private DefaultListModel<Wantslist> wantListModel;
	private WantListTableModel itemsTableModel;
	private ArticlesTableModel articlesTableModel;
	private Wantslist selected;
	
	private transient WantsService serviceW;
	private transient ArticleService serviceA;
	private JButton btnEditItem;
	private JButton btnRenameWl;
	private JButton btnDeleteWl;
	
	private transient Logger logger = LogManager.getLogger(this.getClass());

	private void initGUI()
	{
		serviceW = new WantsService();
		serviceA = new ArticleService();
		
		JScrollPane scrollitems;
		JPanel panelSouth;
		JScrollPane panelWest;
		JButton btnLoadWantlist;
		JSplitPane rightPanel;
		JButton btnDelete;
		JScrollPane scrollArticles;
		JTable tableArticles;
		JButton btnCreateWl;
		

		setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		add(panelNorth, BorderLayout.NORTH);
		
		btnLoadWantlist = new JButton("Load WantList");
		btnLoadWantlist.addActionListener(ae->loadWantList());
		panelNorth.add(btnLoadWantlist);
		
		btnDelete = new JButton("Delete Item");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(ae->{
				try {
					WantItem it = (WantItem)itemsTableModel.getValueAt(tableItemWl.getSelectedRow(), 0);
					selected = serviceW.deleteItem(selected, it);
					itemsTableModel.init(selected);
				} catch (Exception e) {
					logger.error(e);
					JOptionPane.showMessageDialog(null, e.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
				}
		});
		
		btnRenameWl = new JButton("Rename WL");
		btnRenameWl.setEnabled(false);
		btnRenameWl.addActionListener(ae->{
				String res =JOptionPane.showInputDialog("New Name ?",selected.toString());
				try {
					serviceW.renameWantList(selected, res);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
				}
		});
		
		btnCreateWl = new JButton("Create WL");
		btnCreateWl.addActionListener(ae->{
				String name = JOptionPane.showInputDialog("Name ?");
				try {
					Wantslist l = serviceW.createWantList(name);
					wantListModel.addElement(l);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
				}
		});
		panelNorth.add(btnCreateWl);
		
		btnDeleteWl = new JButton("Delete WL");
		btnDeleteWl.setEnabled(false);
		btnDeleteWl.addActionListener(ae->{
				if(selected==null)
				{
					JOptionPane.showMessageDialog(null, "Need to select a WantList",MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					int res = JOptionPane.showConfirmDialog(null, "delete " + selected + " ?","Confirmation",JOptionPane.YES_OPTION);
					
					if(res==JOptionPane.YES_OPTION)
						{
							try {
								serviceW.deleteWantList(selected);
								wantListModel.removeElement(selected);
								selected=null;

							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
							} 
						}
				}
		});
		panelNorth.add(btnDeleteWl);
		panelNorth.add(btnRenameWl);
		panelNorth.add(btnDelete);
		
		btnEditItem = new JButton("Edit");
		btnEditItem.setEnabled(false);
		btnEditItem.addActionListener(ae->{
				try {
					WantItem it = (WantItem)itemsTableModel.getValueAt(tableItemWl.getSelectedRow(), 0);
					WantListItemEditorPanel dialog = new WantListItemEditorPanel(it);
					dialog.setVisible(true);
					if(dialog.getItem()!=null)
					{
						it = dialog.getItem();
						serviceW.updateItem(selected, it);
						loadWantList(selected);
					}
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
				}
		});
		panelNorth.add(btnEditItem);
		
		panelSouth = new JPanel();
		add(panelSouth, BorderLayout.SOUTH);
		
		panelWest = new JScrollPane();
		add(panelWest, BorderLayout.WEST);
		wantListModel = new DefaultListModel<>();
		listResults = new JList<>(wantListModel);
		listResults.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				selected = listResults.getSelectedValue();
				loadWantList(selected);
			}
		});
		panelWest.setViewportView(listResults);
		
		itemsTableModel = new WantListTableModel();
		articlesTableModel = new ArticlesTableModel();
		
		
		rightPanel = new JSplitPane();
		rightPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(rightPanel, BorderLayout.CENTER);
		
		scrollitems = new JScrollPane();
		
		rightPanel.setLeftComponent(scrollitems);
		tableItemWl = new JTable(itemsTableModel);
		tableItemWl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				btnEditItem.setEnabled(true);
				WantItem it = (WantItem)itemsTableModel.getValueAt(tableItemWl.rowAtPoint(me.getPoint()), 0);
				try {
					articlesTableModel.init(serviceA.find(it.getProduct(), null));
				} catch (Exception e) {
					logger.error(e);
				} 
			}
		});
		
		scrollitems.setViewportView(tableItemWl);
		
		scrollArticles = new JScrollPane();
		rightPanel.setRightComponent(scrollArticles);
		
		tableArticles = new JTable(articlesTableModel);
		scrollArticles.setViewportView(tableArticles);
	}

	protected void loadWantList() {
			List<Wantslist> lists;
		try {
			lists = serviceW.getWantList();

			for(Wantslist l : lists)
				wantListModel.addElement(l);
			
			btnDeleteWl.setEnabled(true);
			btnRenameWl.setEnabled(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
		
		}
		
	}


	public MkmWantListPanel() {
		initGUI();
	
	}

	protected void loadWantList(final Wantslist selectedValue) {
		
		new Thread(()->{
				try {
					serviceW.loadItems(selectedValue);
					itemsTableModel.init(selectedValue);
				}
				catch (Exception e) 
				{
					JOptionPane.showMessageDialog(null, e.getMessage(),MkmConstants.MKM_ERROR,JOptionPane.ERROR_MESSAGE);
				}
		}).start();
	}
}
