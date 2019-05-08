package uk.ac.cam.bizrain.ui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.ScrollPaneConstants;

import uk.ac.cam.bizrain.schedule.Schedule;
import uk.ac.cam.bizrain.schedule.Schedule.ScheduleItem;
import uk.ac.cam.bizrain.ui.comp.RoundedBorder;

public class PanelSchedule extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8141445800963049174L;

	/**
	 * Create the panel.
	 */
	public PanelSchedule(Bizrain br, Schedule sch) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 150, 0, 21, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 5));
		GridBagConstraints gbc_rigidArea = new GridBagConstraints();
		gbc_rigidArea.gridwidth = 2;
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
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_1.gridx = 0;
		gbc_horizontalStrut_1.gridy = 1;
		add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		JButton btnSchedules = new JButton("Schedules");
		btnSchedules.setBorder(new RoundedBorder(10));
		btnSchedules.setBackground(Color.WHITE);
		btnSchedules.setIcon(new ImageIcon(PanelSchedule.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-chevron-left-16.png")));
		GridBagConstraints gbc_btnSchedules = new GridBagConstraints();
		gbc_btnSchedules.gridwidth = 2;
		gbc_btnSchedules.ipadx = 5;
		gbc_btnSchedules.anchor = GridBagConstraints.WEST;
		gbc_btnSchedules.insets = new Insets(0, 0, 5, 5);
		gbc_btnSchedules.gridx = 1;
		gbc_btnSchedules.gridy = 1;
		add(btnSchedules, gbc_btnSchedules);
		
		JPanel pnOverview = new PanelOverview(sch, null);
		GridBagConstraints gbc_pnOverview = new GridBagConstraints();
		gbc_pnOverview.insets = new Insets(0, 0, 5, 5);
		gbc_pnOverview.fill = GridBagConstraints.BOTH;
		gbc_pnOverview.gridx = 2;
		gbc_pnOverview.gridy = 2;
		add(pnOverview, gbc_pnOverview);
		
		JButton btnAdd = new JButton("");
		btnAdd.setBorder(new RoundedBorder(30));
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setIcon(new ImageIcon(PanelSchedule.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-plus-16.png")));
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.EAST;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 2;
		gbc_btnAdd.gridy = 3;
		add(btnAdd, gbc_btnAdd);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 4;
		add(scrollPane, gbc_scrollPane);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 5;
		add(verticalStrut, gbc_verticalStrut);
		
		reschedule(br, scrollPane, sch);
	}
	
	public void reschedule(Bizrain br, JScrollPane pan, Schedule sch) {
		JPanel panel = new JPanel();
		pan.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0};
		panel.setLayout(gbl_panel);
		
		int pos = 0;
		for (ScheduleItem si : sch.getItems()) {
			Component rigidArea_1 = Box.createRigidArea(new Dimension(5, 5));
			GridBagConstraints gbc_rigidArea_1 = new GridBagConstraints();
			gbc_rigidArea_1.insets = new Insets(0, 0, 5, 5);
			gbc_rigidArea_1.gridx = 0;
			gbc_rigidArea_1.gridy = 2*pos;
			panel.add(rigidArea_1, gbc_rigidArea_1);
			
			Component rigidArea_2 = Box.createRigidArea(new Dimension(5, 5));
			GridBagConstraints gbc_rigidArea_2 = new GridBagConstraints();
			gbc_rigidArea_2.insets = new Insets(0, 0, 5, 0);
			gbc_rigidArea_2.gridx = 2;
			gbc_rigidArea_2.gridy = 2*pos;
			panel.add(rigidArea_2, gbc_rigidArea_2);
			
			JPanel panel_1 = new PanelLocation(si, null); //TODO get me a thing
			GridBagConstraints gbc_panel_1 = new GridBagConstraints();
			gbc_panel_1.insets = new Insets(0, 0, 0, 5);
			gbc_panel_1.fill = GridBagConstraints.BOTH;
			gbc_panel_1.gridx = 1;
			gbc_panel_1.gridy = 2*pos+1;
			panel.add(panel_1, gbc_panel_1);
			
			pos++;
		}
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(5, 5));
		GridBagConstraints gbc_rigidArea_1 = new GridBagConstraints();
		gbc_rigidArea_1.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_1.gridx = 0;
		gbc_rigidArea_1.gridy = 2*pos;
		panel.add(rigidArea_1, gbc_rigidArea_1);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(5, 5));
		GridBagConstraints gbc_rigidArea_2 = new GridBagConstraints();
		gbc_rigidArea_2.insets = new Insets(0, 0, 5, 0);
		gbc_rigidArea_2.gridx = 2;
		gbc_rigidArea_2.gridy = 2*pos;
		panel.add(rigidArea_2, gbc_rigidArea_2);
	}
}
