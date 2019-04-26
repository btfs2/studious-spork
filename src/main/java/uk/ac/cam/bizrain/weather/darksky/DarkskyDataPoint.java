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
	public String summary;
	public String icon;
	//Air
	public float dewPoint;
	public float humidity;
	public float pressure;
	//Wind
	public float windSpeed;
	public float windGust;
	public int windBearing;
	//Rain
	public float precipIntensity;
	public float precipProbability;
	public String precipType;
	//Sky
	public float cloudCover;
	public int uvIndex;
}
