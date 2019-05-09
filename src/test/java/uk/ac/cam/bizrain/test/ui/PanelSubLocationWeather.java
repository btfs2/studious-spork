package uk.ac.cam.bizrain.test.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.cam.bizrain.weather.IWeatherData;

public class PanelSubLocationWeather extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 173557214697435863L;

	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public PanelSubLocationWeather(IWeatherData weatherData) {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO
			}
		});
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 30, 0, 24, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 40, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblplaceName = new JLabel("[PLACE NAME]");
		GridBagConstraints gbc_lblplaceName = new GridBagConstraints();
		gbc_lblplaceName.gridwidth = 3;
		gbc_lblplaceName.insets = new Insets(0, 0, 5, 5);
		gbc_lblplaceName.gridx = 0;
		gbc_lblplaceName.gridy = 0;
		add(lblplaceName, gbc_lblplaceName);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridheight = 3;
		gbc_panel.gridwidth = 3;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 4;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{70, 0};
		gbl_panel.rowHeights = new int[]{14, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblIconLabel = new JLabel("");
		GridBagConstraints gbc_lblIconLabel = new GridBagConstraints();
		gbc_lblIconLabel.gridx = 0;
		gbc_lblIconLabel.gridy = 0;
		panel.add(lblIconLabel, gbc_lblIconLabel);
		Image img;
		try {
			img = ImageIO.read(ClassLoader.getSystemClassLoader().getResource("uk/ac/cam/bizrain/test/ui/fa-plus-128.png"));
			lblIconLabel.setIcon(new ImageIcon(img));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		JLabel lblplaceData = new JLabel("[Place Data 2]");
		GridBagConstraints gbc_lblplaceData = new GridBagConstraints();
		gbc_lblplaceData.gridwidth = 3;
		gbc_lblplaceData.insets = new Insets(0, 0, 5, 5);
		gbc_lblplaceData.gridx = 0;
		gbc_lblplaceData.gridy = 1;
		add(lblplaceData, gbc_lblplaceData);
		
		JLabel lblTemperature = new JLabel("Temperature");
		GridBagConstraints gbc_lblTemperature = new GridBagConstraints();
		gbc_lblTemperature.insets = new Insets(0, 0, 5, 5);
		gbc_lblTemperature.gridx = 0;
		gbc_lblTemperature.gridy = 3;
		add(lblTemperature, gbc_lblTemperature);
		
		JLabel lblWind = new JLabel("Wind");
		GridBagConstraints gbc_lblWind = new GridBagConstraints();
		gbc_lblWind.insets = new Insets(0, 0, 5, 5);
		gbc_lblWind.gridx = 2;
		gbc_lblWind.gridy = 3;
		add(lblWind, gbc_lblWind);
		
		JLabel lblFrom = new JLabel("From");
		GridBagConstraints gbc_lblFrom = new GridBagConstraints();
		gbc_lblFrom.insets = new Insets(0, 0, 5, 5);
		gbc_lblFrom.gridx = 5;
		gbc_lblFrom.gridy = 3;
		add(lblFrom, gbc_lblFrom);
		
		JLabel lbltime_1 = new JLabel("[time]");
		GridBagConstraints gbc_lbltime_1 = new GridBagConstraints();
		gbc_lbltime_1.insets = new Insets(0, 0, 5, 0);
		gbc_lbltime_1.gridx = 6;
		gbc_lbltime_1.gridy = 3;
		add(lbltime_1, gbc_lbltime_1);
		
		JLabel lblFeelsLike = new JLabel("Feels Like");
		GridBagConstraints gbc_lblFeelsLike = new GridBagConstraints();
		gbc_lblFeelsLike.insets = new Insets(0, 0, 0, 5);
		gbc_lblFeelsLike.gridx = 0;
		gbc_lblFeelsLike.gridy = 4;
		add(lblFeelsLike, gbc_lblFeelsLike);
		
		JLabel lblPrecipProb = new JLabel("Precipitation");
		lblPrecipProb.setToolTipText("Probability of rain/snow");
		GridBagConstraints gbc_lblPrecipProb = new GridBagConstraints();
		gbc_lblPrecipProb.insets = new Insets(0, 0, 0, 5);
		gbc_lblPrecipProb.gridx = 2;
		gbc_lblPrecipProb.gridy = 4;
		add(lblPrecipProb, gbc_lblPrecipProb);
		
		JLabel lblTo = new JLabel("To");
		GridBagConstraints gbc_lblTo = new GridBagConstraints();
		gbc_lblTo.insets = new Insets(0, 0, 0, 5);
		gbc_lblTo.gridx = 5;
		gbc_lblTo.gridy = 4;
		add(lblTo, gbc_lblTo);
		
		JLabel lbltime = new JLabel("[time2]");
		GridBagConstraints gbc_lbltime = new GridBagConstraints();
		gbc_lbltime.gridx = 6;
		gbc_lbltime.gridy = 4;
		add(lbltime, gbc_lbltime);
		
	}

}
