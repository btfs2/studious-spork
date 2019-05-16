package uk.ac.cam.bizrain.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.ac.cam.bizrain.Bizrain;
import uk.ac.cam.bizrain.schedule.Schedule;
import uk.ac.cam.bizrain.schedule.Schedule.ScheduleItem;
import uk.ac.cam.bizrain.ui.comp.JClock;
import uk.ac.cam.bizrain.ui.comp.RoundedBorder;
import uk.ac.cam.bizrain.ui.comp.SwingUtil;
import uk.ac.cam.bizrain.ui.sub.PanelConfirmOverlap;
import uk.ac.cam.bizrain.ui.sub.PanelTimeSelector;

public class PanelLocationEdit extends JPanel {

	private static final Logger log = Logger.getLogger("LocationSearch");

	/**
	 * Serialised
	 */
	private static final long serialVersionUID = -4832960499617148553L;

	private JButton btnEdit;

	private Runnable validateAdd = () -> {
	};

	class TimeSpinner implements SpinnerModel {

		LocalTime time;
		List<ChangeListener> csl = new ArrayList<ChangeListener>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

		TimeSpinner() {
			this(LocalTime.now());
		}

		TimeSpinner(LocalTime time) {
			this.time = time;
		}

		@Override
		public Object getValue() {
			return time.format(dtf);
		}

		public LocalTime getCurrent() {
			return time;
		}

		@Override
		public void setValue(Object value) {
			if (value instanceof LocalTime) {
				time = (LocalTime) value;
			} else if (value instanceof String) {
				time = LocalTime.parse((String) value);
			} else {
				log.warning("INVALID TYPE");
			}
			TimeSpinner beme = this;
			csl.forEach(i -> i.stateChanged(new ChangeEvent(beme)));
		}

		@Override
		public Object getNextValue() {
			return time.plusMinutes(1);
		}

		@Override
		public Object getPreviousValue() {
			return time.plusMinutes(-1);
		}

		@Override
		public void addChangeListener(ChangeListener l) {
			csl.add(l);
		}

		@Override
		public void removeChangeListener(ChangeListener l) {
			csl.remove(l);
		}

	}

	interface LocEditBack {
		public void back();
	}

