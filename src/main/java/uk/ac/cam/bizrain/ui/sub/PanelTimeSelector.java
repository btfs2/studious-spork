package uk.ac.cam.bizrain.ui.sub;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.cam.bizrain.ui.comp.JTimeSelect;
import uk.ac.cam.bizrain.ui.comp.RoundedBorder;
import uk.ac.cam.bizrain.ui.comp.SwingUtil;

/**
 * Main window panel for selecting time
 * 
 * Just gets time from user and returns it
 * 
 * @author btfs2
 *
 */
public class PanelTimeSelector extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1455428906071148231L;

	/**
	 * Return function for this thing
	 * 
	 * @author btfs2
	 */
	public interface TimeSelectorReturn {
		/**
		 * Called when returning
		 * 
		 * @param time
		 */
		public void returnData(LocalTime time);
	}

	/**
	 * Create the panel for selecting time
	 */
	public PanelTimeSelector(LocalTime start, TimeSelectorReturn ret) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 25, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 4.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea = new GridBagConstraints();
		gbc_rigidArea.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea.gridx = 0;
		gbc_rigidArea.gridy = 0;
		add(rigidArea, gbc_rigidArea);

		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_1 = new GridBagConstraints();
		gbc_rigidArea_1.insets = new Insets(0, 0, 5, 0);
		gbc_rigidArea_1.gridx = 4;
		gbc_rigidArea_1.gridy = 0;
		add(rigidArea_1, gbc_rigidArea_1);

		JLabel lblEnterTime = new JLabel("Enter Time");
		lblEnterTime.setFont(SwingUtil.getFontTitle().deriveFont(20f).deriveFont(Font.BOLD));
		lblEnterTime.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_lblEnterTime = new GridBagConstraints();
		gbc_lblEnterTime.gridwidth = 3;
		gbc_lblEnterTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterTime.gridx = 1;
		gbc_lblEnterTime.gridy = 1;
		add(lblEnterTime, gbc_lblEnterTime);

		Component rigidArea_6 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_6 = new GridBagConstraints();
		gbc_rigidArea_6.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_6.gridx = 0;
		gbc_rigidArea_6.gridy = 2;
		add(rigidArea_6, gbc_rigidArea_6);

		Component rigidArea_7 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_7 = new GridBagConstraints();
		gbc_rigidArea_7.insets = new Insets(0, 0, 5, 0);
		gbc_rigidArea_7.gridx = 4;
		gbc_rigidArea_7.gridy = 2;
		add(rigidArea_7, gbc_rigidArea_7);

		/*
		 * JComboBox<Integer> cbHour = new JComboBox<Integer>(); cbHour.setBorder(new
		 * RoundedBorder(30)); cbHour.setBackground(Color.WHITE); cbHour.setModel(new
		 * DefaultComboBoxModel<Integer>(new Integer[]
		 * {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23}));
		 * //cbHour.setModel(new IntModel(new Integer[]
		 * {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23}));
		 * cbHour.getModel().setSelectedItem(start.getHour()); GridBagConstraints
		 * gbc_cbHour = new GridBagConstraints(); gbc_cbHour.insets = new Insets(0, 0,
		 * 5, 5); gbc_cbHour.fill = GridBagConstraints.HORIZONTAL; gbc_cbHour.gridx = 1;
		 * gbc_cbHour.gridy = 3; add(cbHour, gbc_cbHour); SwingUtil.fixCbBorder(cbHour);
		 * 
		 * JLabel lblColon = new JLabel(":"); GridBagConstraints gbc_lblColon = new
		 * GridBagConstraints(); gbc_lblColon.insets = new Insets(0, 0, 5, 5);
		 * gbc_lblColon.gridx = 2; gbc_lblColon.gridy = 3; add(lblColon, gbc_lblColon);
		 * 
		 * JComboBox<Integer> cbMinute = new JComboBox<Integer>();
		 * cbMinute.setBorder(new RoundedBorder(30));
		 * cbMinute.setBackground(Color.WHITE); cbMinute.setModel(new
		 * DefaultComboBoxModel<Integer>(new Integer[] {0, 5, 10, 15, 20, 25, 30, 35,
		 * 40, 45, 50, 55})); //cbMinute.setModel(new IntModel(new Integer[] {0, 5, 10,
		 * 15, 20, 25, 30, 35, 40, 45, 50, 55}));
		 * cbMinute.getModel().setSelectedItem((start.getMinute()/5)*5);
		 * GridBagConstraints gbc_cbMinute = new GridBagConstraints();
		 * gbc_cbMinute.insets = new Insets(0, 0, 5, 5); gbc_cbMinute.fill =
		 * GridBagConstraints.HORIZONTAL; gbc_cbMinute.gridx = 3; gbc_cbMinute.gridy =
		 * 3; add(cbMinute, gbc_cbMinute); SwingUtil.fixCbBorder(cbMinute);
		 */

		JTimeSelect timeSelector = new JTimeSelect(null); // TODO FIXME
		GridBagConstraints gbc_timeSelector = new GridBagConstraints();
		gbc_timeSelector.gridwidth = 3;
		gbc_timeSelector.insets = new Insets(0, 0, 5, 5);
		gbc_timeSelector.fill = GridBagConstraints.BOTH;
		gbc_timeSelector.gridx = 1;
		gbc_timeSelector.gridy = 3;
		add(timeSelector, gbc_timeSelector);

		JButton btnOk = new JButton("OK");
		btnOk.setToolTipText("Accept time");
		btnOk.setBorder(new RoundedBorder(30));
		btnOk.setBackground(Color.WHITE);
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// ret.returnData(LocalTime.of((int) cbHour.getModel().getSelectedItem(), (int)
				// cbMinute.getModel().getSelectedItem()));
				ret.returnData(timeSelector.getTime());
			}
		});

		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOk.gridwidth = 3;
		gbc_btnOk.insets = new Insets(0, 0, 5, 5);
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 5;
		add(btnOk, gbc_btnOk);

		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_2 = new GridBagConstraints();
		gbc_rigidArea_2.insets = new Insets(0, 0, 0, 5);
		gbc_rigidArea_2.gridx = 0;
		gbc_rigidArea_2.gridy = 6;
		add(rigidArea_2, gbc_rigidArea_2);

		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_3 = new GridBagConstraints();
		gbc_rigidArea_3.gridx = 4;
		gbc_rigidArea_3.gridy = 6;
		add(rigidArea_3, gbc_rigidArea_3);

	}

}
