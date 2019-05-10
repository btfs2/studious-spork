package uk.ac.cam.bizrain.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import uk.ac.cam.bizrain.config.BizrainConfig;

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
	
	/**
	 * Get default epoch converter that uses config
	 * 
	 * @return Default LT2E
	 */
	public static LocalTimeToEpoch getDefault() {
		return (lt) -> {
			//TODO TIMEZONES ARE PAIN
			//SEE https://docs.oracle.com/javase/tutorial/datetime/iso/timezones.html
			//BLOOD FOR THE BLOOD GOD
			//SKULS FOR THE SKULL THRONE
			//MILK FOR THE KHORNE FLAKES
			ZoneId zone = ZoneId.of(BizrainConfig.INSTANCE.timeZoneId);
		    ZonedDateTime zdt = LocalDateTime.now().atZone(zone);
		    ZoneOffset offset = zdt.getOffset();
			return LocalDateTime.of(LocalDate.now(), lt).toEpochSecond(offset);
		};
	}
}