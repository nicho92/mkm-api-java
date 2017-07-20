package org.mkm.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.apache.commons.beanutils.BeanUtils;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.WantItem;
import org.api.mkm.modele.Wantslist;
import org.api.mkm.services.ArticleService;
import org.api.mkm.services.WantsService;
import org.mkm.gui.modeles.ArticlesTableModel;
import org.mkm.gui.modeles.WantListTableModel;

public class MkmWantListPanel extends JPanel {
	private JPanel PanelSouth;
	private JScrollPane panelWest;
	private JList<Wantslist> listResults;
	private JScrollPane panelCenter;
	private JTable tableItemWl;
	private DefaultListModel<Wantslist> wantListModel;
	private WantListTableModel itemsTableModel;
	private JButton btnLoadWantlist;
	private JPanel centerpanel;
	private JButton btnDelete;
	private Wantslist selected;
	
	WantsService serviceW = new WantsService();
	ArticleService serviceA = new ArticleService();
	private JButton btnEditItem;
	private JButton btnRenameWl;
	private JButton btnCreateWl;
	private JButton btnDeleteWl;
	
	
	
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
		
		btnDelete = new JButton("Delete Item");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					WantItem it = (WantItem)itemsTableModel.getValueAt(tableItemWl.getSelectedRow(), 0);
					selected = serviceW.deleteItem(selected, it);
					itemsTableModel.init(selected);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		btnRenameWl = new JButton("Rename WL");
		btnRenameWl.setEnabled(false);
		btnRenameWl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String res =JOptionPane.showInputDialog("New Name ?",selected.toString());
				try {
					serviceW.renameWantList(selected, res);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		btnCreateWl = new JButton("Create WL");
		btnCreateWl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = JOptionPane.showInputDialog("Name ?");
				try {
					Wantslist l = serviceW.createWantList(name);
					wantListModel.addElement(l);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panelNorth.add(btnCreateWl);
		
		btnDeleteWl = new JButton("Delete WL");
		btnDeleteWl.setEnabled(false);
		btnDeleteWl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selected==null)
				{
					JOptionPane.showMessageDialog(null, "Need to select a WantList","ERROR",JOptionPane.ERROR_MESSAGE);
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
								JOptionPane.showMessageDialog(null, e1.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
							} 
						}
				}
				
			}
		});
		panelNorth.add(btnDeleteWl);
		panelNorth.add(btnRenameWl);
		panelNorth.add(btnDelete);
		
		btnEditItem = new JButton("Edit");
		btnEditItem.setEnabled(false);
		btnEditItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
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
					JOptionPane.showMessageDialog(null, e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		panelNorth.add(btnEditItem);
		
		PanelSouth = new JPanel();
		add(PanelSouth, BorderLayout.SOUTH);
		
		panelWest = new JScrollPane();
		add(panelWest, BorderLayout.WEST);
		wantListModel = new DefaultListModel<Wantslist>();
		listResults = new JList<Wantslist>(wantListModel);
		listResults.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selected = listResults.getSelectedValue();
				loadWantList(selected);
			}
		});
		panelWest.setViewportView(listResults);
		
		itemsTableModel = new WantListTableModel();
		
		centerpanel = new JPanel();
		centerpanel.setLayout(new BorderLayout());
		add(centerpanel, BorderLayout.CENTER);
		
		panelCenter = new JScrollPane();
		
		centerpanel.add(panelCenter);
		tableItemWl = new JTable(itemsTableModel);
		tableItemWl.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				btnEditItem.setEnabled(true);
				
			}
		});
		
		panelCenter.setViewportView(tableItemWl);
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
			JOptionPane.showMessageDialog(this, e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
		
		}
		
	}


	public MkmWantListPanel() {
		initGUI();
	
	}

	protected void loadWantList(final Wantslist selectedValue) {
		
		new Thread(new Runnable() {
			public void run() {
				try {
					serviceW.loadItems(selectedValue);
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
