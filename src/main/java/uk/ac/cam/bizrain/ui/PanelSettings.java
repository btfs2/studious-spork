package uk.ac.cam.bizrain.ui;

import javax.swing.JPanel;

import uk.ac.cam.bizrain.config.BizrainConfig;
import uk.ac.cam.bizrain.ui.comp.RoundedBorder;
import uk.ac.cam.bizrain.ui.comp.SwingUtil;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.time.ZoneId;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelSettings extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7690662687510294733L;

	public interface SettingsReturn {
		public void ret();
	}

	/**
	 * Create the panel for settings
	 */
	public PanelSettings(SettingsReturn ret) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		gbc_horizontalStrut.gridx = 3;
		gbc_horizontalStrut.gridy = 0;
		add(horizontalStrut, gbc_horizontalStrut);

		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(SwingUtil.getFontTitle().deriveFont(20f).deriveFont(Font.BOLD));
		GridBagConstraints gbc_lblSettings = new GridBagConstraints();
		gbc_lblSettings.gridwidth = 2;
		gbc_lblSettings.insets = new Insets(0, 0, 5, 0);
		gbc_lblSettings.gridx = 2;
		gbc_lblSettings.gridy = 1;
		add(lblSettings, gbc_lblSettings);

		JLabel lblTimeZone = new JLabel("Time Zone: ");
		lblTimeZone.setFont(SwingUtil.getFontSub().deriveFont(15f).deriveFont(Font.BOLD));
		GridBagConstraints gbc_lblTimeZone = new GridBagConstraints();
		gbc_lblTimeZone.anchor = GridBagConstraints.EAST;
		gbc_lblTimeZone.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeZone.gridx = 1;
		gbc_lblTimeZone.gridy = 3;
		add(lblTimeZone, gbc_lblTimeZone);

		JComboBox<String> comboBox = new JComboBox<String>();
		DefaultComboBoxModel<String> dcbm = new DefaultComboBoxModel<String>();
		Set<String> ids = ZoneId.getAvailableZoneIds();
		for (String i : ids) {
			dcbm.addElement(i);
		}
		dcbm.setSelectedItem(BizrainConfig.INSTANCE.timeZoneId);
		comboBox.setModel(dcbm);
		comboBox.setBorder(new RoundedBorder(10));
		comboBox.setBackground(Color.WHITE);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 3;
		add(comboBox, gbc_comboBox);
		SwingUtil.fixCbBorder(comboBox);

		JLabel lblCredits = new JLabel("Created by Group17");
		lblCredits.setFont(SwingUtil.getFontSub().deriveFont(15f).deriveFont(Font.BOLD));
		GridBagConstraints gbc_lblCredits = new GridBagConstraints();
		gbc_lblCredits.gridwidth = 2;
		gbc_lblCredits.insets = new Insets(0, 0, 5, 5);
		gbc_lblCredits.gridx = 1;
		gbc_lblCredits.gridy = 5;
		add(lblCredits, gbc_lblCredits);

		JLabel lblPoweredByDarksky = new JLabel("Powered by DarkSky");
		lblPoweredByDarksky.setFont(SwingUtil.getFontSub().deriveFont(15f).deriveFont(Font.BOLD));
		GridBagConstraints gbc_lblPoweredByDarksky = new GridBagConstraints();
		gbc_lblPoweredByDarksky.gridwidth = 2;
		gbc_lblPoweredByDarksky.insets = new Insets(0, 0, 5, 5);
		gbc_lblPoweredByDarksky.gridx = 1;
		gbc_lblPoweredByDarksky.gridy = 6;
		add(lblPoweredByDarksky, gbc_lblPoweredByDarksky);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 7;
		add(verticalStrut, gbc_verticalStrut);

		JButton btnBack = new JButton("");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BizrainConfig.INSTANCE.timeZoneId = (String) comboBox.getModel().getSelectedItem();
				BizrainConfig.saveConfig();
				ret.ret();
			}
		});
		btnBack.setBorder(new RoundedBorder(20));
		btnBack.setBackground(Color.WHITE);
		btnBack.setIcon(
				new ImageIcon(PanelSettings.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-chevron-left-16.png")));
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.anchor = GridBagConstraints.WEST;
		gbc_btnBack.insets = new Insets(0, 0, 5, 5);
		gbc_btnBack.gridx = 1;
		gbc_btnBack.gridy = 1;
		add(btnBack, gbc_btnBack);
	}

}
