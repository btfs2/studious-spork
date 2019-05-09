package uk.ac.cam.bizrain.schedule;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import uk.ac.cam.bizrain.location.IPlace;
import uk.ac.cam.bizrain.schedule.Schedule.ScheduleItem;
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
			if (regen || i < schWeather.size() || schWeather.get(i) != null) {
				Map<IPlace, IWeatherData> iwdl = new HashMap<IPlace, IWeatherData>();
				for (ScheduleItem si : s.getItems()) {
					iwdl.put(si.place, iwp.getWeatherDataFor(si.loc));
				}
				schWeather.add(i, iwdl);
			}
			return schWeather.get(i);
		}
	}
	
	public IWeatherData getScheduleCombinedWeather(Schedule s, IWeatherProvider iwp, boolean regen, LocalTimeToEpoch lt2e) {
		Map<IPlace, IWeatherData> dataMap = getScheduleWeather(s, iwp, regen);
		MergedWeatherData out = new MergedWeatherData();
		for (ScheduleItem si : s.getItems()) {
			out.addRegion(new Region(lt2e.toEpoch(si.start), lt2e.toEpoch(si.end), dataMap.get(si.place)));
		}
		return out;
	}
	
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
	private String schedulesPath = "schedules.json";
	
	{
		//Load on instantiation
		loadSchedules();
	}
	
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
		Gson g = new Gson();
		try (Writer r = new FileWriter(configFile)) {
			g.toJson(schedules, r);
		} catch (IOException e) {
			log.log(Level.WARNING, "Failed to save schedules", e);
		}
	}
	
	public void loadSchedules() {
		File configFile = new File(schedulesPath);
		if (configFile.exists()) {
			Gson g = new Gson();
			//Thanks to stackoverflow: 
			//https://stackoverflow.com/questions/5554217/google-gson-deserialize-listclass-object-generic-type
			Type listType = new TypeToken<ArrayList<Schedule>>(){}.getType();
			try (Reader r = new FileReader(configFile)) {
				schedules = g.fromJson(r, listType);
			} catch (IOException e) {
				log.log(Level.WARNING, "Failed to load config", e);
			}
			while (schWeather.size() < schedules.size()) {
				schWeather.add(null);
			}
		}
	}
	
}
