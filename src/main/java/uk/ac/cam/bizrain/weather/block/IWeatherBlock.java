package uk.ac.cam.bizrain.weather.block;

import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.weather.IWeatherProvider;

/**
 * Defines some block of weather data
 * 
 * @author btfs2
 */
public interface IWeatherBlock {

	/**
	 * Get the weather provider that produced this block
	 * 
	 * @return Provider that made this block
	 */
	public IWeatherProvider getWeatherProvider();
	
	/**
	 * Get the length for which the described data is valid
	 * 
	 * <br><br><b>Unit: SECONDS</b>
	 * 
	 * @return Length of time in seconds for which data is valid 
	 */
	public default long getWeatherLength() {
		return getWeatherProvider().getWeatherBlockSize();
	}
	
	/**
	 * Get the time at which this block begins.
	 * 
	 * I.e. this block will describe the weather from returned value untill blocksize later
	 * 
	 * <br><br><b>Unit: EPOCH SECONDS</b>
	 * 
	 * @return Start time of block validity
	 */
	public long getWeatherTime();
	
	/**
	 * Get the location of this weather sample
	 * 
	 * @return Location of this weather data
	 */
	public Location getWeatherLocation();
}
