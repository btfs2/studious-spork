package uk.ac.cam.bizrain.schedule;

import java.time.LocalTime;

/**
 * A function that takes in a local time and converts it to epoch
 * @author btfs2
 *
 */
public interface LocalTimeToEpoch {
	
	/**
	 * Convert the time to epoch
	 * 
	 * How is not specified
	 * 
	 * @param lt Time to convert
	 * @return Time in epoch time SOMEHOW
	 */
	public long toEpoch(LocalTime lt);
}