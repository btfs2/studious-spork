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
	
	public List<IWeatherBlock> getWeatherAllDataIn(long start, long end);
	
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
				blocks.stream()
				.filter(IWeatherBlockTempreture.class::isInstance)
				.map(i -> (IWeatherBlockTempreture)i)
				.mapToDouble(IWeatherBlockTempreture::getWeatherTemperature)
				.forEach(System.out::println);;
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
