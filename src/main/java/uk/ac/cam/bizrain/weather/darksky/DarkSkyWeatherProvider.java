package uk.ac.cam.bizrain.weather.darksky;

import com.google.gson.Gson;

import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.util.NetUtil;
import uk.ac.cam.bizrain.weather.IWeatherData;
import uk.ac.cam.bizrain.weather.IWeatherProvider;

/**
 * A Weather provider powered by darksky
 * 
 * See: https://darksky.net/dev
 * 
 * In production, an intermediate bouncer should be used to prevent distribution of API keys
 * 
 * But this isn't prod, so who cares
 * 
 * @author btfs2
 *
 */
public class DarkSkyWeatherProvider implements IWeatherProvider {

	String apiKey;
	
	/**
	 * Create a new provicer from the api key
	 * 
	 * Remember to not put the api key the internet 
	 * 
	 * @param apiKey API key for darksky
	 */
	public DarkSkyWeatherProvider(String apiKey) {
		this.apiKey = apiKey;
	}
	
	@Override
	public long getWeatherBlockSize() {
		// Min res = 60 seconds
		return 60;
	}

	@Override
	public boolean isWeatherAvaliable() {
		return NetUtil.pingURL("https://darksky.net/");
	}
	
	@Override
	public IWeatherData getWeatherDataFor(Location loc) {
		//Query data
		StringBuilder query = new StringBuilder("https://api.darksky.net/forecast/");
		query.append(apiKey);
		query.append("/");
		query.append(loc.getLat());
		query.append(",");
		query.append(loc.getLng());
		query.append("?units=si"); // Fix units to SI
		String body = NetUtil.httpBody(query.toString(), "GET", 20000, 7200000, false);
		Gson g = new Gson();
		DarkskyResponse res = g.fromJson(body, DarkskyResponse.class);
		return new DarkSkyWeatherData(this, res);
	}

}
