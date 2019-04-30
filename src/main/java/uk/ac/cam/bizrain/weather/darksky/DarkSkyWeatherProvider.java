package uk.ac.cam.bizrain.weather.darksky;

import com.google.gson.Gson;

import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.util.NetUtil;
import uk.ac.cam.bizrain.weather.IWeatherData;
import uk.ac.cam.bizrain.weather.IWeatherProvider;

public class DarkSkyWeatherProvider implements IWeatherProvider {

	String apiKey;
	
	public DarkSkyWeatherProvider(String apiKey) {
		this.apiKey = apiKey;
	}
	
	@Override
	public long getWeatherBlockSize() {
		return 60;
	}

	@Override
	public boolean isWeatherAvaliable() {
		return NetUtil.pingURL("https://darksky.net/");
	}

	@Override
	public IWeatherData getWeatherDataFor(Location loc) {
		StringBuilder query = new StringBuilder("https://api.darksky.net/forecast/");
		query.append(apiKey);
		query.append("/");
		query.append(loc.getLat());
		query.append(",");
		query.append(loc.getLng());
		String body = NetUtil.httpBody(query.toString(), "GET", 20000, 6000000, false);
		Gson g = new Gson();
		DarkskyResponse res = g.fromJson(body, DarkskyResponse.class);
		//TODO finish
		return null;
	}

}
