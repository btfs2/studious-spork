package uk.ac.cam.bizrain.weather.darksky.blocks;

import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.weather.IWeatherProvider;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockAtmospherics;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockPrecipitation;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockSummary;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockTempreture;
import uk.ac.cam.bizrain.weather.darksky.datapoint.DarkskyHourlyDataPoint;

public class DarkSkyHourBlock implements IWeatherBlockPrecipitation, IWeatherBlockTempreture, IWeatherBlockSummary, IWeatherBlockAtmospherics {

	IWeatherProvider provider;
	long time;
	float precipProb, precipIntensity, precipIntensityError;
	IPrecipType precipType = null;
	Location loc;
	float temp, appTemp;
	IWeatherIcon icon;
	float press, due, humid, vis;

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
		press = dp.pressure;
		humid = dp.humidity;
		vis = dp.visibility;
		due = dp.dewPoint;
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
		return 60 * 60;
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

	@Override
	public IWeatherIcon getWeatherIcon() {
		return icon;
	}

	@Override
	public float getWeatherPressure() {
		return press;
	}

	@Override
	public float getWeatherDewPoint() {
		return due;
	}

	@Override
	public float getWeatherHumidity() {
		return humid;
	}

	@Override
	public float getWeatherVisibility() {
		return vis;
	}
}
