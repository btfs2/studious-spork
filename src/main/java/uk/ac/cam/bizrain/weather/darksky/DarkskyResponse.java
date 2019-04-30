package uk.ac.cam.bizrain.weather.darksky;

import java.util.List;

import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.weather.darksky.datapoint.DarkskyDataPoint;
import uk.ac.cam.bizrain.weather.darksky.datapoint.DarkskyDayDataPoint;
import uk.ac.cam.bizrain.weather.darksky.datapoint.DarkskyHourlyDataPoint;

/**
 * A standard response from darksky as defined in docs
 * 
 * @see <a href="https://darksky.net/dev/docs#response-format">Darksky Docs</a>
 * 
 * @author btfs2
 *
 */
public class DarkskyResponse {

	public class DarkskyDataBlock<T extends DarkskyDataPoint> {
		public List<T> data;
		public String summary;
		public String icon;
	}
	
	public float latitude;
	public float longitude;
	public String timezone;
	public DarkskyHourlyDataPoint currently;
	public DarkskyDataBlock<DarkskyDataPoint> minutely;
	public DarkskyDataBlock<DarkskyHourlyDataPoint> hourly;
	public DarkskyDataBlock<DarkskyDayDataPoint> daily;
	
	/**
	 * Gets location of request
	 * 
	 * @return Location of request
	 */
	public Location getLoc() {
		return new Location(latitude, longitude);
	}
	
}
