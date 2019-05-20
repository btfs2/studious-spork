package uk.ac.cam.bizrain.weather;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.weather.block.CombinedWeatherBlock;
import uk.ac.cam.bizrain.weather.block.IWeatherBlock;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockPrecipitation;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockSummary;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockSummary.IWeatherIcon;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockTempreture;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockWorst;

/**
 * A class that contains weather data
 * 
 * I.e. a large amount of IWeatherBlocks and subtypes
 * 
 * @see IWeatherBlock
 * 
 * @author btfs2
 *
 */
public interface IWeatherData {

	/**
	 * Get combination of all weather data at given time
	 * 
	 * @see CombinedWeatherBlock
	 * @param time Epoch time to check
	 * @return Combined weather data at time given
	 */
	public default IWeatherBlock getWeatherDataAt(long time) {
		List<IWeatherBlock> blocks = getWeatherAllDataAt(time);
		if (blocks == null) { return null; }
		return new CombinedWeatherBlock(blocks);
	}
	
	/**
	 * Get all the stored weather data at time
	 * 
	 * Should be ordered from highest resolution to lowest resolution/relevence
	 * 
	 * @param time Time to check in unix epoch
	 * @return All weather data at time, sorted by resolution/relevence
	 */
	public List<IWeatherBlock> getWeatherAllDataAt(long time);
	
	/**
	 * Get the location of this weather data at a given time
	 * 
	 * If at multiple, return the most relevent one
	 * 
	 * @param time Time to check in EPOCH time
	 * @return Location the data is from at a time
	 */
	public default Location getWeatherLocationAt(long time) {
		IWeatherBlock blk = getWeatherDataAt(time);
		if (blk == null) { return null; }
		return getWeatherDataAt(time).getWeatherLocation();
	}
	
	/**
	 * Get all data in a period
	 * 
	 * @param start Epoch time of start
	 * @param end Epoch time of end
	 * @return All data valid in period
	 */
	public List<IWeatherBlock> getWeatherAllDataIn(long start, long end);
	
	/**
	 * Get a worst case weather block over a period
	 *  
	 * @param start Start of period in Epoch
	 * @param end End of time in epoch
	 * @return Worst case data within period
	 */
	public default IWeatherBlockWorst getWeatherWorstIn(long start, long end) {
		List<IWeatherBlock> blocks = getWeatherAllDataIn(start, end);
		return new IWeatherBlockWorst() {

			@Override
			public long getWeatherTime() {
				return start;
			}
			
			@Override
			public long getWeatherLength() {
				return end-start;
			}
			
			@Override
			public IWeatherProvider getWeatherProvider() {
				return blocks.get(0).getWeatherProvider();
			}
			
			@Override
			public Location getWeatherLocation() {
				return blocks.get(0).getWeatherLocation();
			}
			
			@Override
			public float getWeatherMinTemperature() {
				return (float) blocks.stream()
						.filter(IWeatherBlockTempreture.class::isInstance)
						.map(i -> (IWeatherBlockTempreture)i)
						.mapToDouble(IWeatherBlockTempreture::getWeatherTemperature)
						.min().orElse(-1*Float.MAX_VALUE);
			}
			
			@Override
			public float getWeatherMaxTemperature() {
				return (float) blocks.stream()
						.filter(IWeatherBlockTempreture.class::isInstance)
						.map(i -> (IWeatherBlockTempreture)i)
						.mapToDouble(IWeatherBlockTempreture::getWeatherTemperature)
						.max().orElse(-1*Float.MAX_VALUE);
			}
			
			@Override
			public float getWeatherMaxPrecipProb() {
				return (float) blocks.stream()
						.filter(IWeatherBlockPrecipitation.class::isInstance)
						.map(i -> (IWeatherBlockPrecipitation)i)
						.mapToDouble(IWeatherBlockPrecipitation::getWeatherPrecipProb)
						.max().orElse(-1*Float.MAX_VALUE);
			}
			
			@Override
			public float getWeatherMaxPrecipIntensity() {
				return (float) blocks.stream()
						.filter(IWeatherBlockPrecipitation.class::isInstance)
						.map(i -> (IWeatherBlockPrecipitation)i)
						.mapToDouble(IWeatherBlockPrecipitation::getWeatherPrecipIntensity)
						.max().orElse(-1*Float.MAX_VALUE);
			}

			@Override
			public IWeatherIcon getWeatherWorstIcon() {
				List<IWeatherIcon> icons = blocks.stream()
				.filter(IWeatherBlockSummary.class::isInstance)
				.map(i -> (IWeatherBlockSummary)i)
				.map(IWeatherBlockSummary::getWeatherIcon)
				.collect(Collectors.toList());
				if (icons.size() > 0) {
					Collections.sort(icons, Comparator.comparingInt(IWeatherIcon::getIconPriority).reversed());
					return icons.get(0);
				} else {
					return null;
				}
			}
		};
	}
}
