package uk.ac.cam.bizrain.ui.comp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSpinnerUI;

import uk.ac.cam.bizrain.weather.block.IWeatherBlockSummary;

/**
 * Various utilities for doing Swing operations
 * 
 * @author btfs2
 *
 */
public class SwingUtil {

	private static final Logger LOG = Logger.getLogger("SwingUtil");
	
	/**
	 * Used for fixing combobox borders
	 * 
	 * See: https://stackoverflow.com/questions/776893/remove-border-from-jcombobox
	 * 
	 * @param combo
	 */
	public static void fixCbBorder(JComboBox<?> combo) {
		synchronized (combo.getTreeLock()) {
		for (int i = 0; i < combo.getComponentCount(); i++) 
		{
		    if (combo.getComponent(i) instanceof JComponent) {
		        ((JComponent) combo.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
		    }


		    if (combo.getComponent(i) instanceof AbstractButton) {
		        ((AbstractButton) combo.getComponent(i)).setBorderPainted(false);
		    }
		}
		}
	}
	
	/**
	 * Removes controls from spinner
	 * 
	 * See: https://stackoverflow.com/questions/16284594/disable-up-and-down-arrow-buttons-on-jspinner
	 * 
	 * @param spinner
	 */
	public static void hideSpinnerArrow(JSpinner spinner) {
        Dimension d = spinner.getPreferredSize();
        d.width = 30;
        spinner.setUI(new BasicSpinnerUI() {
            protected Component createNextButton() {
                return null;
            }

            protected Component createPreviousButton() {
                return null;
            }
        });
        spinner.setPreferredSize(d);
    }
	
	/**
	 * Converts an IWeatherIcon into a UI icon
	 * 
	 * Not guarenteed to provide total coverage, but is meant to provide seperation of
	 * back end and front end (i.e. backing gives frontend IWeatherIcon, frontend actually
	 * finds appropriate file)
	 * 
	 * @param iwi Icon to find
	 * @return Swing icon for that file
	 */
	public static ImageIcon getIconOfWeather(IWeatherBlockSummary.IWeatherIcon iwi) {
		LOG.info("Parsing icon: " + (iwi != null ? iwi.getIconName() : "null"));
		if (iwi instanceof IWeatherBlockSummary.WeatherIcons) {
			switch ((IWeatherBlockSummary.WeatherIcons) iwi) {
			case RAIN:
				return new ImageIcon(SwingUtil.class.getResource("/uk/ac/cam/bizrain/ui/weather/cl-rain.png"));
			case CLEAR_DAY:
				return new ImageIcon(SwingUtil.class.getResource("/uk/ac/cam/bizrain/ui/weather/cl-sun.png"));
			case CLEAR_NIGHT:
				return new ImageIcon(SwingUtil.class.getResource("/uk/ac/cam/bizrain/ui/weather/cl-moon.png"));
			case CLOUDY:
				return new ImageIcon(SwingUtil.class.getResource("/uk/ac/cam/bizrain/ui/weather/cl-cloud.png"));
			case SNOW:
				return new ImageIcon(SwingUtil.class.getResource("/uk/ac/cam/bizrain/ui/weather/cl-snow.png"));
			case WIND:
				return new ImageIcon(SwingUtil.class.getResource("/uk/ac/cam/bizrain/ui/weather/cl-wind.png"));
			case PARTLY_CLOUDY_DAY:
				return new ImageIcon(SwingUtil.class.getResource("/uk/ac/cam/bizrain/ui/weather/cl-cloud-sun.png"));
			case PARTLY_CLOUDY_NIGHT:
				return new ImageIcon(SwingUtil.class.getResource("/uk/ac/cam/bizrain/ui/weather/cl-cloud-moon.png"));
			case FOG:
				return new ImageIcon(SwingUtil.class.getResource("/uk/ac/cam/bizrain/ui/weather/cl-fog.png"));
			default:
				break;
			}
		}
		return null;
	}
	
	/**
	 * Turn temerature into a color
	 * 
	 * Done by lots of ifs
	 * 
	 * TODO: Interpolate
	 * 
	 * @param temp Tempreture in Celsius
	 * @return Color for temp
	 */
	public static Color tempToColor(float temp) {
		if (temp < -5) {
			return Color.decode("0x70A0D2");
		}
		if (temp < 3) {
			return Color.decode("0x8ac3d8");
		}
		if (temp < 13) {
			return Color.decode("0xCAE5ED");
		}
		if (temp < 18) {
			return Color.decode("0xEDE0CA");
		}
		if (temp < 23) {
			return Color.decode("0xFFB680");
		}
		return Color.decode("0xFF5C3C");
	}
	
	/**
	 * Get the application title font
	 * 	
	 * @return title font
	 */
	public static Font getFontTitle() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, SwingUtil.class.getResourceAsStream("/uk/ac/cam/bizrain/ui/font/Raleway-Medium.ttf"));
		} catch (FontFormatException | IOException e) {
			LOG.log(Level.WARNING, "Failed to load: ", e);
		}
		return null;
	}
	
	/**
	 * Get the application body font
	 * 	
	 * @return body font
	 */
	public static Font getFontSub() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, SwingUtil.class.getResourceAsStream("/uk/ac/cam/bizrain/ui/font/Raleway-Regular.ttf"));
		} catch (FontFormatException | IOException e) {
			LOG.log(Level.WARNING, "Failed to load: ", e);
		}
		return null;
	}
	
	/**
	 * Get the application number font
	 * 
	 * Ends up being most of the body
	 * 	
	 * @return number font
	 */
	public static Font getFontNum() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, SwingUtil.class.getResourceAsStream("/uk/ac/cam/bizrain/ui/font/Roboto-Regular.ttf"));
		} catch (FontFormatException | IOException e) {
			LOG.log(Level.WARNING, "Failed to load: ", e);
		}
		return null;
	}
	
	
	
}
