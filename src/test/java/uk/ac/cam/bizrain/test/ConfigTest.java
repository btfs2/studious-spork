package uk.ac.cam.bizrain.test;

import java.util.TimeZone;

import uk.ac.cam.bizrain.config.BizrainConfig;

/**
 * 
 * Tests the config
 * 
 * @author btfs2
 *
 */
public class ConfigTest {

	public static void main(String[] args) {
		System.out.println(BizrainConfig.INSTANCE.darkSkyKey);
		BizrainConfig.INSTANCE.timeZoneId = TimeZone.getTimeZone("").getID();
		BizrainConfig.saveConfig();
	}
	
}
