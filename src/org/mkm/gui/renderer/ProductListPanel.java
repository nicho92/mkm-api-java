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
		
		/*
		lblNewLabel = new DisplayableCard(card,new Dimension(77, 107),false);
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.gridheight = 4;
				gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel.gridx = 0;
				gbc_lblNewLabel.gridy = 0;
				add(lblNewLabel, gbc_lblNewLabel);
		*/
		JLabel lblName = new JLabel(p.getEnName());
		lblName.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 0;
		add(lblName, gbc_lblName);
		
		JLabel lblEdition = new JLabel(p.getExpansionName());
		GridBagConstraints gbc_lblEdition = new GridBagConstraints();
		gbc_lblEdition.insets = new Insets(0, 0, 5, 0);
		gbc_lblEdition.gridwidth = 2;
		gbc_lblEdition.anchor = GridBagConstraints.WEST;
		gbc_lblEdition.gridx = 1;
		gbc_lblEdition.gridy = 1;
		add(lblEdition, gbc_lblEdition);
		
		JLabel lblGame = new JLabel(p.getGameName());
		GridBagConstraints gbc_lblGame = new GridBagConstraints();
		gbc_lblGame.insets = new Insets(0, 0, 0, 5);
		gbc_lblGame.gridx = 1;
		gbc_lblGame.gridy = 2;
		add(lblGame, gbc_lblGame);
		
		//lblEdition.setIcon();
	
	}	
	
	
}
