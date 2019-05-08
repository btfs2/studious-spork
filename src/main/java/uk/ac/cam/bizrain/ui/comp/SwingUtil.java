package uk.ac.cam.bizrain.ui.comp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;

import uk.ac.cam.bizrain.ui.PanelLocation;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockSummary;

public class SwingUtil {

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
			System.out.println(combo.getComponent(i).getClass().getCanonicalName());
		    if (combo.getComponent(i) instanceof JComponent) {
		        ((JComponent) combo.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
		    }


		    if (combo.getComponent(i) instanceof AbstractButton) {
		        ((AbstractButton) combo.getComponent(i)).setBorderPainted(false);
		    }
		}
		}
	}
	
	public static ImageIcon getImageIcon(String classpath) throws IOException  {
		byte[] out = new byte[16];
		InputStream is = SwingUtil.class.getResourceAsStream(classpath);
		int idx = 0;
		while (is.available() > 0) {
			out[idx++] = (byte) is.read();
			if (idx == out.length) {
				out = Arrays.copyOf(out, out.length*2);
			}
		}
		out = Arrays.copyOf(out, idx);
		return new ImageIcon(out);
	}
	
	public static ImageIcon getIconOfWeather(IWeatherBlockSummary.IWeatherIcon iwi) {
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
			}
		}
		return null;
	}
}
