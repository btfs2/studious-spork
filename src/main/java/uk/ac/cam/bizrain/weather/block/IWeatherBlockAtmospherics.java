package uk.ac.cam.bizrain.weather.block;

/**
 * Provides atmospheric infomation
 * 
 * @author btfs2
 *
 */
public interface IWeatherBlockAtmospherics extends IWeatherBlock {

	/**
	 * Get current atmospheric pressure 
	 * 
	 * <br><br><b>Unit: HPA</b>
	 * 
	 * @return
	 */
	public float getWeatherPressure();
	
	/**
	 * Get dewpoint
	 * 
	 * <br><br><b>Unit: Celsius</b>
	 * 
	 * @return Dewpoint in celcius
	 */
	public float getWeatherDewPoint();
	
	/**
	 * Get reletive humidity
	 * 
	 * <br><br><b>Unit: Reletive humidity (0, 1)</b>
	 * 
	 * @return reletive humidity
	 */
	public float getWeatherHumidity();
	
	/**
	 * Get the visibility
	 * 
	 * <br><br><b>Unit: temperature in Kilometers</b>
	 * 
	 * @return Visibility
	 */
	public float getWeatherVisibility();
	
}
