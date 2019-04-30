package uk.ac.cam.bizrain.weather.darksky;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cam.bizrain.weather.IWeatherData;
import uk.ac.cam.bizrain.weather.block.IWeatherBlock;
import uk.ac.cam.bizrain.weather.darksky.blocks.DarkSkyHourBlock;
import uk.ac.cam.bizrain.weather.darksky.blocks.DarkSkyMinuteBlock;
import uk.ac.cam.bizrain.weather.darksky.datapoint.DarkskyDataPoint;
import uk.ac.cam.bizrain.weather.darksky.datapoint.DarkskyHourlyDataPoint;

public class DarkSkyWeatherData implements IWeatherData {

	List<DarkSkyMinuteBlock> minblk;
	List<DarkSkyHourBlock> hrblk;
	
	public DarkSkyWeatherData(DarkSkyWeatherProvider dsp, DarkskyResponse dsr) {
		minblk = new ArrayList<DarkSkyMinuteBlock>();
		for (DarkskyDataPoint mp : dsr.minutely.data) {
			minblk.add(
					new DarkSkyMinuteBlock(dsp, 
							dsr.getLoc(), mp));
		}
		hrblk = new ArrayList<DarkSkyHourBlock>();
		for (DarkskyHourlyDataPoint mp : dsr.hourly.data) {
			hrblk.add(new DarkSkyHourBlock(dsp, dsr.getLoc(), mp));
		}
		//TODO ADD DAYS
	}
	
	@Override
	public List<IWeatherBlock> getWeatherAllDataAt(long time) {
		List<IWeatherBlock> out = new ArrayList<IWeatherBlock>();
		IWeatherBlock thing = minblk.stream()
				.filter(b -> b.getWeatherTime() <= time && b.getWeatherTime() + b.getWeatherLength() >= time)
				.findFirst().orElse(null);
		if (thing != null) out.add(thing);
		thing = hrblk.stream()
				.filter(b -> b.getWeatherTime() <= time && b.getWeatherTime() + b.getWeatherLength() >= time)
				.findFirst().orElse(null);
		if (thing != null) out.add(thing);
		return out;
	}

}
