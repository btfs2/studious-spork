package uk.ac.cam.bizrain.ui.comp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
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
	
	
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface DontTouchMe {}
	
	/**
	 * Theme a thing and all sub things
	 * 
	 * Uses ungodly amounts of reflection
	 * 
	 * Isn't the fastest, so is best suited for testing
	 * 
	 * @param comp Component to theme
	 */
	public static void theme(Component comp) {
		try {
			recursiveTheme(comp, new HashSet<Object>());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A recursive patching function for updating lots of things
	 * 
	 * In this case; theme patching
	 * 
	 * This IS taken from one of my other projects.
	 * 
	 * Next gen reflection hacking (it is in fact not; that is bytecode hacking)
	 * 
	 * @param o Object to patch
	 * @param dedupe Set of things already patched
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static void recursiveTheme(Object o, Set<Object> dedupe) throws IllegalArgumentException, IllegalAccessException {
		try {
		if (o == null || o.getClass() == null || o.getClass().getName() == null) {
			return;
		}
		if (o instanceof Number || o instanceof Boolean || o instanceof Class<?> || o instanceof String 
				|| o instanceof boolean[] || o instanceof char[] || o instanceof byte[] 
				|| o instanceof short[] || o instanceof int[] || o instanceof long[] 
				|| o instanceof float[] || o instanceof double[] || o.getClass().getSuperclass() == null) {
			return;
		}
		if (dedupe.contains(o)) {
			return;
		}
		dedupe.add(o);
		if (o.getClass().isArray() && !o.getClass().isPrimitive()) {
			for (Object p : (Object[]) o) {
				recursiveTheme(p, dedupe);
			}
			return;
		}
		if (!o.getClass().isAnnotationPresent(DontTouchMe.class)) {
			if (o instanceof Component) {
				((Component)o).setBackground(Color.WHITE);
				((Component)o).setForeground(Color.BLACK);
			}
			if (o instanceof JPanel) {
				((JPanel)o).setBackground(Color.LIGHT_GRAY);
			}
		}
		Class<?> c = o.getClass();
		for (Field f : c.getFields()) {
			if ((f.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
				continue;
			}
			f.setAccessible(true);
			recursiveTheme(f.get(o), dedupe);
		}
		while (c.getSuperclass() != null) {
			for (Field f : c.getDeclaredFields()) {
				if ((f.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
					continue;
				}
				f.setAccessible(true);
				recursiveTheme(f.get(o), dedupe);
			}
			c = c.getSuperclass();
		}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warning("Failed on class: " + o.getClass());
			LOG.warning("Class: Primitive: " + o.getClass().isPrimitive() + 
					" Array: " + o.getClass().isArray());
		}
	}
	
	public static Font getFontTitle() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, SwingUtil.class.getResourceAsStream("/uk/ac/cam/bizrain/ui/font/Raleway-Medium.ttf"));
		} catch (FontFormatException | IOException e) {
			LOG.log(Level.WARNING, "Failed to load: ", e);
		}
		return null;
	}
	
	public static Font getFontSub() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, SwingUtil.class.getResourceAsStream("/uk/ac/cam/bizrain/ui/font/Raleway-Regular.ttf"));
		} catch (FontFormatException | IOException e) {
			LOG.log(Level.WARNING, "Failed to load: ", e);
		}
		return null;
	}
	
	public static Font getFontNum() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, SwingUtil.class.getResourceAsStream("/uk/ac/cam/bizrain/ui/font/Roboto-Regular.ttf"));
		} catch (FontFormatException | IOException e) {
			LOG.log(Level.WARNING, "Failed to load: ", e);
		}
		return null;
	}
	
	
	
}
