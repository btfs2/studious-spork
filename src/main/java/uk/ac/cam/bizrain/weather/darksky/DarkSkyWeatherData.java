package uk.ac.cam.bizrain.weather.darksky;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cam.bizrain.weather.IWeatherData;
import uk.ac.cam.bizrain.weather.block.IWeatherBlock;
import uk.ac.cam.bizrain.weather.darksky.blocks.DarkSkyDayBlock;
import uk.ac.cam.bizrain.weather.darksky.blocks.DarkSkyHourBlock;
import uk.ac.cam.bizrain.weather.darksky.blocks.DarkSkyMinuteBlock;
import uk.ac.cam.bizrain.weather.darksky.datapoint.DarkskyDataPoint;
import uk.ac.cam.bizrain.weather.darksky.datapoint.DarkskyDayDataPoint;
import uk.ac.cam.bizrain.weather.darksky.datapoint.DarkskyHourlyDataPoint;

/**
 * Darksky weather data
 * 
 * Just auto-parses from the backing response
 * 
 * @author btfs2
 *
 */
public class DarkSkyWeatherData implements IWeatherData {

	List<DarkSkyMinuteBlock> minblk;
	List<DarkSkyHourBlock> hrblk;
	List<DarkSkyDayBlock> dblk;
	
	/**
	 * Create a new one from a given provider and response
	 * 
	 * @param dsp DarkSky Provider from which data came
	 * @param dsr DarkSky response to take data from
	 */
	public DarkSkyWeatherData(DarkSkyWeatherProvider dsp, DarkskyResponse dsr) {
		minblk = new ArrayList<DarkSkyMinuteBlock>();
		if (dsr.minutely != null && dsr.minutely.data != null) {
			for (DarkskyDataPoint mp : dsr.minutely.data) {
				minblk.add(
						new DarkSkyMinuteBlock(dsp, 
								dsr.getLoc(), mp));
			}
		}
		hrblk = new ArrayList<DarkSkyHourBlock>();
		if (dsr.hourly != null && dsr.hourly.data != null) {
			for (DarkskyHourlyDataPoint mp : dsr.hourly.data) {
				hrblk.add(new DarkSkyHourBlock(dsp, dsr.getLoc(), mp));
			}
		}
		dblk = new ArrayList<DarkSkyDayBlock>();
		if (dsr.daily != null && dsr.daily.data != null) {
			for (DarkskyDayDataPoint mp : dsr.daily.data) {
				dblk.add(new DarkSkyDayBlock(dsp, dsr.getLoc(), mp));
			}
		}
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
		thing = dblk.stream()
				.filter(b -> b.getWeatherTime() <= time && b.getWeatherTime() + b.getWeatherLength() >= time)
				.findFirst().orElse(null);
		if (thing != null) out.add(thing);
		return out;
	}

	@Override
	public List<IWeatherBlock> getWeatherAllDataIn(long start, long end) {
		List<IWeatherBlock> out = new ArrayList<IWeatherBlock>();
		minblk.stream().filter(b -> b.getWeatherTime() <= end && b.getWeatherTime() + b.getWeatherLength() >= start)
		.forEach(out::add);
		hrblk.stream().filter(b -> b.getWeatherTime() <= end && b.getWeatherTime() + b.getWeatherLength() >= start)
		.forEach(out::add);
		dblk.stream().filter(b -> b.getWeatherTime() <= end && b.getWeatherTime() + b.getWeatherLength() >= start)
		.forEach(out::add);
		return out;
	}

}
