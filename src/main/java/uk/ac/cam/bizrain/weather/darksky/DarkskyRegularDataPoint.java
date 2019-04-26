package uk.ac.cam.bizrain.weather.darksky;

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
}
