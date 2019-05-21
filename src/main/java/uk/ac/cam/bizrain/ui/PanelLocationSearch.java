package uk.ac.cam.bizrain.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import uk.ac.cam.bizrain.Bizrain;
import uk.ac.cam.bizrain.location.IGeocoder;
import uk.ac.cam.bizrain.location.IPlace;
import uk.ac.cam.bizrain.location.StringPlace;
import uk.ac.cam.bizrain.schedule.Schedule;
import uk.ac.cam.bizrain.schedule.ScheduleItem;
import uk.ac.cam.bizrain.ui.comp.JClock;
import uk.ac.cam.bizrain.ui.comp.JTimeSelect;
import uk.ac.cam.bizrain.ui.comp.RoundedBorder;
import uk.ac.cam.bizrain.ui.comp.SwingUtil;
import uk.ac.cam.bizrain.ui.sub.PanelConfirmOverlap;
import uk.ac.cam.bizrain.ui.sub.PanelTimeSelector;

/**
 * 
 * Panel for searching location
 * 
 * Much like LocationEdit, but also contains a search box
 * 
 * @see PanelLocationEdit
 * @see JClock
 * @see JTimeSelect
 * 
 * @author btfs2, Paulina (docs)
 *
 */
public class PanelLocationSearch extends JPanel {

	private static final Logger log = Logger.getLogger("LocationSearch"); 
	
	/**
	 * Serialised
	 */
	private static final long serialVersionUID = -4832960499617148553L;

	private JButton btnAdd;
	
	/**
	 * 
	 */
	private Runnable validateAdd = () -> {};
	
	/**
	 * The location searching model
	 * 
	 * Appears to work
	 * 
	 * May search for the same thing multiple times, but requests are cached and geocoding is free
	 * 
	 * TODO: Stop multi-calling
	 * 
	 * @author btfs2
	 *
	 */
	class LocSearchModel implements ComboBoxModel<IPlace> {

		//Stuff stored
		IGeocoder geocoder;
		List<IPlace> places = new ArrayList<IPlace>();
		IPlace selected;
		List<ListDataListener> ldl = new ArrayList<ListDataListener>();
		JComboBox<IPlace> cb;
		
		/**
		 * Create a new location search model
		 * 
		 * @param geocoder Geolcoder to use
		 * @param cb Combobox being used to show popup on result return
		 */
		LocSearchModel(IGeocoder geocoder, JComboBox<IPlace> cb) {
			this.geocoder = geocoder;
			this.cb = cb;
		}

		boolean searching = false;
		
