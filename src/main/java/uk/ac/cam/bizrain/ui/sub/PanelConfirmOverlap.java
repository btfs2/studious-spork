package uk.ac.cam.bizrain.ui.sub;

import javax.swing.JPanel;

import uk.ac.cam.bizrain.ui.comp.RoundedBorder;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * Panel that propts to confirm overlap
 * 
 * Ideally it would be bundled with confirm delete
 * 
 * But that would be effort when you can just duplicate code
 * 
 * @author btfs2, Paulina (docs)
 *
 */
public class PanelConfirmOverlap extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7777377584656583227L;

	/**
	 * Return CB
	 * 
	 * @author btfs2
	 *
	 */
	public interface ConfirmOverlapReturn {
		public void ret(boolean accept);
	}
	
	/**
	 * Create the panel.
	 */
	public PanelConfirmOverlap(ConfirmOverlapReturn cdr) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 2.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		JLabel lblConfirmDelete = new JLabel("Confirm Overlap");
		lblConfirmDelete.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_lblConfirmDelete = new GridBagConstraints();
		gbc_lblConfirmDelete.gridwidth = 2;
		gbc_lblConfirmDelete.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmDelete.gridx = 1;
		gbc_lblConfirmDelete.gridy = 1;
		add(lblConfirmDelete, gbc_lblConfirmDelete);
		
		JLabel lblMessage = new JLabel("Adding this item will create an overlap in your schedule");
		lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblMessage = new GridBagConstraints();
		gbc_lblMessage.gridwidth = 2;
		gbc_lblMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblMessage.gridx = 1;
		gbc_lblMessage.gridy = 3;
		add(lblMessage, gbc_lblMessage);
		
		JLabel lblThisActionCannot = new JLabel("This may have occured due to misselecting the times");
		lblThisActionCannot.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblThisActionCannot = new GridBagConstraints();
		gbc_lblThisActionCannot.gridwidth = 2;
		gbc_lblThisActionCannot.insets = new Insets(0, 0, 5, 5);
		gbc_lblThisActionCannot.gridx = 1;
		gbc_lblThisActionCannot.gridy = 4;
		add(lblThisActionCannot, gbc_lblThisActionCannot);
		
		JButton btnBack = new JButton("Back");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cdr.ret(false);
			}
		});
		btnBack.setIcon(new ImageIcon(PanelConfirmOverlap.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-chevron-left-16.png")));
		btnBack.setBackground(Color.WHITE);
		btnBack.setBorder(new RoundedBorder(30));
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 5, 5);
		gbc_btnBack.gridx = 1;
		gbc_btnBack.gridy = 6;
		add(btnBack, gbc_btnBack);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cdr.ret(true);
			}
		});
		btnConfirm.setIcon(new ImageIcon(PanelConfirmOverlap.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-plus-16.png")));
		btnConfirm.setBackground(Color.WHITE);
		btnConfirm.setBorder(new RoundedBorder(30));
		GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
		gbc_btnConfirm.insets = new Insets(0, 0, 5, 5);
		gbc_btnConfirm.gridx = 2;
		gbc_btnConfirm.gridy = 6;
		add(btnConfirm, gbc_btnConfirm);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 7;
		add(verticalStrut, gbc_verticalStrut);

	}

}
