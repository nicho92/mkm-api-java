package org.mkm.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.modele.Order;
import org.api.mkm.services.OrderService;
import org.api.mkm.services.OrderService.ACTOR;
import org.api.mkm.services.OrderService.STATE;
import org.mkm.gui.modeles.OrderTableModel;

public class MkmOrderPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<STATE> comboBox;
	private JTable table;
	private OrderTableModel model;
	private transient Logger logger = LogManager.getLogger(this.getClass());

	
	public MkmOrderPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		comboBox = new JComboBox<>(new DefaultComboBoxModel<OrderService.STATE>(OrderService.STATE.values()));
		comboBox.addItemListener(ie->{
				OrderService service = new OrderService();
				try {
					List<Order> orders = service.listOrders(ACTOR.buyer, (STATE)comboBox.getSelectedItem(), null);
					model.init(orders);
				} catch (Exception e) {
					logger.error(e);
				} 
		});
		panel.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		model = new OrderTableModel();
		table = new JTable(model);
		scrollPane.setViewportView(table);
	}
}
