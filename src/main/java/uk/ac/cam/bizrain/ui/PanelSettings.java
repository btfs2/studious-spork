package uk.ac.cam.bizrain.ui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

public class PanelSettings extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7690662687510294733L;

	public interface SettingsReturn {
		public void ret();
	}
	
	/**
	 * Create the panel.
	 */
	public PanelSettings(SettingsReturn ret) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea = new GridBagConstraints();
		gbc_rigidArea.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea.gridx = 0;
		gbc_rigidArea.gridy = 0;
		add(rigidArea, gbc_rigidArea);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut.gridx = 2;
		gbc_horizontalStrut.gridy = 0;
		add(horizontalStrut, gbc_horizontalStrut);
		
		JLabel lblSettings = new JLabel("Settings");
		GridBagConstraints gbc_lblSettings = new GridBagConstraints();
		gbc_lblSettings.insets = new Insets(0, 0, 5, 5);
		gbc_lblSettings.gridx = 1;
		gbc_lblSettings.gridy = 1;
		add(lblSettings, gbc_lblSettings);
		
		JLabel lblCredits = new JLabel("Created by Group17");
		GridBagConstraints gbc_lblCredits = new GridBagConstraints();
		gbc_lblCredits.insets = new Insets(0, 0, 5, 5);
		gbc_lblCredits.gridx = 1;
		gbc_lblCredits.gridy = 3;
		add(lblCredits, gbc_lblCredits);
		
		JLabel lblPoweredByDarksky = new JLabel("Powered by DarkSky");
		GridBagConstraints gbc_lblPoweredByDarksky = new GridBagConstraints();
		gbc_lblPoweredByDarksky.insets = new Insets(0, 0, 5, 5);
		gbc_lblPoweredByDarksky.gridx = 1;
		gbc_lblPoweredByDarksky.gridy = 4;
		add(lblPoweredByDarksky, gbc_lblPoweredByDarksky);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 5;
		add(verticalStrut, gbc_verticalStrut);

	}

}
