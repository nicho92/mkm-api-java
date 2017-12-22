package org.mkm.gui;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.api.mkm.modele.Order;
import org.api.mkm.services.OrderService;
import org.api.mkm.services.OrderService.ACTOR;
import org.api.mkm.services.OrderService.STATE;
import org.mkm.gui.modeles.OrderTableModel;

public class MkmOrderPanel extends JPanel{
	JComboBox<STATE> comboBox;
	private JTable table;
	private OrderTableModel model;
	
	
	public MkmOrderPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		comboBox = new JComboBox(new DefaultComboBoxModel<OrderService.STATE>(OrderService.STATE.values()));
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				OrderService service = new OrderService();
				try {
					List<Order> orders = service.listOrders(ACTOR.buyer, (STATE)comboBox.getSelectedItem(), null);
					model.init(orders);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
		panel.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		model = new OrderTableModel();
		table = new JTable(model);
		scrollPane.setViewportView(table);
		// TODO Auto-generated constructor stub
	}
}
