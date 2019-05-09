package uk.ac.cam.bizrain.ui.sub;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.cam.bizrain.location.IPlaceSpecific;
import uk.ac.cam.bizrain.schedule.Schedule;
import uk.ac.cam.bizrain.schedule.Schedule.ScheduleItem;
import uk.ac.cam.bizrain.ui.comp.RoundedBorder;
import uk.ac.cam.bizrain.ui.comp.SwingUtil;
import uk.ac.cam.bizrain.weather.IWeatherData;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockWorst;

public class PanelOverview extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5858012430000486336L;

	/**
	 * Create the panel.
	 */
	public PanelOverview(Schedule sch, IWeatherData locWeather) {
		setBorder(new RoundedBorder(30));
		setBackground(Color.decode("0xDDDDDD"));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 18, 17, 7, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 100));
		GridBagConstraints gbc_rigidArea = new GridBagConstraints();
		gbc_rigidArea.gridheight = 4;
		gbc_rigidArea.insets = new Insets(0, 0, 0, 5);
		gbc_rigidArea.gridx = 1;
		gbc_rigidArea.gridy = 0;
		add(rigidArea, gbc_rigidArea);
		
		JLabel lbllocationName = new JLabel(sch.getScheduleName());
		lbllocationName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lbllocationName = new GridBagConstraints();
		gbc_lbllocationName.anchor = GridBagConstraints.WEST;
		gbc_lbllocationName.insets = new Insets(0, 0, 5, 5);
		gbc_lbllocationName.gridx = 0;
		gbc_lbllocationName.gridy = 0;
		add(lbllocationName, gbc_lbllocationName);
		
		StringBuilder majorPlaces = new StringBuilder();
		List<ScheduleItem> mp = sch.getNLongestItems(3);
		List<String> inThing = new ArrayList<String>();
		boolean first = true;
		for (ScheduleItem schi : mp) {
			String toAdd = "";
			if (schi.getPlace() instanceof IPlaceSpecific) {
				toAdd = ((IPlaceSpecific)schi.getPlace()).getName();
			} else {
				toAdd = schi.getPlace().getDisplayName().split(",")[0];
			}
			if (inThing.contains(toAdd)) {
				if (first) {
					first = false;
				} else {
					majorPlaces.append(",");
				}
				majorPlaces.append(toAdd);
				inThing.add(toAdd);
			}
		}
		JLabel lblMajorplaces = new JLabel(majorPlaces.toString());
		GridBagConstraints gbc_lblMajorplaces = new GridBagConstraints();
		gbc_lblMajorplaces.anchor = GridBagConstraints.WEST;
		gbc_lblMajorplaces.insets = new Insets(0, 0, 5, 5);
		gbc_lblMajorplaces.gridx = 0;
		gbc_lblMajorplaces.gridy = 1;
		add(lblMajorplaces, gbc_lblMajorplaces);
		
		
		JLabel lblfromto = new JLabel(String.format("%s-%s", 
				sch.getStart().format(DateTimeFormatter.ofPattern("HH:mm")),
				sch.getEnd().format(DateTimeFormatter.ofPattern("HH:mm"))));
		GridBagConstraints gbc_lblfromto = new GridBagConstraints();
		gbc_lblfromto.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblfromto.insets = new Insets(0, 0, 0, 5);
		gbc_lblfromto.gridx = 0;
		gbc_lblfromto.gridy = 3;
		add(lblfromto, gbc_lblfromto);
		
		long start = LocalDateTime.of(LocalDate.now(ZoneOffset.UTC), sch.getStart()).toEpochSecond(ZoneOffset.UTC);
		long end = LocalDateTime.of(LocalDate.now(ZoneOffset.UTC), sch.getEnd()).toEpochSecond(ZoneOffset.UTC);
		IWeatherBlockWorst worst = locWeather.getWeatherWorstIn(start, end);
		JLabel lblmax = new JLabel(String.format("%.1f\u00B0C/%.1f\u00B0C",
				worst.getWeatherMaxTemperature(), worst.getWeatherMinTemperature()));
		GridBagConstraints gbc_lblmax = new GridBagConstraints();
		gbc_lblmax.anchor = GridBagConstraints.SOUTH;
		gbc_lblmax.gridx = 2;
		gbc_lblmax.gridy = 3;
		add(lblmax, gbc_lblmax);
		
		JLabel lblIco = new JLabel("");
		lblIco.setIcon(SwingUtil.getIconOfWeather(worst.getWeatherWorstIcon()));
		GridBagConstraints gbc_lblIco = new GridBagConstraints();
		gbc_lblIco.gridheight = 3;
		gbc_lblIco.insets = new Insets(0, 0, 5, 0);
		gbc_lblIco.gridx = 2;
		gbc_lblIco.gridy = 0;
		add(lblIco, gbc_lblIco);

	}

}