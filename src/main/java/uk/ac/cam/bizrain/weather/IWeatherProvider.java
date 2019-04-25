package uk.ac.cam.bizrain.weather;

/**
 * Represents a provider of weather information
 * 
 * @author btfs2
 *
 */
public interface IWeatherProvider {

	/**
	 * Gets the size of each weather block IN SECONDS
	 * 
	 * Note that doing second by second res is a bad idea
	 * 
	 * @return Size of weather
	 */
	public long getWeatherBlockSize();
	
	/**
	 * Verify that weather site is up and accessible
	 * 
	 * @return If provider is avaliable
	 */
	public boolean isWeatherAvaliable();
}