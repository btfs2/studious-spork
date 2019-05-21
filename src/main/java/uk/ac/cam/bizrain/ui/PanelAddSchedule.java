package uk.ac.cam.bizrain.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import uk.ac.cam.bizrain.Bizrain;
import uk.ac.cam.bizrain.ui.comp.RoundedBorder;
import uk.ac.cam.bizrain.ui.comp.SwingUtil;

/**
 * Panel that adds schedules
 * 
 * Contains name entry box, and add button, and a back button
 * 
 * @author btfs2, Paulina (docs)
 *
 */
public class PanelAddSchedule extends JPanel {
	
	/**
	 * Deals with serialisation warning 
	 */
	private static final long serialVersionUID = 8783712046635389519L;
	
	/**
	 * Text field used for containing name
	 */
	private JTextField textField;

	/**
	 * Called when new schedule is to be added
	 * 
	 * @author btfs2
	 *
	 */
	interface AddSchedReturn {
		public void ret(String name);
	}
	
	/**
	 * Called when canceling
	 * 
	 * @author btfs2
	 *
	 */
	interface AddSchedBack {
		public void back();
	}
	
	/**
	 * Create the panel.
	 * 
	 * Sets up swing and stuff
	 */
	public PanelAddSchedule(Bizrain br ,AddSchedBack back, AddSchedReturn ret) {
		
		//View
		//////
		
		//Layout setup
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 21, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		//Window padding
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
		
		//Title 
		JLabel lblNewSchedule = new JLabel("New Schedule");
		lblNewSchedule.setFont(SwingUtil.getFontTitle().deriveFont(20f).deriveFont(Font.BOLD));
		GridBagConstraints gbc_lblNewSchedule = new GridBagConstraints();
		gbc_lblNewSchedule.gridwidth = 3;
		gbc_lblNewSchedule.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewSchedule.gridx = 1;
		gbc_lblNewSchedule.gridy = 1;
		add(lblNewSchedule, gbc_lblNewSchedule);
		
		//Padding
		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_4 = new GridBagConstraints();
		gbc_rigidArea_4.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_4.gridx = 0;
		gbc_rigidArea_4.gridy = 2;
		add(rigidArea_4, gbc_rigidArea_4);
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_5 = new GridBagConstraints();
		gbc_rigidArea_5.insets = new Insets(0, 0, 5, 0);
		gbc_rigidArea_5.gridx = 4;
		gbc_rigidArea_5.gridy = 2;
		add(rigidArea_5, gbc_rigidArea_5);
		
		//Label indicating box
		//Spaces are to ensure alignment
		JLabel lblName = new JLabel("     Name:");
		lblName.setFont(SwingUtil.getFontSub().deriveFont(12f).deriveFont(Font.BOLD));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.gridwidth = 3;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 3;
		add(lblName, gbc_lblName);
		
		//Main textbox
		textField = new JTextField();
		textField.setBorder(new RoundedBorder(30));
		textField.setBackground(Color.WHITE);
		textField.setFont(SwingUtil.getFontNum().deriveFont(12f).deriveFont(Font.BOLD));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 4;
		add(textField, gbc_textField);
		textField.setColumns(10);
		
		//Warning prompt for user
		JLabel lblError = new JLabel("No name entered");
		lblError.setFont(SwingUtil.getFontTitle().deriveFont(15f).deriveFont(Font.BOLD));
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.gridwidth = 3;
		gbc_lblError.insets = new Insets(0, 0, 5, 5);
		gbc_lblError.gridx = 1;
		gbc_lblError.gridy = 6;
		add(lblError, gbc_lblError);
		lblError.setVisible(false);
		
		//Button panel to make alignment easier
		JPanel panelButtons = new JPanel();
		GridBagConstraints gbc_panelButtons = new GridBagConstraints();
		gbc_panelButtons.gridwidth = 3;
		gbc_panelButtons.insets = new Insets(0, 0, 5, 5);
		gbc_panelButtons.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelButtons.gridx = 1;
		gbc_panelButtons.gridy = 8;
		add(panelButtons, gbc_panelButtons);
		GridBagLayout gbl_panelButtons = new GridBagLayout();
		gbl_panelButtons.columnWidths = new int[]{0, 0, 0};
		gbl_panelButtons.rowHeights = new int[]{0, 0};
		gbl_panelButtons.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelButtons.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelButtons.setLayout(gbl_panelButtons);
		
		JButton btnBack = new JButton("");
		btnBack.setBorder(new RoundedBorder(30));
		btnBack.setBackground(Color.WHITE);
		btnBack.setIcon(new ImageIcon(PanelAddSchedule.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-chevron-left-16.png")));
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 0;
		gbc_btnBack.gridy = 0;
		panelButtons.add(btnBack, gbc_btnBack);
		
		JButton btnAdd = new JButton("");
		btnAdd.setBorder(new RoundedBorder(30));
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setIcon(new ImageIcon(PanelAddSchedule.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-plus-16.png")));
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.gridx = 1;
		gbc_btnAdd.gridy = 0;
		panelButtons.add(btnAdd, gbc_btnAdd);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_2 = new GridBagConstraints();
		gbc_rigidArea_2.insets = new Insets(0, 0, 0, 5);
		gbc_rigidArea_2.gridx = 0;
		gbc_rigidArea_2.gridy = 9;
		add(rigidArea_2, gbc_rigidArea_2);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_3 = new GridBagConstraints();
		gbc_rigidArea_3.gridx = 4;
		gbc_rigidArea_3.gridy = 9;
		add(rigidArea_3, gbc_rigidArea_3);

		// CONTROLLER
		/////////////
		
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ret.ret(textField.getText());
			}
		});
		
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				back.back();
			}
		});
		
		//Entry validator to ensure you don't get schedules with no name
		textField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				validate();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				validate();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				validate();
			}
			
			public void validate() {
				if (textField.getText() == null || textField.getText().equals("")) {
					btnAdd.setEnabled(false);
					lblError.setVisible(true);
				} else {
					btnAdd.setEnabled(true);
					lblError.setVisible(false);
				}
			}
		});
		
		
		//TODO DEDUPE
		if (textField.getText() == null || textField.getText().equals("")) {
			btnAdd.setEnabled(false);
			lblError.setVisible(true);
		} else {
			btnAdd.setEnabled(true);
			lblError.setVisible(false);
		}
		
	}

}
