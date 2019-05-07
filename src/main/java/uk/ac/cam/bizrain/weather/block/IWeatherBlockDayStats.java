package uk.ac.cam.bizrain.weather.block;

/**
 * Defines a block with daywise weather stats, such as sunrise, sunset
 * 
 * @author btfs2
 *
 */
public interface IWeatherBlockDayStats extends IWeatherBlock {
	
	/**
	 * Get sunrise on day of block validity
	 * 
	 * <br><br><b>Unit: EPOCH SECONDS</b>
	 * 
	 * @return Sunrise of the day in epoch seconds
	 */
	public long getWeatherSunrise();
	
	/**
	 * Get sunset on day of block validity
	 * 
	 * <br><br><b>Unit: EPOCH SECONDS</b>
	 * 
	 * @return Sunset of the day in epoch seconds
	 */
	public long getWeatherSunset();
	
	/**
	 * Get the current fractional part of the lunation number
	 * 
	 * @return Fractional part of lunation number
	 */
	public float getLunarPhase();
	
}
