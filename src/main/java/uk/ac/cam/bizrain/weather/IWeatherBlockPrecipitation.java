package uk.ac.cam.bizrain.weather;

/**
 * A weather block that provides precipitation information
 * 
 * @author btfs2
 *
 */
public interface IWeatherBlockPrecipitation extends IWeatherBlock {

	/**
	 * Get the probability of precipitation in given block span
	 * 
	 * <br><br><b>Unit: Probability (i.e. float in (0,1) inclusive)</b>
	 * 
	 * @return Probability of precipitation 
	 */
	public float getWeatherPrecipProb();
	
	/**
	 * Get the predicted intensity of rain
	 * 
	 * <br><br><b>Unit: mm/hour</b>
	 * 
	 * @return predicted amount of rain in mm/hour
	 */
	public float getWeatherPrecipIntensity();
	
	/**
	 * The error bars on the predicted precipitation intensity
	 * 
	 * <br><br><b>Unit: mm/hour</b>
	 * 
	 * @return Error on predicted intensity
	 */
	public default float getWeatherPrecipIntensityErr() {
		return 0;
	}
	
	/**
	 * Describes some type of precipitation
	 * 
	 * Some have been defined for you
	 * 
	 * @see PrecipType
	 * 
	 * @author btfs2
	 *
	 */
	public interface IPrecipType {
		public String getPrecipTypeName();
	}
	
	/**
	 * Some predefined precipitation types
	 * 
	 * @author btfs2
	 */
	public enum PrecipType implements IPrecipType {
		Rain,
		Snow,
		Hail;
		
		public String getPrecipTypeName() {
			return name();
		}
	}
	
	/**
	 * Get the preditcted type of precipitation
	 * 
	 * <br><br><b>Unit: N/a</b>
	 * 
	 * @return Type of precipitation predicted
	 */
	public IPrecipType getWeatherPrecipType();
	
	
	
}
