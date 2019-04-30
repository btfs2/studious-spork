package uk.ac.cam.bizrain.weather.block;

/**
 * A weather block that provides temprature data
 * 
 * @author btfs2
 *
 */
public interface IWeatherBlockTempreture extends IWeatherBlock {

	/**
	 * Get the true temperature in given time span
	 * 
	 * <br><br><b>Unit: temperature in Celsius</b>
	 * 
	 * null val: -1*Float.MAX_VALUE
	 * 
	 * @return True temperature
	 */
	public float getWeatherTemperature();
	
	/**
	 * Get the apparent temperature
	 * 
	 * <br><br><b>Unit: temperature in Celsius</b>
	 * 
	 * null val: -1*Float.MAX_VALUE
	 * 
	 * @return apparent temperature 
	 */
	public default float getWeatherAppTemperature() {
		return getWeatherTemperature();
	}
}
