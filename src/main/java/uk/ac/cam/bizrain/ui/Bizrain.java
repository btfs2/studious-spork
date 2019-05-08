package uk.ac.cam.bizrain.ui;

import java.awt.EventQueue;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import uk.ac.cam.bizrain.config.BizrainConfig;
import uk.ac.cam.bizrain.location.IGeocoder;
import uk.ac.cam.bizrain.location.photon.PhotonGeocoder;
import uk.ac.cam.bizrain.schedule.Schedule;
import uk.ac.cam.bizrain.schedule.ScheduleManager;
import uk.ac.cam.bizrain.ui.comp.SwingUtil;
import uk.ac.cam.bizrain.weather.IWeatherData;
import uk.ac.cam.bizrain.weather.IWeatherProvider;
import uk.ac.cam.bizrain.weather.darksky.DarkSkyWeatherProvider;

import java.awt.BorderLayout;

public class Bizrain {

	private JFrame frame;
	private JPanel mainPanel;
	
	public ScheduleManager sm = new ScheduleManager();
	public IWeatherProvider weatherProv = new DarkSkyWeatherProvider(BizrainConfig.INSTANCE.darkSkyKey);
	public IGeocoder geocoder = new PhotonGeocoder();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Bizrain window = new Bizrain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Bizrain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 574);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new PanelLocationSearch(this, (place, loc, startTime, endTime) -> {
			System.out.println("Location entered: " + place.getDisplayName() + 
					"\nAt: " + loc.toString() + 
					"\nFrom:" + startTime.format(DateTimeFormatter.ofPattern("HH:mm")) + 
					"\nTo: " + endTime.format(DateTimeFormatter.ofPattern("HH:mm")));
			JOptionPane.showMessageDialog(null, "Location entered: " + place.getDisplayName() + 
					"\nAt: " + loc.toString() + 
					"\nFrom:" + startTime.format(DateTimeFormatter.ofPattern("HH:mm")) + 
					"\nTo: " + endTime.format(DateTimeFormatter.ofPattern("HH:mm")));
			IWeatherData iwd = weatherProv.getWeatherDataFor(loc);
			JPanel locPan = new PanelLocation(new Schedule.ScheduleItem(place, loc, startTime, endTime), iwd);
			setMainPanel(locPan);
		});
		//mainPanel = new PanelAddSchedule();
		//SwingUtil.theme(mainPanel);
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	public void setMainPanel(JPanel newPanel) {
		frame.getContentPane().remove(mainPanel);
		//SwingUtil.theme(newPanel);
		frame.getContentPane().add(newPanel, BorderLayout.CENTER);
		mainPanel = newPanel;
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}
}
