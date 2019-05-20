package uk.ac.cam.bizrain.weather.block;

import java.util.List;
import java.util.logging.Logger;

import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.weather.IWeatherProvider;

/**
 * 
 * This merges the highest resolution data from multiple blocks
 * 
 * It powers a load of things
 * 
 * It is very fancy
 * 
 * It pre-fills it's own data from highest to lowest resolution for a time period, then uses those values
 * 
 * @author btfs2
 *
 */
public class CombinedWeatherBlock implements IWeatherBlock, IWeatherBlockPrecipitation, IWeatherBlockSummary, IWeatherBlockTempreture, IWeatherBlockDayStats {

	//Logger
	private static Logger LOG = Logger.getLogger("Weather"); 
	
	//Backing blocks
	List<IWeatherBlock> blocks;
	//ALL THE VARS; initialised to null values
	IWeatherProvider prov = null;
	long time = Long.MIN_VALUE;
	long extent = Long.MIN_VALUE;
	Location loc = null;
	float temp = -1*Float.MAX_VALUE;
	float apptemp = -1*Float.MAX_VALUE;
	String summary = null;
	IWeatherIcon icon = null;
	float precipProb = -1*Float.MAX_VALUE;
	float precipIntensity = -1*Float.MAX_VALUE;
	float precipIntensityErr = -1*Float.MAX_VALUE;
	IPrecipType precipType = null;
	long sunrise = Long.MIN_VALUE;
	long sunset = Long.MIN_VALUE;
	float luna = -1*Float.MAX_VALUE;
	
	/**
	 * Create a new combined datablock out of the passed blocks
	 * 
	 * @param blocks Blocks, sorted from high res to low res
	 */
	public CombinedWeatherBlock(List<IWeatherBlock> blocks) {
		this.blocks = blocks;
		init();
	}
	
	/**
	 * Initialise this blocks data; so we aren't continuously rescanning
	 */
	private void init() {
		for (IWeatherBlock iwb : blocks) {
			if (loc == null) {
				loc = iwb.getWeatherLocation();
			} else if (!loc.equals(iwb.getWeatherLocation())) {
				LOG.warning("Mismatched blocks combined" 
						+ "\n" + loc.toString() + "\n" + iwb.getWeatherLocation());
			}
			if (prov == null) {
				prov = iwb.getWeatherProvider();
			}
			if (time == Long.MIN_VALUE) {
				time = iwb.getWeatherTime();
			}
			if (extent == Long.MIN_VALUE) {
				extent = iwb.getWeatherLength();
			}
			if (iwb instanceof IWeatherBlockTempreture) {
				if (temp == -1*Float.MAX_VALUE) {
					temp = ((IWeatherBlockTempreture) iwb).getWeatherTemperature();
				//}
				//if (apptemp == -1*Float.MAX_VALUE) {
					apptemp = ((IWeatherBlockTempreture) iwb).getWeatherAppTemperature();
				}
			}
			if (iwb instanceof IWeatherBlockPrecipitation) {
				if (precipProb == -1*Float.MAX_VALUE) {
					precipProb = ((IWeatherBlockPrecipitation) iwb).getWeatherPrecipProb();
				//}
				//if (precipIntensity == -1*Float.MAX_VALUE) {
					precipIntensity = ((IWeatherBlockPrecipitation) iwb).getWeatherPrecipIntensity();
				//}
				//if (precipIntensityErr == -1*Float.MAX_VALUE) {
					precipIntensityErr = ((IWeatherBlockPrecipitation) iwb).getWeatherPrecipIntensityErr();
				//}
				//if (precipType == null) {
					precipType = ((IWeatherBlockPrecipitation) iwb).getWeatherPrecipType();
				}
			}
			if (iwb instanceof IWeatherBlockSummary) {
				if (summary == null) {
					summary = ((IWeatherBlockSummary) iwb).getWeatherSummary();
				}
				if (icon == null) {
					icon = ((IWeatherBlockSummary) iwb).getWeatherIcon();
				}
			}
			if (iwb instanceof IWeatherBlockDayStats) {
				if (luna == -1*Float.MAX_VALUE) {
					luna = ((IWeatherBlockDayStats) iwb).getLunarPhase();
				}
				if (sunrise == Long.MIN_VALUE) {
					sunrise = ((IWeatherBlockDayStats) iwb).getWeatherSunrise();
				}
				if (sunset == Long.MIN_VALUE) {
					sunset = ((IWeatherBlockDayStats) iwb).getWeatherSunset();
				}
			}
		}
	}
	
	@Override
	public float getWeatherTemperature() {
		return temp;
	}
	
	@Override
	public float getWeatherAppTemperature() {
		return apptemp;
	}

	@Override
	public String getWeatherSummary() {
		return summary;
	}

	@Override
	public IWeatherIcon getWeatherIcon() {
		return icon;
	}

	@Override
	public float getWeatherPrecipProb() {
		return precipProb;
	}

	@Override
	public float getWeatherPrecipIntensity() {
		return precipIntensity;
	}
	
	@Override
	public float getWeatherPrecipIntensityErr() {
		return precipIntensityErr;
	}

	@Override
	public IPrecipType getWeatherPrecipType() {
		return precipType;
	}

	@Override
	public IWeatherProvider getWeatherProvider() {
		return prov;
	}

	@Override
	public long getWeatherTime() {
		return time;
	}
	
	@Override
	public long getWeatherLength() {
		return extent;
	}
	
	@Override
	public Location getWeatherLocation() {
		return loc;
	}

	@Override
	public long getWeatherSunrise() {
		return sunrise;
	}

	@Override
	public long getWeatherSunset() {
		return sunset;
	}

	@Override
	public float getLunarPhase() {
		return luna;
	}

}
