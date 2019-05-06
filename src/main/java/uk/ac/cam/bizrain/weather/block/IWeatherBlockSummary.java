package uk.ac.cam.bizrain.weather.block;

/**
 * Describes a weather block that provides a summary of the data
 * 
 * @author btfs2
 */
public interface IWeatherBlockSummary {

	/**
	 * Get the forcast summary
	 * 
	 * null implies one doesn't exist
	 * 
	 * @return Human readable forcast
	 */
	public default String getWeatherSummary() {
		return null;
	}
	
	interface IWeatherIcon {
		public String getIconName();
	}
	
	enum WeatherIcons implements IWeatherIcon {
		CLEAR_DAY,
		CLEAR_NIGHT,
		RAIN,
		SNOW,
		SLEET,
		WIND,
		FOG,
		CLOUDY,
		PARTLY_CLOUDY_DAY,
		PARTLY_CLOUDY_NIGHT;

		@Override
		public String getIconName() {return name();}
		
	}
	
	/**
	 * Returns an icon to be displayed to the user
	 * 
	 * @return Icon for user
	 */
	public IWeatherIcon getWeatherIcon();
}
