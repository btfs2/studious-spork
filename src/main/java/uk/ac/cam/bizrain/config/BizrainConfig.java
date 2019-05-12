package uk.ac.cam.bizrain.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

/**
 * The config of the app
 * 
 * Contains usefull stuff
 * 
 * @author btfs2
 *
 */
public class BizrainConfig {

	// CONFIG METADATA
	//////////////////
	
	private static final Logger log = Logger.getLogger("Config");
	public static BizrainConfig INSTANCE;
	
	private static String configPath = "config.json";
	private static String configDefaultClassPath = "/uk/ac/cam/bizrain/config.default.json";
	
	// ACTUAL CONFIG
	////////////////
	
	public String darkSkyKey;
	public String timeZoneId;
	public boolean isTime12hr;
	public String units;
	
	
	// SAVE/LOAD
	////////////
	
	/**
	 * Load config on system start
	 */
	static {
		loadConfig();
	}
	
	public static void saveConfig() {
		File configFile = new File(configPath);
		Gson g = new Gson();
		try (Writer r = new FileWriter(configFile)) {
			g.toJson(INSTANCE, r);
		} catch (IOException e) {
			log.log(Level.WARNING, "Failed to save config", e);
		}
	}
	
	public static void loadConfig() {
		File configFile = new File(configPath);
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
				InputStream is = BizrainConfig.class.getResourceAsStream(configDefaultClassPath);
				try (OutputStream os = new FileOutputStream(configFile)) {
					while (is.available() > 0) {
						os.write(is.read());
					}
				}
			} catch (IOException e) {
				log.log(Level.WARNING, "Failed to create config file from default", e);
			}
		}
		Gson g = new Gson();
		try (Reader r = new FileReader(configFile)) {
			INSTANCE = g.fromJson(r, BizrainConfig.class);
		} catch (IOException e) {
			log.log(Level.WARNING, "Failed to load config", e);
		}
	}
	
}
