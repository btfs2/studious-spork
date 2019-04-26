package uk.ac.cam.bizrain.weather;

/**
 * Represents a provider of weather information
 * 
 * @author btfs2
 *
 */
public interface IWeatherProvider {

	/**
	 * Gets the default size of each weather block IN SECONDS
	 * 
	 * Note that doing second by second res is a bad idea
	 * 
	 * The seconds are as this is used in EPOCH time systems
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