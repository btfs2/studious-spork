package uk.ac.cam.bizrain.weather;

import java.util.List;

import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.weather.block.CombinedWeatherBlock;
import uk.ac.cam.bizrain.weather.block.IWeatherBlock;

public interface IWeatherData {

	public default IWeatherBlock getWeatherDataAt(long time) {
		List<IWeatherBlock> blocks = getWeatherAllDataAt(time);
		if (blocks == null) { return null; }
		return new CombinedWeatherBlock(blocks);
	}
	
	public List<IWeatherBlock> getWeatherAllDataAt(long time);
	
	public default Location getWeatherLocationAt(long time) {
		IWeatherBlock blk = getWeatherDataAt(time);
		if (blk == null) { return null; }
		return getWeatherDataAt(time).getWeatherLocation();
	}
	
}
