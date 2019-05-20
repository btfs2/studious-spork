package uk.ac.cam.bizrain.weather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import uk.ac.cam.bizrain.weather.block.IWeatherBlock;

/**
 * A merged set of weather data
 * 
 * Done on a region by region basis; with shortest region first
 * 
 * @author btfs2
 *
 */
public class MergedWeatherData implements IWeatherData {

	/**
	 * A region operated on
	 * 
	 * Has a start point, and an end point
	 * 
	 * Compares first on temporal linearity; then on length
	 * 
	 * @author btfs2
	 *
	 */
	public static class Region implements Comparable<Region> {

		long start, end;
		IWeatherData iwd;
		
		/**
		 * Create a region with some data, a start point and an end point
		 * 
		 * @param start
		 * @param end
		 * @param iwd
		 */
		public Region(long start, long end, IWeatherData iwd) {
			this.start = start;
			this.end = end;
			this.iwd = iwd;
		}
		
		@Override
		public int compareTo(Region o) {
			if (o.start > end) {
				return 1;
			} else if (o.end < start) {
				return -1;
			} else {
				return Long.compare(end-start, o.end-o.start);
			}
		}
		
		/**
		 * Determine if a time is before, after, or inside this region
		 * 
		 * @param o Long to look at
		 * @return 1 if o is after; -1 if o is before, and 0 if o is within
		 */
		public int compareTo(Long o) {
			if (o > end) {
				return 1;
			} else if (o < start) {
				return -1;
			} else {
				return 0;
			}
		}
		
		/**
		 * Get the weather data for this region
		 * @return
		 */
		public IWeatherData getIwd() {
			return iwd;
		}
	}
	
	List<Region> providers = new ArrayList<>();
	
	/**
	 * Create a new merged weather data with a given set of regions
	 * 
	 * @param regs Regions
	 */
	public MergedWeatherData(Region... regs) {
		providers.addAll(Arrays.asList(regs));
		Collections.sort(providers);
	}
	
	/**
	 * Create an empty one
	 */
	public MergedWeatherData() {}
	
	/**
	 * Add a region to this
	 * 
	 * @param r region to add
	 */
	public void addRegion(Region r) {
		providers.add(r);
		Collections.sort(providers);
	}
	
	@Override
	public List<IWeatherBlock> getWeatherAllDataAt(long time) {
		Region iwd = providers.stream().filter(i -> i.compareTo(time) == 0).findFirst().orElse(null);
		if (iwd != null) {
			return iwd.getIwd().getWeatherAllDataAt(time);
		} else { return null; }
	}

	@Override
	public List<IWeatherBlock> getWeatherAllDataIn(long start, long end) {
		//HAIL STREAMS
		//MAGIC
		return providers.stream().filter(i -> i.end > start || i.start < end)
				.flatMap(i->i.iwd.getWeatherAllDataIn(Math.max(i.start, start), Math.min(i.end, end)).stream())
				.collect(Collectors.toList());
	}

}
