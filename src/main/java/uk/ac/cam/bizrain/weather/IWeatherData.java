package uk.ac.cam.bizrain.weather;

import java.util.List;

import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.weather.block.CombinedWeatherBlock;
import uk.ac.cam.bizrain.weather.block.IWeatherBlock;

public interface IWeatherData {

	public default IWeatherBlock getWeatherDataAt(long time) {
		return new CombinedWeatherBlock(getWeatherAllDataAt(time));
	}
	
	public List<IWeatherBlock> getWeatherAllDataAt(long time);
	
	public default Location getWeatherLocationAt(long time) {
		return getWeatherDataAt(time).getWeatherLocation();
	}
	
}