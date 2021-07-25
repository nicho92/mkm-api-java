package org.mkm.gui.renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.modele.Product;

public class ProductListPanel extends JPanel{
	private transient Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Product p;
	
	
	public ProductListPanel(Product p) {
		this.p=p;
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{77, 230, 0};
		gridBagLayout.rowHeights = new int[]{14, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblPics = new JLabel();
		
		try {
			var img = ImageIO.read(new URL("https:"+p.getImage()));
			lblPics.setIcon(new ImageIcon(img.getScaledInstance(85, 118, Image.SCALE_SMOOTH)));
		} catch (Exception e) {
			logger.error(e);
		}
		
		GridBagConstraints gbclblPics = new GridBagConstraints();
		gbclblPics.gridheight = 3;
		gbclblPics.insets = new Insets(0, 0, 5, 5);
		gbclblPics.gridx = 0;
		gbclblPics.gridy = 1;
		add(lblPics, gbclblPics);
		
		JLabel lblName = new JLabel(p.getEnName());
		lblName.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbclblName = new GridBagConstraints();
		gbclblName.gridwidth = 2;
		gbclblName.insets = new Insets(0, 0, 5, 0);
		gbclblName.anchor = GridBagConstraints.NORTHWEST;
		gbclblName.gridx = 0;
		gbclblName.gridy = 0;
		add(lblName, gbclblName);
		
		JLabel lblEdition = new JLabel(p.getExpansionName());
		GridBagConstraints gbclblEdition = new GridBagConstraints();
		gbclblEdition.insets = new Insets(0, 0, 5, 0);
		gbclblEdition.anchor = GridBagConstraints.WEST;
		gbclblEdition.gridx = 1;
		gbclblEdition.gridy = 1;
		add(lblEdition, gbclblEdition);
		
		JLabel lblIdProduct = new JLabel("ID "+p.getIdProduct());
		GridBagConstraints gbclblidProduct = new GridBagConstraints();
		gbclblidProduct.anchor = GridBagConstraints.WEST;
		gbclblidProduct.insets = new Insets(0, 0, 5, 0);
		gbclblidProduct.gridx = 1;
		gbclblidProduct.gridy = 2;
		add(lblIdProduct, gbclblidProduct);
		
		JLabel lblType = new JLabel(p.getCategoryName());
		GridBagConstraints gbclblType = new GridBagConstraints();
		gbclblType.anchor = GridBagConstraints.WEST;
		gbclblType.gridx = 1;
		gbclblType.gridy = 3;
		add(lblType, gbclblType);
		
	}


	
}
