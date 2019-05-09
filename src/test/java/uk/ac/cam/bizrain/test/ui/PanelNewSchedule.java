package uk.ac.cam.bizrain.test.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * Auto-Generated by WindowBuilder
 * 
 * PLS NO BRICK
 * 
 * @author btfs2
 *
 */
public class PanelNewSchedule extends JPanel {

	/**
	 * Fixes warnings and serialisation
	 */
	private static final long serialVersionUID = -1139123244037952256L;

	/**
	 * Create the panel.
	 */
	public PanelNewSchedule() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{144, 0};
		gridBagLayout.rowHeights = new int[]{137, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton buttonAdd = new JButton("");
		buttonAdd.setIcon(new ImageIcon(PanelNewSchedule.class.getResource("/uk/ac/cam/bizrain/test/ui/fa-plus-128.png")));
		GridBagConstraints gbc_buttonAdd = new GridBagConstraints();
		gbc_buttonAdd.fill = GridBagConstraints.BOTH;
		gbc_buttonAdd.gridx = 0;
		gbc_buttonAdd.gridy = 0;
		add(buttonAdd, gbc_buttonAdd);

	}

}
