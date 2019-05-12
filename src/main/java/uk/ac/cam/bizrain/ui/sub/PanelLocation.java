package uk.ac.cam.bizrain.ui.sub;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.cam.bizrain.config.BizrainConfig;
import uk.ac.cam.bizrain.location.IPlace;
import uk.ac.cam.bizrain.location.IPlaceSpecific;
import uk.ac.cam.bizrain.schedule.LocalTimeToEpoch;
import uk.ac.cam.bizrain.schedule.Schedule.ScheduleItem;
import uk.ac.cam.bizrain.ui.comp.RoundedBorder;
import uk.ac.cam.bizrain.ui.comp.SwingUtil;
import uk.ac.cam.bizrain.weather.IWeatherData;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockWorst;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelLocation extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5858012430000486336L;

	/**
	 * Create the panel.
	 */
	public PanelLocation(ScheduleItem schi, IWeatherData locWeather, LocalTimeToEpoch lt2e) {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO DO THING
			}
		});
		setBorder(new RoundedBorder(30));
		setBackground(Color.decode("0xDDDDDD"));
		//TODO map temp to colour and set
		
		String line1, line2, line3;
		IPlace place = schi.getPlace();
		if (place instanceof IPlaceSpecific) {
			line1 = ((IPlaceSpecific) place).getName();
			line2 = ((IPlaceSpecific) place).getCity();
			line3 = ((IPlaceSpecific) place).getCountry();
		} else {
			String[] stuff = place.getDisplayName().split(",");
			line1 = stuff[0];
			line2 = stuff.length > 1 ? stuff[1] : "";
			line3 = stuff.length > 2 ? stuff[2] : "";
		}
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 17, 7, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 100));
		GridBagConstraints gbc_rigidArea = new GridBagConstraints();
		gbc_rigidArea.gridheight = 5;
		gbc_rigidArea.insets = new Insets(0, 0, 0, 5);
		gbc_rigidArea.gridx = 1;
		gbc_rigidArea.gridy = 0;
		add(rigidArea, gbc_rigidArea);
		
		JLabel lbllocationName = new JLabel(line1);
		GridBagConstraints gbc_lbllocationName = new GridBagConstraints();
		gbc_lbllocationName.anchor = GridBagConstraints.WEST;
		gbc_lbllocationName.insets = new Insets(0, 0, 5, 5);
		gbc_lbllocationName.gridx = 0;
		gbc_lbllocationName.gridy = 0;
		add(lbllocationName, gbc_lbllocationName);
		
		JLabel lbllocationCity = new JLabel(line2);
		GridBagConstraints gbc_lbllocationCity = new GridBagConstraints();
		gbc_lbllocationCity.anchor = GridBagConstraints.WEST;
		gbc_lbllocationCity.insets = new Insets(0, 0, 5, 5);
		gbc_lbllocationCity.gridx = 0;
		gbc_lbllocationCity.gridy = 1;
		add(lbllocationCity, gbc_lbllocationCity);
		
		JLabel lbllocationCountry = new JLabel(line3);
		GridBagConstraints gbc_lbllocationCountry = new GridBagConstraints();
		gbc_lbllocationCountry.anchor = GridBagConstraints.WEST;
		gbc_lbllocationCountry.insets = new Insets(0, 0, 5, 5);
		gbc_lbllocationCountry.gridx = 0;
		gbc_lbllocationCountry.gridy = 2;
		add(lbllocationCountry, gbc_lbllocationCountry);
		
		JLabel lblfromto = new JLabel(String.format("%s-%s", 
				schi.getStart().format(DateTimeFormatter.ofPattern("HH:mm")),
				schi.getEnd().format(DateTimeFormatter.ofPattern("HH:mm"))));
		GridBagConstraints gbc_lblfromto = new GridBagConstraints();
		gbc_lblfromto.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblfromto.insets = new Insets(0, 0, 0, 5);
		gbc_lblfromto.gridx = 0;
		gbc_lblfromto.gridy = 4;
		add(lblfromto, gbc_lblfromto);
		
		long start = lt2e.toEpoch(schi.getStart());
		long end = lt2e.toEpoch(schi.getEnd());
		IWeatherBlockWorst worst = locWeather.getWeatherWorstIn(start, end);
		JLabel lblmax = new JLabel(String.format("%.1f\u00B0C/%.1f\u00B0C",
				worst.getWeatherMaxTemperature(), worst.getWeatherMinTemperature()));
		if (worst.getWeatherMaxTemperature() == -1*Float.MAX_VALUE) {
			lblmax.setText("No Data");
		} else {
			//System.out.println(schi.getEnd());
			//System.out.println(LocalTime.now(ZoneId.of(BizrainConfig.INSTANCE.timeZoneId)));
			if (schi.getEnd().isAfter(LocalTime.now(ZoneId.of(BizrainConfig.INSTANCE.timeZoneId)))) {
				setBackground(SwingUtil.tempToColor((worst.getWeatherMaxTemperature() + worst.getWeatherMinTemperature())/2));
			}
		}
		GridBagConstraints gbc_lblmax = new GridBagConstraints();
		gbc_lblmax.anchor = GridBagConstraints.SOUTH;
		gbc_lblmax.gridx = 2;
		gbc_lblmax.gridy = 4;
		add(lblmax, gbc_lblmax);
		
		
		
		JLabel lblIco = new JLabel("");
		lblIco.setIcon(SwingUtil.getIconOfWeather(worst.getWeatherWorstIcon()));
		GridBagConstraints gbc_lblIco = new GridBagConstraints();
		gbc_lblIco.gridheight = 4;
		gbc_lblIco.insets = new Insets(0, 0, 5, 0);
		gbc_lblIco.gridx = 2;
		gbc_lblIco.gridy = 0;
		add(lblIco, gbc_lblIco);

	}

}
