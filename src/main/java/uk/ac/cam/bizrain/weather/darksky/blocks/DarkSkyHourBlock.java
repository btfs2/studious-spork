package uk.ac.cam.bizrain.weather.darksky.blocks;

import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.weather.IWeatherProvider;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockPrecipitation;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockTempreture;
import uk.ac.cam.bizrain.weather.darksky.datapoint.DarkskyHourlyDataPoint;

public class DarkSkyHourBlock implements IWeatherBlockPrecipitation, IWeatherBlockTempreture {

	IWeatherProvider provider;
	long time;
	float precipProb, precipIntensity, precipIntensityError;
	IPrecipType precipType = null;
	Location loc;
	float temp, appTemp;
	
	public DarkSkyHourBlock(IWeatherProvider wp, Location l, DarkskyHourlyDataPoint dp) {
		provider = wp;
		time = dp.time;
		precipProb = dp.precipProbability;
		precipIntensity = dp.precipIntensity;
		precipIntensityError = dp.precipIntensityError;
		if (dp.precipType != null) {
			if (dp.precipType.equalsIgnoreCase("snow")) {
				precipType = PrecipType.SNOW;
			} else if (dp.precipType.equalsIgnoreCase("sleet")) {
				precipType = PrecipType.SLEET;
			} else {
				precipType = PrecipType.RAIN;
			}
		}
		loc = l;
		temp = dp.temperature;
		appTemp = dp.apparentTemperature;
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
		return 60*60;
	}

	@Override
	public Location getWeatherLocation() {
		return loc;
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
	public float getWeatherTemperature() {
		return temp;
	}
	
	@Override
	public float getWeatherAppTemperature() {
		return appTemp;
	}
}
