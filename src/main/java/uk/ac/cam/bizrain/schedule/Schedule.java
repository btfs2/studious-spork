package uk.ac.cam.bizrain.schedule;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A given schedule, store places contained, and times people are at palces
 * 
 * Doesn't care about overlap
 * 
 * @see ScheduleItem
 * @see ScheduleManager
 * 
 * @author btfs2
 *
 */
public class Schedule implements Serializable {

	/**
	 * Serialised
	 */
	private static final long serialVersionUID = -59247302811135435L;
	
	String scheduleName;
	
	//Exists for serialisation reasons
	@SuppressWarnings("unused")
	private Schedule() {}
	
	/**
	 * Create a new schedule with a given name
	 * 
	 * @param scheduleName
	 */
	public Schedule(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	
	List<ScheduleItem> items = new ArrayList<ScheduleItem>();
	
	/**
	 * Check if a schedule item overlaps with another
	 * 
	 * @param schi Item to check
	 * @return If item overlaps another that isn't itself
	 */
	public boolean doesOverlap(ScheduleItem schi) {
		return !items.stream().allMatch(i -> (i == schi) || ((
				(i.start.compareTo(schi.start) < 0 && i.end.compareTo(schi.start) < 0 
						&& i.start.compareTo(schi.end) < 0 && i.end.compareTo(schi.end) < 0)) ||
				((i.start.compareTo(schi.start) > 0 && i.end.compareTo(schi.start) > 0 
						&& i.start.compareTo(schi.end) > 0 && i.end.compareTo(schi.end) > 0))));
	}
	
	/**
	 * Add a schedule item
	 * @param schi item to add
	 */
	public void addScheduleItem(ScheduleItem schi) {
		items.add(schi);
		Collections.sort(items);
	}
	
	/**
	 * Remove item
	 * @param schi
	 */
	public void removeScheduleItem(ScheduleItem schi) {
		items.remove(schi);
		Collections.sort(items);
	}
	
	/**
	 * Get seperate list of all contained items
	 * @return
	 */
	public List<ScheduleItem> getItems() {
		return new ArrayList<ScheduleItem>(items);
	}
	
	/**
	 * Get the n longest places by time
	 * 
	 * @param n number of places to get
	 * @return List of items
	 */
	public List<ScheduleItem> getNLongestItems(int n) {
		Collections.sort(items, (i, j) -> {
			return -1*Long.compare(i.start.until(i.end, ChronoUnit.MINUTES), j.start.until(j.end, ChronoUnit.MINUTES));
		});
		List<ScheduleItem> si = new ArrayList<ScheduleItem>();
		for (int i = 0; i < Math.min(n, items.size()); i++) {
			si.add(items.get(i));
		}
		Collections.sort(items);
		return si;
	}
	
	/**
	 * Get schedule name
	 * 
	 * No shit
	 * 
	 * @return Name
	 */
	public String getScheduleName() {
		return scheduleName;
	}
	
	/**
	 * Get schedule start time
	 * 
	 * These functions are obvious
	 * 
	 * @return Start time, or LocalTime.MIN if no items are in schedule
	 */
	public LocalTime getStart() {
		if (items.size() == 0) {
			return LocalTime.MIN;
		}
		return items.get(0).getStart();
	}
	
	/**
	 * Does a dance...
	 * 
	 * Of course not, it returns the end time
	 * 
	 * @return End time, or LocalTime.MAX if no items are in schedule
	 */
	public LocalTime getEnd() {
		return items.stream().map(ScheduleItem::getEnd).max((i, j) -> i.compareTo(j)).orElse(LocalTime.MAX);
	}
	
}
