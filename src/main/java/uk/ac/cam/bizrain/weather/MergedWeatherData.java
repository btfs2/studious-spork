package uk.ac.cam.bizrain.weather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import uk.ac.cam.bizrain.weather.block.IWeatherBlock;

public class MergedWeatherData implements IWeatherData {

	class Region implements Comparable<Region> {

		long start, end;
		IWeatherData iwd;
		
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
		
		public int compareTo(Long o) {
			if (o > end) {
				return 1;
			} else if (o < start) {
				return -1;
			} else {
				return 0;
			}
		}
		
		public IWeatherData getIwd() {
			return iwd;
		}
	}
	
	List<Region> providers = new ArrayList<>();
	
	public MergedWeatherData(Region... regs) {
		providers.addAll(Arrays.asList(regs));
		Collections.sort(providers);
	}
	
	public MergedWeatherData() {}
	
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
		// TODO Auto-generated method stub
		return null;
	}

}