	/**
	 * Create the panel for editing location and uses PanelTimeSelect to select time
	 * 
	 */
	public PanelLocationEdit(Bizrain br, Schedule sch, ScheduleItem schi, LocEditBack bac) {
		// Theming
		// setBackground(Color.decode("0xDDDDDD"));

		// This reference for callbacks
		PanelLocationEdit beme = this;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 16, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 37, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblLocname = new JLabel(schi.getPlace().getDisplayName()) {

			private static final long serialVersionUID = -1738911771085636850L;

			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		lblLocname.setFont(SwingUtil.getFontTitle().deriveFont(20f));
		lblLocname.setBorder(new RoundedBorder(30));
		lblLocname.setBackground(Color.WHITE);
		GridBagConstraints gbc_lblLocname = new GridBagConstraints();
		gbc_lblLocname.gridwidth = 3;
		gbc_lblLocname.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocname.gridx = 1;
		gbc_lblLocname.gridy = 1;
		add(lblLocname, gbc_lblLocname);

		JClock clock = new JClock();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 3;
		add(clock, gbc_panel);

		TimeSpinner tsStart = new TimeSpinner(schi.getStart());

		TimeSpinner tsEnd = new TimeSpinner(schi.getEnd());

		JSpinner spStart = new JSpinner();
		spStart.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				clock.setFrom(((TimeSpinner) spStart.getModel()).getCurrent());
				validateAdd.run();
			}
		});
		SwingUtil.hideSpinnerArrow(spStart);
		// Centers text:
		// https://stackoverflow.com/questions/22702014/how-to-center-text-of-a-jspinner
		((JSpinner.DefaultEditor) spStart.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
		spStart.setModel(tsStart);
		GridBagConstraints gbc_spStart = new GridBagConstraints();
		gbc_spStart.fill = GridBagConstraints.HORIZONTAL;
		gbc_spStart.insets = new Insets(0, 0, 5, 5);
		gbc_spStart.gridx = 1;
		gbc_spStart.gridy = 4;
		// spStart.setVisible(false);
		add(spStart, gbc_spStart);

		JSpinner spEnd = new JSpinner();
		spEnd.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				clock.setTo(((TimeSpinner) spEnd.getModel()).getCurrent());
				validateAdd.run();
			}
		});
		// Centers text:
		// https://stackoverflow.com/questions/22702014/how-to-center-text-of-a-jspinner
		((JSpinner.DefaultEditor) spEnd.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
		SwingUtil.hideSpinnerArrow(spEnd);
		spEnd.setModel(tsEnd);
		GridBagConstraints gbc_spEnd = new GridBagConstraints();
		gbc_spEnd.fill = GridBagConstraints.HORIZONTAL;
		gbc_spEnd.insets = new Insets(0, 0, 5, 5);
		gbc_spEnd.gridx = 3;
		gbc_spEnd.gridy = 4;
		add(spEnd, gbc_spEnd);

		JButton btnStart = new JButton("Start");
		btnStart.setBorder(new RoundedBorder(30));
		btnStart.setBackground(Color.WHITE);
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				br.setMainPanel(new PanelTimeSelector(((TimeSpinner) spStart.getModel()).getCurrent(), (time) -> {
					br.setMainPanel(beme);
					spStart.setValue(time);
				}));
			}
		});
		btnStart.setIcon(
				new ImageIcon(PanelLocationEdit.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-clock-16.png")));
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.fill = GridBagConstraints.BOTH;
		gbc_btnStart.insets = new Insets(0, 0, 5, 5);
		gbc_btnStart.gridx = 1;
		gbc_btnStart.gridy = 3;
		add(btnStart, gbc_btnStart);

		JButton btnEnd = new JButton("End");
		btnEnd.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		btnEnd.setBorder(new RoundedBorder(30));
		btnEnd.setBackground(Color.WHITE);
		btnEnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				br.setMainPanel(new PanelTimeSelector(((TimeSpinner) spEnd.getModel()).getCurrent(), (time) -> {
					br.setMainPanel(beme);
					spEnd.setValue(time);
				}));
			}
		});
		btnEnd.setIcon(new ImageIcon(PanelLocationEdit.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-clock-16.png")));
		GridBagConstraints gbc_btnEnd = new GridBagConstraints();
		gbc_btnEnd.fill = GridBagConstraints.BOTH;
		gbc_btnEnd.insets = new Insets(0, 0, 5, 5);
		gbc_btnEnd.gridx = 3;
		gbc_btnEnd.gridy = 3;
		add(btnEnd, gbc_btnEnd);

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

		JLabel lblError = new JLabel("Start time is after end time");
		lblError.setFont(SwingUtil.getFontSub().deriveFont(17f));
		lblError.setVisible(false);
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.gridwidth = 3;
		gbc_lblError.insets = new Insets(0, 0, 5, 5);
		gbc_lblError.gridx = 1;
		gbc_lblError.gridy = 7;
		add(lblError, gbc_lblError);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 3;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 9;
		add(panel, gbc_panel_2);

		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel_2);

		JButton btnBack = new JButton("");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bac.back();
			}
		});
		btnBack.setBorder(new RoundedBorder(30));
		btnBack.setBackground(Color.WHITE);
		btnBack.setIcon(
				new ImageIcon(PanelLocationEdit.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-chevron-left-16.png")));
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 0;
		gbc_btnBack.gridy = 0;
		panel.add(btnBack, gbc_btnBack);

		JButton btnDel = new JButton("");
		btnDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sch.removeScheduleItem(schi);
				bac.back();
			}
		});

		btnDel.setBorder(new RoundedBorder(30));
		btnDel.setBackground(Color.WHITE);
		btnDel.setIcon(new ImageIcon(PanelLocationEdit.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-trash-16.png")));
		GridBagConstraints gbc_btnDel = new GridBagConstraints();
		gbc_btnDel.insets = new Insets(0, 0, 0, 5);
		gbc_btnDel.gridx = 1;
		gbc_btnDel.gridy = 0;
		panel.add(btnDel, gbc_btnDel);

		btnEdit = new JButton("");
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.gridx = 2;
		gbc_btnEdit.gridy = 0;
		panel.add(btnEdit, gbc_btnEdit);
		btnEdit.setBorder(new RoundedBorder(30));
		btnEdit.setBackground(Color.WHITE);
		btnEdit.setIcon(
				new ImageIcon(PanelLocationEdit.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-pencil-16.png")));
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnEdit.isEnabled()) {

					LocalTime oldStart = schi.getStart(), oldEnd = schi.getEnd();
					schi.setStart(((TimeSpinner) spStart.getModel()).getCurrent());
					schi.setEnd(((TimeSpinner) spEnd.getModel()).getCurrent());
					if (sch.doesOverlap(schi)) {
						br.setMainPanel(new PanelConfirmOverlap((accept) -> {
							if (accept) {
								bac.back();
							} else {
								schi.setStart(oldStart);
								schi.setEnd(oldEnd);
								br.setMainPanel(beme);
							}
						}));
					} else {
						bac.back();
					}
				}
			}
		});

		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_2 = new GridBagConstraints();
		gbc_rigidArea_2.insets = new Insets(0, 0, 0, 5);
		gbc_rigidArea_2.gridx = 0;
		gbc_rigidArea_2.gridy = 10;
		add(rigidArea_2, gbc_rigidArea_2);

		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_3 = new GridBagConstraints();
		gbc_rigidArea_3.gridx = 4;
		gbc_rigidArea_3.gridy = 10;
		add(rigidArea_3, gbc_rigidArea_3);

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

		validateAdd = () -> {
			clock.setFrom(((TimeSpinner) spStart.getModel()).getCurrent());
			clock.setTo(((TimeSpinner) spEnd.getModel()).getCurrent());
			if (((TimeSpinner) spStart.getModel()).getCurrent()
					.isBefore(((TimeSpinner) spEnd.getModel()).getCurrent())) {
				lblError.setVisible(false);
				btnEdit.setEnabled(true);
				return;
			}
			lblError.setVisible(true);
			btnEdit.setEnabled(false);
		};

		validateAdd.run();
	}

}
