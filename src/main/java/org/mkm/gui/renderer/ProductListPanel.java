package org.mkm.gui.renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.api.mkm.modele.Product;

public class ProductListPanel extends JPanel{
	
	Product p;
	
	
	public ProductListPanel(Product p) {
		this.p=p;
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 230, 106, 0};
		gridBagLayout.rowHeights = new int[]{14, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblName = new JLabel(p.getEnName());
		lblName.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbclblName = new GridBagConstraints();
		gbclblName.insets = new Insets(0, 0, 5, 5);
		gbclblName.anchor = GridBagConstraints.NORTHWEST;
		gbclblName.gridx = 1;
		gbclblName.gridy = 0;
		add(lblName, gbclblName);
		
		JLabel lblEdition = new JLabel(p.getExpansionName());
		GridBagConstraints gbclblEdition = new GridBagConstraints();
		gbclblEdition.insets = new Insets(0, 0, 5, 0);
		gbclblEdition.gridwidth = 2;
		gbclblEdition.anchor = GridBagConstraints.WEST;
		gbclblEdition.gridx = 1;
		gbclblEdition.gridy = 1;
		add(lblEdition, gbclblEdition);
		
		JLabel lblGame = new JLabel(p.getGameName());
		GridBagConstraints gbclblGame = new GridBagConstraints();
		gbclblGame.insets = new Insets(0, 0, 0, 5);
		gbclblGame.gridx = 1;
		gbclblGame.gridy = 2;
		add(lblGame, gbclblGame);
		
	}	
	
	
}
