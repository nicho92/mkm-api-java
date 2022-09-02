package org.mkm.gui;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.api.mkm.modele.Order;
import org.api.mkm.services.OrderService;
import org.api.mkm.services.OrderService.ACTOR;
import org.api.mkm.services.OrderService.STATE;
import org.mkm.gui.modeles.LightArticlesTableModel;
import org.mkm.gui.modeles.OrderTableModel;

public class MkmOrderPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<STATE> comboBox;
	private JTable tableOrders;
	private OrderTableModel model;
	private LightArticlesTableModel modelsArticles;
	private transient Logger logger = LogManager.getLogger(this.getClass());
	private JTable tableItems;
	private JSplitPane splitPane;

	
	public MkmOrderPanel() {
		setLayout(new BorderLayout(0, 0));
		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		
		model = new OrderTableModel();
		modelsArticles = new LightArticlesTableModel();

		
		tableOrders= new JTable(model);
		tableItems = new JTable(modelsArticles);
		
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		
		comboBox = new JComboBox<>(new DefaultComboBoxModel<>(OrderService.STATE.values()));
		comboBox.addItemListener(ie->{
			
			if(ie.getStateChange() == ItemEvent.SELECTED){
			
				OrderService service = new OrderService();
				try {
					List<Order> orders = service.listOrders(ACTOR.buyer, (STATE)comboBox.getSelectedItem(), null);
					model.init(orders);
				} catch (Exception e) {
					logger.error(e);
				} 
			}
		});
		panel.add(comboBox);
		
		
		tableOrders.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Order o = (Order)tableOrders.getValueAt(tableOrders.getSelectedRow(), 0);
				modelsArticles.init(o.getArticle());
				
				
			}
		});
		splitPane.setLeftComponent(new JScrollPane(tableOrders));
		splitPane.setRightComponent(new JScrollPane(tableItems));
		
		add(splitPane, BorderLayout.CENTER);
		
	}
}
