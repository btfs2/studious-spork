package uk.ac.cam.bizrain.schedule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.ac.cam.bizrain.location.IPlace;
import uk.ac.cam.bizrain.weather.IWeatherData;
import uk.ac.cam.bizrain.weather.IWeatherProvider;
import uk.ac.cam.bizrain.weather.MergedWeatherData;
import uk.ac.cam.bizrain.weather.MergedWeatherData.Region;

/**
 * 
 * Stores multiple schedules, and maps schedules to weather
 * 
 * @author btfs2
 *
 */
public class ScheduleManager {
	
	/**
	 * Create a schedule manager with default path
	 */
	public ScheduleManager() {}
	
	/**
	 * Create a schedule manager with custom path
	 */
	public ScheduleManager(String schedulesPath) {
		this.schedulesPath = schedulesPath;
	}
	
	// Schedule stuff
	/////////////////
	
	List<Schedule> schedules = new ArrayList<Schedule>();
	transient List<Map<IPlace, IWeatherData>> schWeather = new ArrayList<Map<IPlace, IWeatherData>>();
	
	{
		// Length assurance
		while (schWeather.size() < schedules.size()) {
			schWeather.add(null);
		}
	}
	
	/**
	 * Get all schedules
	 * 
	 * @return Isolated copy of schedules
	 */
	public List<Schedule> getSchedules() {
		return new ArrayList<Schedule>(schedules);
	}
	
	/**
	 * The reason this exists entire type exists
	 * 
	 * Gets or (re)generates associated weather data
	 * 
	 * @param s Schedule to get data from
	 * @param iwp Weather provider for weather aquisition
	 * @param regen Weather to regen if data already exists, i.e. schedule has changed
	 * @return Weather data; or <tt>null</tt> if schedule not in list
	 */
	public Map<IPlace, IWeatherData> getScheduleWeather(Schedule s, IWeatherProvider iwp, boolean regen) {
		int i = schedules.indexOf(s);
		if (i == -1) {
			return null;
		} else {
			//TODO Refresh on timeout
			Map<IPlace, IWeatherData> iwdl;
			if (regen || i < schWeather.size() || schWeather.get(i) != null) {
				iwdl = new HashMap<IPlace, IWeatherData>();
				for (ScheduleItem si : s.getItems()) {
					iwdl.put(si.place, iwp.getWeatherDataFor(si.loc));
				}
				schWeather.add(i, iwdl);
			} else {
				iwdl = schWeather.get(i);
				for (ScheduleItem si : s.getItems()) {
					if (!iwdl.containsKey(si.place)) {
						iwdl.put(si.place, iwp.getWeatherDataFor(si.loc));
					}
				}
			}
			return iwdl;
		}
	}
	
	/**
	 * Gets combined weather data for a schedule
	 * 
	 * @see MergedWeatherData
	 * 
	 * @param s Schedule
	 * @param iwp Provider to pull weather from
	 * @param regen If data should be regenned
	 * @param lt2e Local time to epoch converter
	 * @return MergedWeatherData of all data on schedule
	 */
	public IWeatherData getScheduleCombinedWeather(Schedule s, IWeatherProvider iwp, boolean regen, LocalTimeToEpoch lt2e) {
		Map<IPlace, IWeatherData> dataMap = getScheduleWeather(s, iwp, regen);
		MergedWeatherData out = new MergedWeatherData();
		for (ScheduleItem si : s.getItems()) {
			out.addRegion(new Region(lt2e.toEpoch(si.start), lt2e.toEpoch(si.end), dataMap.get(si.place)));
		}
		return out;
	}
	
	
	/**
	 * Gets weather data for an item on a schedule
	 * 
	 * 
	 * @param s Schedule
	 * @param iwp Provider to pull weather from
	 * @param regen If data should be regenned
	 * @param si Item to get data for

	 */
	public IWeatherData getScheduleItemWeather(Schedule s, IWeatherProvider iwp, boolean regen, ScheduleItem si) {
		return getScheduleWeather(s, iwp, regen).get(si.place);
	}
	
	/**
	 * Add schedule to set
	 * 
	 * Also add associated null weather data to weather data listing
	 * 
	 * @param s Schedule to add
	 */
	public void addSchedule(Schedule s) {
		schedules.add(s);
		schWeather.add(null);
	}
	
	/**
	 * Remove a schedule and associated data
	 * 
	 * Last part is important
	 * 
	 * @param s Schedule to remove
	 * @return True if schedule existed in thing
	 */
	public boolean remSchedule(Schedule s) {
		int i = schedules.indexOf(s);
		if (i != -1) {
			schedules.remove(i);
			schWeather.remove(i);
			return true;
		}
		return false;
	}
	
	// Serialisation
	////////////////
	
	private static final Logger log = Logger.getLogger("Config");
	private String schedulesPath = "schedules.bin";
	
	{
		//Load on instantiation
		loadSchedules();
	}
	
	/**
	 * Save the data to given file
	 * 
	 * Uses javas built in serialisation
	 */
	public void saveSchedules() {
		File configFile = new File(schedulesPath);
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
				return;
			} catch (IOException e) {
				log.log(Level.WARNING, "Failed to create schedules", e);
			}
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(configFile))) {
			oos.writeObject(schedules);
			oos.flush();
		} catch (IOException e) {
			log.log(Level.WARNING, "Failed to save schedules", e);
		}
	}
	
	/**
	 * Load the data to given file
	 * 
	 * Uses javas built in serialisation
	 */
	@SuppressWarnings("unchecked") // cannot validate casting
	public void loadSchedules() {
		File configFile = new File(schedulesPath);
		if (configFile.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(configFile))) {
				Object o = ois.readObject();
				if (o instanceof List<?>) {
					schedules = (List<Schedule>) o;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			while (schWeather.size() < schedules.size()) {
				schWeather.add(null);
			}
		}
	}
	
}
