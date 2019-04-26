package uk.ac.cam.bizrain.weather.darksky;

/**
 * A datapoint from darksky as defined in docs. Formed by only having stuff on every datapoint type
 * 
 * @see <a href="https://darksky.net/dev/docs#response-format">Darksky Docs</a>
 * 
 * @author btfs2
 *
 */
public class DarkskyDataPoint {
	
	//General
	public long time;
	//Rain
	public float precipIntensity;
	public float precipIntensityError;
	public float precipProbability;
	public String precipType;
}
