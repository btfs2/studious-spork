package uk.ac.cam.bizrain.ui;

import javax.swing.JPanel;

import uk.ac.cam.bizrain.Bizrain;
import uk.ac.cam.bizrain.schedule.LocalTimeToEpoch;
import uk.ac.cam.bizrain.schedule.Schedule;
import uk.ac.cam.bizrain.ui.comp.RoundedBorder;
import uk.ac.cam.bizrain.ui.sub.PanelOverview;
import uk.ac.cam.bizrain.weather.IWeatherData;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelScheduleList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8335544785427042936L;

	/**
	 * Create the panel.
	 */
	public PanelScheduleList(Bizrain br) {
		PanelScheduleList beme = this;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea = new GridBagConstraints();
		gbc_rigidArea.gridheight = 2;
		gbc_rigidArea.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea.gridx = 0;
		gbc_rigidArea.gridy = 0;
		add(rigidArea, gbc_rigidArea);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 5));
		GridBagConstraints gbc_rigidArea_1 = new GridBagConstraints();
		gbc_rigidArea_1.insets = new Insets(0, 0, 5, 0);
		gbc_rigidArea_1.gridx = 3;
		gbc_rigidArea_1.gridy = 0;
		add(rigidArea_1, gbc_rigidArea_1);
		
		JLabel lblYourSchedules = new JLabel("Your Schedules");
		lblYourSchedules.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_lblYourSchedules = new GridBagConstraints();
		gbc_lblYourSchedules.anchor = GridBagConstraints.WEST;
		gbc_lblYourSchedules.insets = new Insets(0, 0, 5, 5);
		gbc_lblYourSchedules.gridx = 1;
		gbc_lblYourSchedules.gridy = 1;
		add(lblYourSchedules, gbc_lblYourSchedules);
		
		JButton btnSettings = new JButton("");
		btnSettings.setBackground(Color.WHITE);
		btnSettings.setBorder(new RoundedBorder(30));
		btnSettings.setIcon(new ImageIcon(PanelScheduleList.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-cogs-16.png")));
		GridBagConstraints gbc_btnSettings = new GridBagConstraints();
		gbc_btnSettings.anchor = GridBagConstraints.EAST;
		gbc_btnSettings.insets = new Insets(0, 0, 5, 5);
		gbc_btnSettings.gridx = 2;
		gbc_btnSettings.gridy = 1;
		add(btnSettings, gbc_btnSettings);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 2;
		add(verticalStrut, gbc_verticalStrut);
		
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		add(scrollPane, gbc_scrollPane);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				br.setMainPanel(new PanelAddSchedule(br, () -> br.setMainPanel(beme), s -> {
					br.sm.addSchedule(new Schedule(s));
					beme.reschedule(br, scrollPane, LocalTimeToEpoch.getDefault());
					br.setMainPanel(beme);
				}));
			}
		});
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setBorder(new RoundedBorder(30));
		btnAdd.setIcon(new ImageIcon(PanelScheduleList.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-plus-16.png")));
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.EAST;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 2;
		gbc_btnAdd.gridy = 3;
		add(btnAdd, gbc_btnAdd);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 5;
		add(verticalStrut_1, gbc_verticalStrut_1);

		reschedule(br, scrollPane, LocalTimeToEpoch.getDefault());
	}
		
	public void reschedule(Bizrain br, JScrollPane pan, LocalTimeToEpoch lt2e) {
		PanelScheduleList beme = this;
		
		JPanel panel = new JPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0};
		panel.setLayout(gbl_panel);
		
		int pos = 0;
		for (Schedule s : br.sm.getSchedules()) {
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
			
			IWeatherData iwd = br.sm.getScheduleCombinedWeather(s, br.weatherProv, false, 
					LocalTimeToEpoch.getDefault());
			JPanel panel_1 = new PanelOverview(br, s, iwd, lt2e);
			panel_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					br.setMainPanel(new PanelSchedule(br, s, () -> {
						beme.reschedule(br, pan, LocalTimeToEpoch.getDefault());
						br.setMainPanel(beme);
					}));
				}
			});
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
		
		pan.setViewportView(panel);
		
		invalidate();
		validate();
		repaint();
	}

}
