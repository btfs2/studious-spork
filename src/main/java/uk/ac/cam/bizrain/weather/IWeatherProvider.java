package uk.ac.cam.bizrain.weather;

import uk.ac.cam.bizrain.location.Location;

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
	
	/**
	 * Get as much weather data as is possible for a given location
	 * 
	 * TODO: Add past data collection to see historicals
	 * 
	 * @param loc Location to get weather for
	 * @return Current weather data for location
	 */
	public IWeatherData getWeatherDataFor(Location loc);
}