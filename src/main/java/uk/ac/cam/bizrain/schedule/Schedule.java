package uk.ac.cam.bizrain.schedule;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.cam.bizrain.location.IPlace;
import uk.ac.cam.bizrain.location.Location;

public class Schedule implements Serializable {

	/**
	 * Serialised
	 */
	private static final long serialVersionUID = -59247302811135435L;
	
	String scheduleName;
	
	@SuppressWarnings("unused")
	private Schedule() {}
	
	public Schedule(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	
	public static class ScheduleItem implements Comparable<ScheduleItem>, Serializable {
		/**
		 * Serialised
		 */
		private static final long serialVersionUID = 7549934192264357008L;
		
		IPlace place;
		Location loc;
		LocalTime start;
		LocalTime end;
		
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
		
		public IPlace getPlace() {
			return place;
		}
		
		public LocalTime getEnd() {
			return end;
		}
		
		public LocalTime getStart() {
			return start;
		}
		
		public Location getLoc() {
			return loc;
		}
		
	}
	
	List<ScheduleItem> items = new ArrayList<ScheduleItem>();
	
	public boolean doesOverlap(ScheduleItem schi) {
		return items.stream().anyMatch(i -> i.start.compareTo(schi.start) < 0 && i.start.compareTo(schi.end) > 0);
	}
	
	public void addScheduleItem(ScheduleItem schi) {
		items.add(schi);
		Collections.sort(items);
	}
	
	public List<ScheduleItem> getItems() {
		return new ArrayList<ScheduleItem>(items);
	}
	
	public List<ScheduleItem> getNLongestItems(int n) {
		Collections.sort(items, (i, j) -> {
			return -1*Long.compare(i.start.until(i.end, ChronoUnit.MINUTES), j.start.until(j.end, ChronoUnit.MINUTES));
		});
		List<ScheduleItem> si = new ArrayList<Schedule.ScheduleItem>();
		for (int i = 0; i < Math.min(n, items.size()); i++) {
			si.add(items.get(i));
		}
		Collections.sort(items);
		return si;
	}
	
	public String getScheduleName() {
		return scheduleName;
	}
	
	public LocalTime getStart() {
		if (items.size() == 0) {
			return LocalTime.MIN;
		}
		return items.get(0).getStart();
	}
	
	public LocalTime getEnd() {
		return items.stream().map(ScheduleItem::getEnd).max((i, j) -> i.compareTo(j)).orElse(LocalTime.MAX);
	}
	
}
