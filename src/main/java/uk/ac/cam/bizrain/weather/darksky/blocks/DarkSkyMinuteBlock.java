package uk.ac.cam.bizrain.weather.darksky.blocks;

import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.weather.IWeatherBlockPrecipitation;
import uk.ac.cam.bizrain.weather.IWeatherProvider;
import uk.ac.cam.bizrain.weather.darksky.datapoint.DarkskyDataPoint;

/**
 * Data block for a minute of data
 * 
 * Darksky only provides minutely resolution for precip
 * 
 * @author btfs2
 *
 */
public class DarkSkyMinuteBlock implements IWeatherBlockPrecipitation {

	IWeatherProvider provider;
	long time;
	float precipProb, precipIntensity, precipIntensityError;
	IPrecipType precipType = PrecipType.RAIN;
	Location loc;
	
	public DarkSkyMinuteBlock(IWeatherProvider wp, Location l, DarkskyDataPoint dp) {
		provider = wp;
		time = dp.time;
		precipProb = dp.precipProbability;
		precipIntensity = dp.precipIntensity;
		precipIntensityError = dp.precipIntensityError;
		if (dp.precipType.equalsIgnoreCase("snow")) {
			precipType = PrecipType.SNOW;
		} else if (dp.precipType.equalsIgnoreCase("sleet")) {
			precipType = PrecipType.SLEET;
		}
		loc = l;
	}
	
	@Override
	public IWeatherProvider getWeatherProvider() {
		return provider;
	}

	@Override
	public long getWeatherTime() {
		return time;
	}
	
	@Override
	public long getWeatherLength() {
		return 60;
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
		return precipIntensityError;
	}
	
	@Override
	public IPrecipType getWeatherPrecipType() {
		return precipType;
	}

	@Override
	public Location getWeatherLocation() {
		return loc;
	}

}
