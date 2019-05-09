package uk.ac.cam.bizrain.weather.darksky.blocks;

import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.weather.IWeatherProvider;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockDayStats;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockPrecipitation;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockSummary;
import uk.ac.cam.bizrain.weather.darksky.datapoint.DarkskyDayDataPoint;

public class DarkSkyDayBlock implements IWeatherBlockPrecipitation, IWeatherBlockSummary, IWeatherBlockDayStats {

	IWeatherProvider provider;
	long time;
	float precipProb, precipIntensity, precipIntensityError;
	IPrecipType precipType = null;
	Location loc;
	IWeatherIcon icon;
	long sunrise, sunset;
	float lunation;

	public DarkSkyDayBlock(IWeatherProvider wp, Location l, DarkskyDayDataPoint dp) {
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
		if (dp.icon != null) {
			switch (dp.icon) {
			case "clear-day":
				icon = WeatherIcons.CLEAR_DAY;
				break;
			case "clear-night":
				icon = WeatherIcons.CLEAR_NIGHT;
				break;
			case "rain":
				icon = WeatherIcons.RAIN;
				break;
			case "snow":
				icon = WeatherIcons.SNOW;
				break;
			case "sleet":
				icon = WeatherIcons.SLEET;
				break;
			case "wind":
				icon = WeatherIcons.WIND;
				break;
			case "fog":
				icon = WeatherIcons.FOG;
				break;
			case "cloudy":
				icon = WeatherIcons.CLOUDY;
				break;
			case "partly-cloudy-day":
				icon = WeatherIcons.PARTLY_CLOUDY_DAY;
				break;
			case "partly-cloudy-night":
				icon = WeatherIcons.PARTLY_CLOUDY_NIGHT;
				break;
			}
		}
		sunrise = dp.sunriseTime;
		sunset = dp.sunsetTime;
		lunation = dp.moonPhase;
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
		return 60 * 60 * 24;
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
	public IWeatherIcon getWeatherIcon() {
		return icon;
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
		return lunation;
	}
}
