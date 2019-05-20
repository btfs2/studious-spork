package uk.ac.cam.bizrain.schedule;

import java.io.Serializable;
import java.time.LocalTime;

import uk.ac.cam.bizrain.location.IPlace;
import uk.ac.cam.bizrain.location.Location;

/**
 * An item in a schedule
 * 
 * @author btfs2
 *
 */
public class ScheduleItem implements Comparable<ScheduleItem>, Serializable {
	/**
	 * Serialised
	 */
	private static final long serialVersionUID = 7549934192264357008L;
	
	IPlace place;
	Location loc;
	LocalTime start;
	LocalTime end;
	
	/**
	 * Make new item
	 * 
	 * @param place Place to be
	 * @param loc Loc to be
	 * @param start Start of itme
	 * @param end End of item
	 */
	public ScheduleItem(IPlace place, Location loc, LocalTime start, LocalTime end) {
		super();
		this.place = place;
		this.loc = loc;
		this.start = start;
		this.end = end;
	}

	@Override
	public int compareTo(ScheduleItem o) {
		return start.compareTo(o.start);
	}
	
	/**
	 * Get place of item
	 * @return Place
	 */
	public IPlace getPlace() {
		return place;
	}
	
	/**
	 * Get end time of item
	 * @return End time
	 */
	public LocalTime getEnd() {
		return end;
	}
	
	/**
	 * Get start time of item
	 * @return Start time
	 */
	public LocalTime getStart() {
		return start;
	}
	
	/**
	 * Get location of item
	 * @return LatLong of item
	 */
	public Location getLoc() {
		return loc;
	}
	
	/**
	 * Update end time
	 * @param end New end time
	 */
	public void setEnd(LocalTime end) {
		this.end = end;
	}
	
	/**
	 * Update start time
	 * @param start New start time
	 */
	public void setStart(LocalTime start) {
		this.start = start;
	}
	
}