		/**
		 * Searches for a location
		 */
		public void search() {
			// Run search in new thread to avoid locking UI
			new Thread(() -> {
				log.log(Level.INFO, "Searching " + selected.getDisplayName());
				//Check geocoder avaliability
				if (!geocoder.isGeocoderAvaliable()) {
					JOptionPane.showMessageDialog(null, "Cannot connect to geocoder\nplease check network connection",
							"Networking error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				searching = true;
				//Prompt user that searching is in effect
				validateAdd.run();
				List<IPlace> nplaces = geocoder.predict(selected);
				searching = false;
				synchronized (places) {
					places = nplaces;
				}
				LocSearchModel beme = this;
				//Set data and fire updates
				ldl.forEach(i -> i
						.contentsChanged(new ListDataEvent(beme, ListDataEvent.CONTENTS_CHANGED, 0, beme.getSize())));
				log.info("Search complete");
				//Try show popup; will fail if not on screen; fortunately, we don't care
				try {
					cb.setPopupVisible(true);
				} catch (IllegalStateException ise) {
					log.log(Level.INFO, "Failed to show popup as component not on screen", ise);
				}
			}).start();
		}

		@Override
		public int getSize() {
			synchronized (places) {
				return places.size();
			}
		}

		@Override
		public IPlace getElementAt(int index) {
			synchronized (places) {
				return places.get(index);
			}
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			ldl.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			ldl.remove(l);
		}

		@Override
		public void setSelectedItem(Object anItem) {
			if (anItem instanceof IPlace) {
				selected = (IPlace) anItem;
			} else if(anItem instanceof String) {
				selected = places.stream().filter(i -> i.getDisplayName().equals(anItem)).findFirst().orElse(new StringPlace((String) anItem));
			} else {
				System.err.println("INVALID TYPE: " + anItem.getClass().getName());
			}
		}

		@Override
		public Object getSelectedItem() {
			return selected;
		}
		
	}
	
	/**
	 * Spinner control for time
	 * 
	 * Not really used anymore, just for displaying time
	 * 
	 * @author btfs2
	 *
	 */
	static class TimeSpinner implements SpinnerModel {

		LocalTime time;
		List<ChangeListener> csl = new ArrayList<ChangeListener>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		
		/**
		 * Create one now
		 */
		TimeSpinner() {
			this(LocalTime.now());
		}
		
		/**
		 * Create on at given time
		 * @param time given time
		 */
		TimeSpinner(LocalTime time) {
			this.time = time;
		}
		
		@Override
		public Object getValue() {
			return time.format(dtf);
		}
		
		/**
		 * Get current value as Local time rather than string
		 * @return
		 */
		public LocalTime getCurrent() {
			return time;
		}

		@Override
		public void setValue(Object value) {
			//Parse input value
			if (value instanceof LocalTime) {
				time = (LocalTime) value;
			} else if (value instanceof String) {
				time = LocalTime.parse((String)value);
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
	
	/**
	 * CB for additive return
	 * 
	 * @author btfs2
	 *
	 */
	public interface LocSearchReturn {
		public void returnData(ScheduleItem si);
	}
	
	/**
	 * CB for back without adding
	 * @author btfs2
	 *
	 */
	interface LocSearchBack {
		public void back();
	}
	
	/**
	 * Create the panel.
	 */
	public PanelLocationSearch(Bizrain br, Schedule sch, LocSearchReturn ret, LocSearchBack bac) {
		// This reference for callbacks
		PanelLocationSearch beme = this;
	
		//View
		///////////
		
		//Setup Layout
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 16, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 37, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		//Notice label for searching/ error information
		JLabel lblSearching = new JLabel("Searching...");
		lblSearching.setFont(SwingUtil.getFontTitle().deriveFont(15f).deriveFont(Font.BOLD));
		lblSearching.setVisible(false);
		GridBagConstraints gbc_lblSearching = new GridBagConstraints();
		gbc_lblSearching.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearching.gridx = 2;
		gbc_lblSearching.gridy = 1;
		add(lblSearching, gbc_lblSearching);
		
		//Clock for displaying time
		JClock clock = new JClock();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 4;
		add(clock, gbc_panel);
		
		//Search box with all relevent code
		JComboBox<IPlace> cbSearch = new JComboBox<IPlace>();
		cbSearch.setBorder(new RoundedBorder(30));
		cbSearch.setBackground(Color.WHITE);
		LocSearchModel lsm = new LocSearchModel(br.geocoder, cbSearch);
		cbSearch.setModel(lsm);
		cbSearch.setSelectedItem(new StringPlace(""));
		cbSearch.setMaximumRowCount(14);
		cbSearch.setEditable(true);
		GridBagConstraints gbc_cbSearch = new GridBagConstraints();
		gbc_cbSearch.gridwidth = 2;
		gbc_cbSearch.insets = new Insets(0, 0, 5, 5);
		gbc_cbSearch.fill = GridBagConstraints.BOTH;
		gbc_cbSearch.gridx = 1;
		gbc_cbSearch.gridy = 2;
		add(cbSearch, gbc_cbSearch);
		SwingUtil.fixCbBorder(cbSearch);
		
		//Search button
		JButton btnSearch = new JButton("");
		btnSearch.setBackground(Color.WHITE);
		btnSearch.setBorder(new RoundedBorder(30));
		btnSearch.setIcon(new ImageIcon(PanelLocationSearch.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-search-16.png")));
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.BOTH;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 3;
		gbc_btnSearch.gridy = 2;
		add(btnSearch, gbc_btnSearch);
		
		//Time things
		TimeSpinner tsStart = new TimeSpinner();
		
		TimeSpinner tsEnd = new TimeSpinner(LocalTime.now().plusHours(1));

		//Time display; can be used for manually entering time if needed in fallback
		JSpinner spStart = new JSpinner();
		SwingUtil.hideSpinnerArrow(spStart);
		spStart.setModel(tsStart);
		GridBagConstraints gbc_spStart = new GridBagConstraints();
		gbc_spStart.fill = GridBagConstraints.HORIZONTAL;
		gbc_spStart.insets = new Insets(0, 0, 5, 5);
		gbc_spStart.gridx = 1;
		gbc_spStart.gridy = 5;
		add(spStart, gbc_spStart);
		
		//DITTO
		JSpinner spEnd = new JSpinner();
		SwingUtil.hideSpinnerArrow(spEnd);
		spEnd.setModel(tsEnd);
		GridBagConstraints gbc_spEnd = new GridBagConstraints();
		gbc_spEnd.fill = GridBagConstraints.HORIZONTAL;
		gbc_spEnd.insets = new Insets(0, 0, 5, 5);
		gbc_spEnd.gridx = 3;
		gbc_spEnd.gridy = 5;
		add(spEnd, gbc_spEnd);
		
		//Start time prompt button
		JButton btnStart = new JButton("Start");
		btnStart.setBorder(new RoundedBorder(30));
		btnStart.setBackground(Color.WHITE);
		btnStart.setIcon(new ImageIcon(PanelLocationSearch.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-clock-16.png")));
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.fill = GridBagConstraints.BOTH;
		gbc_btnStart.insets = new Insets(0, 0, 5, 5);
		gbc_btnStart.gridx = 1;
		gbc_btnStart.gridy = 4;
		add(btnStart, gbc_btnStart);
		
		//End time prompt button
		JButton btnEnd = new JButton("End");
		btnEnd.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		btnEnd.setBorder(new RoundedBorder(30));
		btnEnd.setBackground(Color.WHITE);
		btnEnd.setIcon(new ImageIcon(PanelLocationSearch.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-clock-16.png")));
		GridBagConstraints gbc_btnEnd = new GridBagConstraints();
		gbc_btnEnd.fill = GridBagConstraints.BOTH;
		gbc_btnEnd.insets = new Insets(0, 0, 5, 5);
		gbc_btnEnd.gridx = 3;
		gbc_btnEnd.gridy = 4;
		add(btnEnd, gbc_btnEnd);
		
		//Padding
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
		
		//Error label
		JLabel lblError = new JLabel("error");
		lblError.setFont(SwingUtil.getFontSub().deriveFont(15f).deriveFont(Font.BOLD));
		lblError.setVisible(false);
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.gridwidth = 3;
		gbc_lblError.insets = new Insets(0, 0, 5, 5);
		gbc_lblError.gridx = 1;
		gbc_lblError.gridy = 8;
		add(lblError, gbc_lblError);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 3;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 10;
		add(panel, gbc_panel_2);
		
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel_2);
		
		JButton btnBack = new JButton("");
		btnBack.setBorder(new RoundedBorder(30));
		btnBack.setBackground(Color.WHITE);
		btnBack.setIcon(new ImageIcon(PanelLocationSearch.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-chevron-left-16.png")));
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 0;
		gbc_btnBack.gridy = 0;
		panel.add(btnBack, gbc_btnBack);
		
		btnAdd = new JButton("");
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.gridx = 1;
		gbc_btnAdd.gridy = 0;
		panel.add(btnAdd, gbc_btnAdd);
		btnAdd.setBorder(new RoundedBorder(30));
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setIcon(new ImageIcon(PanelLocationSearch.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-plus-16.png")));
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_2 = new GridBagConstraints();
		gbc_rigidArea_2.insets = new Insets(0, 0, 0, 5);
		gbc_rigidArea_2.gridx = 0;
		gbc_rigidArea_2.gridy = 11;
		add(rigidArea_2, gbc_rigidArea_2);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_3 = new GridBagConstraints();
		gbc_rigidArea_3.gridx = 4;
		gbc_rigidArea_3.gridy = 11;
		add(rigidArea_3, gbc_rigidArea_3);
		
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_4 = new GridBagConstraints();
		gbc_rigidArea_4.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_4.gridx = 0;
		gbc_rigidArea_4.gridy = 3;
		add(rigidArea_4, gbc_rigidArea_4);
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_5 = new GridBagConstraints();
		gbc_rigidArea_5.insets = new Insets(0, 0, 5, 0);
		gbc_rigidArea_5.gridx = 4;
		gbc_rigidArea_5.gridy = 3;
		add(rigidArea_5, gbc_rigidArea_5);
		
		// CONTROLLER
		/////////////
		
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lsm.search();
			}
		});
		
		spEnd.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				clock.setTo(((TimeSpinner) spEnd.getModel()).getCurrent());
				validateAdd.run();
			}
		});
		
		cbSearch.addActionListener(e -> {
			if (e.getActionCommand().equals("comboBoxEdited")) {
				lsm.search();
			}
			validateAdd.run();
		});
		
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				br.setMainPanel(new PanelTimeSelector(((TimeSpinner) spStart.getModel()).getCurrent(), (time) -> {
					br.setMainPanel(beme);
					spStart.setValue(time);
				}));
			}
		});
		
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnAdd.isEnabled()) {
					
					cbSearch.setPopupVisible(false);
					ScheduleItem si = new ScheduleItem(
						(IPlace)cbSearch.getSelectedItem(), 
						br.geocoder.placeToLoc((IPlace) cbSearch.getSelectedItem()), 
						((TimeSpinner) spStart.getModel()).getCurrent(), 
						((TimeSpinner) spEnd.getModel()).getCurrent());
					if (sch.doesOverlap(si)) {
						br.setMainPanel(new PanelConfirmOverlap((accept) -> {
							if (accept) {
								ret.returnData(si);
							} else {
								br.setMainPanel(beme);
							}
						}));
					} else {
						ret.returnData(si);
					}
				}
			}
		});
		
		spStart.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				clock.setFrom(((TimeSpinner) spStart.getModel()).getCurrent());
				validateAdd.run();
			}
		});
		
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cbSearch.setPopupVisible(false);
				bac.back();
			}
		});
		
		btnEnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				br.setMainPanel(new PanelTimeSelector(((TimeSpinner) spEnd.getModel()).getCurrent(), (time) -> {
					br.setMainPanel(beme);
					spEnd.setValue(time);
				}));
			}
		});
		
		validateAdd = () -> {
			clock.setFrom(((TimeSpinner) spStart.getModel()).getCurrent());
			clock.setTo(((TimeSpinner) spEnd.getModel()).getCurrent());
			if (cbSearch.getSelectedItem() != null 
					&& cbSearch.getSelectedItem().toString() != null
					&& !cbSearch.getSelectedItem().toString().equals("")
					&& !cbSearch.getSelectedItem().toString().trim().equals("")) {
				if (((TimeSpinner) spStart.getModel()).getCurrent()
						.isBefore(((TimeSpinner) spEnd.getModel()).getCurrent())) {
					
						btnAdd.setEnabled(true);
						lblError.setVisible(false);
				} else {
					lblError.setText("Start time is before end time");
					lblError.setVisible(true);
					btnAdd.setEnabled(false);
				}
			} else {
				lblError.setText("No place entered; try searching for one");
				lblError.setVisible(true);
				btnAdd.setEnabled(false);
			}
			if (((LocSearchModel)cbSearch.getModel()).searching) {
				lblError.setText("Searching...");
				lblError.setVisible(true);
			}
		};
		
		validateAdd.run();
	}
	
	

}
