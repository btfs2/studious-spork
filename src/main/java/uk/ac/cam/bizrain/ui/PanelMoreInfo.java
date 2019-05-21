package uk.ac.cam.bizrain.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.cam.bizrain.Bizrain;
import uk.ac.cam.bizrain.config.BizrainConfig;
import uk.ac.cam.bizrain.schedule.LocalTimeToEpoch;
import uk.ac.cam.bizrain.schedule.Schedule;
import uk.ac.cam.bizrain.schedule.ScheduleItem;
import uk.ac.cam.bizrain.ui.comp.JClock;
import uk.ac.cam.bizrain.ui.comp.RoundedBorder;
import uk.ac.cam.bizrain.ui.comp.SwingUtil;
import uk.ac.cam.bizrain.weather.block.IWeatherBlock;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockDayStats;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockWorst;

/**
 * This panel is a work in progress
 * 
 * It displayes more data about a place
 * 
 * @author btfs2
 *
 */
public class PanelMoreInfo extends JPanel {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3803561983051360855L;
	
	/**
	 * CBFUN
	 * 
	 * @author btfs2
	 *
	 */
	interface MoreInfoRet {
		public void ret();
	}
	
	/**
	 * Create the panel.
	 */
	public PanelMoreInfo(Bizrain br, Schedule sch, ScheduleItem schi, LocalTimeToEpoch lt2e, MoreInfoRet ret) {
		IWeatherBlockWorst iwbw = br.sm.getScheduleItemWeather(sch, br.weatherProv, false, schi)
				.getWeatherWorstIn(lt2e.toEpoch(schi.getStart()), lt2e.toEpoch(schi.getEnd()));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 3.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		JLabel lblPlacename = new JLabel(schi.getPlace().getDisplayName()) {

			private static final long serialVersionUID = -1738911771085636850L;

			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		lblPlacename.setFont(SwingUtil.getFontTitle().deriveFont(20f));
		lblPlacename.setBorder(new RoundedBorder(30));
		lblPlacename.setBackground(Color.WHITE);
		GridBagConstraints gbc_lblPlacename = new GridBagConstraints();
		gbc_lblPlacename.gridwidth = 2;
		gbc_lblPlacename.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlacename.gridx = 1;
		gbc_lblPlacename.gridy = 1;
		add(lblPlacename, gbc_lblPlacename);
		
		JClock clock = new JClock(schi.getStart(), schi.getEnd());
		GridBagConstraints gbc_clock = new GridBagConstraints();
		gbc_clock.gridwidth = 2;
		gbc_clock.insets = new Insets(0, 0, 5, 5);
		gbc_clock.fill = GridBagConstraints.BOTH;
		gbc_clock.gridx = 1;
		gbc_clock.gridy = 3;
		add(clock, gbc_clock);
		
		JLabel lblMinTemp = new JLabel(String.format("Min Temp %.0f\u00B0", iwbw.getWeatherMinTemperature()));
		lblMinTemp.setFont(SwingUtil.getFontNum().deriveFont(15f));
		if (iwbw.getWeatherMinTemperature() == -1*Float.MAX_VALUE) {
			lblMinTemp.setText("No Min Temp Data");
		}
		GridBagConstraints gbc_lblMinTemp = new GridBagConstraints();
		gbc_lblMinTemp.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinTemp.gridx = 1;
		gbc_lblMinTemp.gridy = 5;
		add(lblMinTemp, gbc_lblMinTemp);
		
		JLabel lblMaxTemp = new JLabel(String.format("Max Temp %.0f\u00B0", iwbw.getWeatherMaxTemperature()));
		lblMaxTemp.setFont(SwingUtil.getFontNum().deriveFont(15f));
		if (iwbw.getWeatherMaxTemperature() == -1*Float.MAX_VALUE) {
			lblMaxTemp.setText("No Max Temp Data");
		}
		
		JPanel minTempPanel = new JPanel();
		GridBagConstraints gbc_minTempPanel = new GridBagConstraints();
		gbc_minTempPanel.insets = new Insets(0, 0, 5, 5);
		gbc_minTempPanel.fill = GridBagConstraints.BOTH;
		gbc_minTempPanel.gridx = 1;
		gbc_minTempPanel.gridy = 5;
		add(minTempPanel, gbc_minTempPanel);
		GridBagLayout gbl_minTempPanel = new GridBagLayout();
		gbl_minTempPanel.columnWidths = new int[]{0};
		gbl_minTempPanel.rowHeights = new int[]{0};
		gbl_minTempPanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_minTempPanel.rowWeights = new double[]{Double.MIN_VALUE};
		minTempPanel.setLayout(gbl_minTempPanel);
		GridBagConstraints gbc_lblMaxTemp = new GridBagConstraints();
		gbc_lblMaxTemp.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxTemp.gridx = 2;
		gbc_lblMaxTemp.gridy = 5;
		add(lblMaxTemp, gbc_lblMaxTemp);
		
		JLabel lblProbabilityOfRain = new JLabel(String.format("Rain Chance: %.1f%%", iwbw.getWeatherMaxPrecipProb()));
		lblProbabilityOfRain.setFont(SwingUtil.getFontNum().deriveFont(15f));
		GridBagConstraints gbc_lblProbabilityOfRain = new GridBagConstraints();
		gbc_lblProbabilityOfRain.insets = new Insets(0, 0, 5, 5);
		gbc_lblProbabilityOfRain.gridx = 1;
		gbc_lblProbabilityOfRain.gridy = 7;
		add(lblProbabilityOfRain, gbc_lblProbabilityOfRain);
		
		JLabel lblMaximumRainIntensity = new JLabel(String.format("Rain intensity: %.1f mm/hr", iwbw.getWeatherMaxPrecipIntensity()));
		lblMaximumRainIntensity.setFont(SwingUtil.getFontNum().deriveFont(15f));
		GridBagConstraints gbc_lblMaximumRainIntensity = new GridBagConstraints();
		gbc_lblMaximumRainIntensity.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaximumRainIntensity.gridx = 2;
		gbc_lblMaximumRainIntensity.gridy = 7;
		add(lblMaximumRainIntensity, gbc_lblMaximumRainIntensity);
		
		IWeatherBlock cwb = br.sm.getScheduleItemWeather(sch, br.weatherProv, false, schi).getWeatherDataAt((lt2e.toEpoch(schi.getStart())+lt2e.toEpoch(schi.getEnd()))/2);
		
		if (cwb instanceof IWeatherBlockDayStats && ((IWeatherBlockDayStats) cwb).getWeatherSunrise() != 0) {
			LocalDateTime sunrise = Instant.ofEpochSecond(((IWeatherBlockDayStats) cwb).getWeatherSunrise())
					.atZone(ZoneId.of(BizrainConfig.INSTANCE.timeZoneId)).toLocalDateTime();
			JLabel lblSunriseTimes = new JLabel(String.format("Sunrise Time: %s", DateTimeFormatter.ofPattern("HH:mm").format(sunrise))); //TODO
			lblSunriseTimes.setFont(SwingUtil.getFontNum().deriveFont(15f));
			GridBagConstraints gbc_lblSunriseTimes = new GridBagConstraints();
			gbc_lblSunriseTimes.insets = new Insets(0, 0, 5, 5);
			gbc_lblSunriseTimes.gridx = 1;
			gbc_lblSunriseTimes.gridy = 9;
			add(lblSunriseTimes, gbc_lblSunriseTimes);
		}
		if (cwb instanceof IWeatherBlockDayStats && ((IWeatherBlockDayStats) cwb).getWeatherSunset() != 0) {
			LocalDateTime sunset = Instant.ofEpochSecond(((IWeatherBlockDayStats) cwb).getWeatherSunset())
					.atZone(ZoneId.of(BizrainConfig.INSTANCE.timeZoneId)).toLocalDateTime();
			JLabel lblSunsetTimes = new JLabel(String.format("Sunset Time: %s", DateTimeFormatter.ofPattern("HH:mm").format(sunset))); //TODO
			lblSunsetTimes.setFont(SwingUtil.getFontNum().deriveFont(15f));
			GridBagConstraints gbc_lblSunsetTimes = new GridBagConstraints();
			gbc_lblSunsetTimes.insets = new Insets(0, 0, 5, 5);
			gbc_lblSunsetTimes.gridx = 2;
			gbc_lblSunsetTimes.gridy = 9;
			add(lblSunsetTimes, gbc_lblSunsetTimes);
		}
		
		JPanel panelButtons = new JPanel();
		GridBagConstraints gbc_panelButtons = new GridBagConstraints();
		gbc_panelButtons.gridwidth = 2;
		gbc_panelButtons.insets = new Insets(0, 0, 5, 5);
		gbc_panelButtons.fill = GridBagConstraints.BOTH;
		gbc_panelButtons.gridx = 1;
		gbc_panelButtons.gridy = 11;
		add(panelButtons, gbc_panelButtons);
		GridBagLayout gbl_panelButtons = new GridBagLayout();
		gbl_panelButtons.columnWidths = new int[]{0, 0, 0};
		gbl_panelButtons.rowHeights = new int[]{0, 0};
		gbl_panelButtons.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelButtons.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelButtons.setLayout(gbl_panelButtons);
		
		JButton btnBack = new JButton("");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ret.ret();
			}
		});
		btnBack.setBorder(new RoundedBorder(30));
		btnBack.setBackground(Color.WHITE);
		btnBack.setIcon(new ImageIcon(PanelMoreInfo.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-chevron-left-16.png")));
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 0;
		gbc_btnBack.gridy = 0;
		panelButtons.add(btnBack, gbc_btnBack);
		
		JButton btnEdit = new JButton("");
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				br.setMainPanel(new PanelLocationEdit(br, sch, schi, (delete) -> {
						if (!delete) {
							br.setMainPanel(new PanelMoreInfo(br, sch, schi, lt2e, ret));
							br.sm.saveSchedules();
						} else {
							ret.ret();
						}
					}));
			}
		});
		btnEdit.setBorder(new RoundedBorder(30));
		btnEdit.setBackground(Color.WHITE);
		btnEdit.setIcon(new ImageIcon(PanelMoreInfo.class.getResource("/uk/ac/cam/bizrain/ui/ico/fa-pencil-16.png")));
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.gridx = 1;
		gbc_btnEdit.gridy = 0;
		panelButtons.add(btnEdit, gbc_btnEdit);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 12;
		add(verticalStrut, gbc_verticalStrut);

		
		
	}

}
