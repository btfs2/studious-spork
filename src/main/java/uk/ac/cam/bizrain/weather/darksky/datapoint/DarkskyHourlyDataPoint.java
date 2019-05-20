package uk.ac.cam.bizrain.weather.darksky.datapoint;

/**
 * An hourly datapoint from darksky as defined in docs. Formed by only having hourly stuff
 * 
 * @see <a href="https://darksky.net/dev/docs#response-format">Darksky Docs</a>
 * 
 * @author btfs2
 *
 */
public class DarkskyHourlyDataPoint extends DarkskyRegularDataPoint {

	//Temp
	public float temperature;
	public float apparentTemperature;
}
