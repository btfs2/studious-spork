package uk.ac.cam.bizrain.schedule;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.cam.bizrain.location.IPlace;
import uk.ac.cam.bizrain.location.Location;

public class Schedule {

	String scheduleName;
	
	public class ScheduleItem implements Comparable<ScheduleItem> {
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
	
	public String getScheduleName() {
		return scheduleName;
	}
	
}
