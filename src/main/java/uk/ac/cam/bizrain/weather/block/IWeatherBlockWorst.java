package uk.ac.cam.bizrain.weather.block;

import uk.ac.cam.bizrain.weather.block.IWeatherBlockSummary.IWeatherIcon;

public interface IWeatherBlockWorst extends IWeatherBlock {

	/**
	 * Get the max temperature in given time span
	 * 
	 * <br><br><b>Unit: temperature in Celsius</b>
	 * 
	 * null val: -1*Float.MAX_VALUE
	 * 
	 * @return Max True temperature
	 */
	public float getWeatherMaxTemperature();
	
	/**
	 * Get the min temperature in given time span
	 * 
	 * <br><br><b>Unit: temperature in Celsius</b>
	 * 
	 * null val: -1*Float.MAX_VALUE
	 * 
	 * @return Min True temperature
	 */
	public float getWeatherMinTemperature();
	
	/**
	 * Get maximum precip prob in timespan
	 * 
	 * <br><br><b>Unit: Probability (i.e. float in (0,1) inclusive)</b>
	 * 
	 * null val: -1*Float.MAX_VALUE
	 * 
	 * @return Max probability of precipitation 
	 */
	public float getWeatherMaxPrecipProb();
	
	/**
	 * Get the predicted max intensity of rain
	 * 
	 * <br><br><b>Unit: mm/hour</b>
	 * 
	 * null val: -1*Float.MAX_VALUE
	 * 
	 * @return Max predicted amount of rain in mm/hour
	 */
	public float getWeatherMaxPrecipIntensity();
	
	/**
	 * Returns an icon to be displayed to the user
	 * 
	 * @return Icon for user
	 */
	public IWeatherIcon getWeatherWorstIcon();
	
}
