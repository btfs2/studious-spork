package uk.ac.cam.bizrain;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.ac.cam.bizrain.config.BizrainConfig;
import uk.ac.cam.bizrain.location.IGeocoder;
import uk.ac.cam.bizrain.location.photon.PhotonGeocoder;
import uk.ac.cam.bizrain.schedule.ScheduleManager;
import uk.ac.cam.bizrain.ui.PanelScheduleList;
import uk.ac.cam.bizrain.weather.IWeatherProvider;
import uk.ac.cam.bizrain.weather.darksky.DarkSkyWeatherProvider;

/**
 * Main of program
 * 
 * @author btfs2
 *
 */
public class Bizrain {

	//Swing shit
	private JFrame frame;
	private JPanel mainPanel;
	
	//Weather shit
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
		//Try to make text not shit
		System.setProperty("awt.useSystemAAFontSettings","on");
		System.setProperty("swing.aatext", "true");
		frame = new JFrame();
		//Use phone screen size; looks as would on IPhone, if scaled up 1.7x
		frame.setBounds(100, 100, (int)Math.floor(750f/1.7f), (int)Math.floor(1334f/1.7f));
		//Lock size
		frame.setResizable(false);
		//Dont leave phantom java open
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Setup main window
		mainPanel = new PanelScheduleList(this);
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
	}

	/**
	 * Gets current main panel
	 * 
	 * @return current main panel
	 */
	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	/**
	 * Sets current pain panel
	 * 
	 * @param newPanel new main panel
	 */
	public void setMainPanel(JPanel newPanel) {
		frame.getContentPane().remove(mainPanel);
		frame.getContentPane().add(newPanel, BorderLayout.CENTER);
		mainPanel = newPanel;
		//Ensure full redraw
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}
}
