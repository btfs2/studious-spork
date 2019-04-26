package uk.ac.cam.bizrain.weather.darksky;

/**
 * A Daily datapoint from darksky as defined in docs. Formed by only having daily stuff
 * 
 * @see <a href="https://darksky.net/dev/docs#response-format">Darksky Docs</a>
 * 
 * @author btfs2
 *
 */
public class DarkskyDayDataPoint extends DarkskyRegularDataPoint {

	//Day info
	public long sunriseTime;
	public long sunsetTime;
	public float moonPhase;
	//Precipitation
	public float precipIntensityMax;
	public long precipIntensityMaxTime;
	//Temp Daytime
	public float temperatureHigh;
	public long temperatureHighTime;
	public float temperatureLow;
	public long temperatureLowTime;
	public float apparentTemperatureHigh;
	public long apparentTemperatureHighTime;
	public float apparentTemperatureLow;
	public long apparentTemperatureLowTime;
	//Wind
	public long windGustTime;
	//Sky
	public long uvIndexTime;
	public float visibility;
	//Total Temp
	public float temperatureMax;
	public long temperatureMaxTime;
	public float temperatureMin;
	public long temperatureMinTime;
	public float apparentTemperatureMax;
	public long apparentTemperatureMaxTime;
	public float apparentTemperatureMin;
	public long apparentTemperatureMinTime;
}
