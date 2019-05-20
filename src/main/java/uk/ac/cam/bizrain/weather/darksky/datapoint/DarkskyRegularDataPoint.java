package uk.ac.cam.bizrain.weather.darksky.datapoint;

/**
 * An regular datapoint from darksky as defined in docs. Formed by having a load of the common stuff
 * 
 * @see <a href="https://darksky.net/dev/docs#response-format">Darksky Docs</a>
 * 
 * @author btfs2
 *
 */
public class DarkskyRegularDataPoint extends DarkskyDataPoint {

	//General
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
	//Sky
	public float cloudCover;
	public int uvIndex;
	public float visibility; 
}
