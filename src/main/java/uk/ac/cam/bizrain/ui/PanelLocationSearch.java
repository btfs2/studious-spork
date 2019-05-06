package uk.ac.cam.bizrain.ui;

import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import uk.ac.cam.bizrain.location.IGeocoder;
import uk.ac.cam.bizrain.location.IPlace;
import uk.ac.cam.bizrain.location.StringPlace;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JSpinner;
import javax.swing.Box;
import java.awt.Dimension;

public class PanelLocationSearch extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4832960499617148553L;

	class LocSearchModel implements ComboBoxModel<IPlace> {

		IGeocoder geocoder;
		List<IPlace> places = new ArrayList<IPlace>();
		IPlace selected;
		List<ListDataListener> ldl = new ArrayList<ListDataListener>();
		
		LocSearchModel(IGeocoder geocoder) {
			this.geocoder = geocoder;
		}
		
		public void search() {
			new Thread(() -> {
				System.out.println("Searching " + selected.getDisplayName());
				List<IPlace> nplaces = geocoder.predict(selected);
				synchronized (places) {
					places = nplaces;
				}
				LocSearchModel beme = this;
				ldl.forEach(i -> i.contentsChanged(new ListDataEvent(beme, ListDataEvent.CONTENTS_CHANGED, 0, beme.getSize())));
				System.out.println("Search complete");
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
			System.out.println("SETTING ITEM TO: " + anItem);
			if (anItem instanceof IPlace) {
				selected = (IPlace) anItem;
			} else if(anItem instanceof String) {
				selected = new StringPlace((String) anItem);
			} else {
				System.err.println("INVALID TYPE: " + anItem.getClass().getName());
			}
		}

		@Override
		public Object getSelectedItem() {
			return selected;
		}
		
	}
	
	class TimeSpinner implements SpinnerModel {

		LocalTime time;
		List<ChangeListener> csl = new ArrayList<ChangeListener>();
		
		TimeSpinner() {
			this(LocalTime.now());
		}
		
		TimeSpinner(LocalTime time) {
			this.time = time;
		}
		
		@Override
		public Object getValue() {
			return time.format(DateTimeFormatter.ofPattern("HH:mm"));
		}

		@Override
		public void setValue(Object value) {
			System.out.println("TIMECHANGE: " + value);
			if (value instanceof LocalTime) {
				time = (LocalTime) value;
			} else if (value instanceof String) {
				time = LocalTime.parse((String)value);
				//time = LocalTime.of(Integer.parseInt(((String) value).split(":")[0]), Integer.parseInt(((String) value).split(":")[1]), 0, 0);
			} else {
				System.err.println("INVALID TYPE");
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
	 * Create the panel.
	 */
	public PanelLocationSearch(IGeocoder geocoder) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 16, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 37, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 2.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_rigidArea_1.gridx = 5;
		gbc_rigidArea_1.gridy = 0;
		add(rigidArea_1, gbc_rigidArea_1);
		
		JLabel lblAddLocation = new JLabel("Add Location");
		lblAddLocation.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_lblAddLocation = new GridBagConstraints();
		gbc_lblAddLocation.gridwidth = 4;
		gbc_lblAddLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddLocation.gridx = 1;
		gbc_lblAddLocation.gridy = 1;
		add(lblAddLocation, gbc_lblAddLocation);
		
		LocSearchModel lsm = new LocSearchModel(geocoder);
		
		JComboBox<IPlace> cbSearch = new JComboBox<>();
		cbSearch.setModel(lsm);
		cbSearch.setMaximumRowCount(14);
		cbSearch.setEditable(true);
		cbSearch.addActionListener(e -> {
			if (e.getActionCommand().equals("comboBoxEdited")) {
				lsm.search();
			}
		});
		//cbSearch.setRenderer(new PlaceCellRenderer()); TODO FIX FOR INTEROPERABILITY; CURRENT SOLUTION IS TO JUST ENSURE TOSTRING WORKS
		cbSearch.setToolTipText("Location Search");
		GridBagConstraints gbc_cbSearch = new GridBagConstraints();
		gbc_cbSearch.gridwidth = 3;
		gbc_cbSearch.insets = new Insets(0, 0, 5, 5);
		gbc_cbSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSearch.gridx = 1;
		gbc_cbSearch.gridy = 2;
		add(cbSearch, gbc_cbSearch);
		
		
		JButton btnSearch = new JButton("");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lsm.search();
			}
		});
		btnSearch.setToolTipText("Search");
		btnSearch.setIcon(new ImageIcon(PanelLocationSearch.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-search-16.png")));
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 4;
		gbc_btnSearch.gridy = 2;
		add(btnSearch, gbc_btnSearch);
		
		TimeSpinner tsStart = new TimeSpinner();
		
		JLabel lblStart = new JLabel("Start");
		GridBagConstraints gbc_lblStart = new GridBagConstraints();
		gbc_lblStart.insets = new Insets(0, 0, 5, 5);
		gbc_lblStart.gridx = 2;
		gbc_lblStart.gridy = 4;
		add(lblStart, gbc_lblStart);
		JSpinner spStart = new JSpinner();
		spStart.setModel(tsStart);
		GridBagConstraints gbc_spStart = new GridBagConstraints();
		gbc_spStart.fill = GridBagConstraints.HORIZONTAL;
		gbc_spStart.insets = new Insets(0, 0, 5, 5);
		gbc_spStart.gridx = 3;
		gbc_spStart.gridy = 4;
		add(spStart, gbc_spStart);
		
		TimeSpinner tsEnd = new TimeSpinner(LocalTime.now().plusHours(1));
		
		JLabel lblEnd = new JLabel("End");
		GridBagConstraints gbc_lblEnd = new GridBagConstraints();
		gbc_lblEnd.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnd.gridx = 2;
		gbc_lblEnd.gridy = 6;
		add(lblEnd, gbc_lblEnd);
		JSpinner spEnd = new JSpinner();
		spEnd.setModel(tsEnd);
		GridBagConstraints gbc_spEnd = new GridBagConstraints();
		gbc_spEnd.fill = GridBagConstraints.HORIZONTAL;
		gbc_spEnd.insets = new Insets(0, 0, 5, 5);
		gbc_spEnd.gridx = 3;
		gbc_spEnd.gridy = 6;
		add(spEnd, gbc_spEnd);
		
		JButton btnAdd = new JButton("");
		btnAdd.setIcon(new ImageIcon(PanelLocationSearch.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-plus-16.png")));
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.gridwidth = 3;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 2;
		gbc_btnAdd.gridy = 8;
		add(btnAdd, gbc_btnAdd);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_2 = new GridBagConstraints();
		gbc_rigidArea_2.insets = new Insets(0, 0, 0, 5);
		gbc_rigidArea_2.gridx = 0;
		gbc_rigidArea_2.gridy = 9;
		add(rigidArea_2, gbc_rigidArea_2);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_3 = new GridBagConstraints();
		gbc_rigidArea_3.gridx = 5;
		gbc_rigidArea_3.gridy = 9;
		add(rigidArea_3, gbc_rigidArea_3);

	}

}
