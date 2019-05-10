package uk.ac.cam.bizrain.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Networking utilities
 * 
 * @author btfs2
 *
 */
public class NetUtil {
	
	static Logger log = Logger.getLogger("NetUtil");
	
	/**
	 * Checks a URL for availability, with default values
	 * 
	 * @see #pingURL(String, int, String, int[])
	 * 
	 * @param url URL to ping
	 * @return if a head returns HTTP 200 to a HEAD request to endpoint in 250ms
	 */
	public static boolean pingURL(String url) {
		return pingURL(url, 250, "HEAD", new int[] {HttpURLConnection.HTTP_OK});
	}
	
	/**
	 * Configurably checks a URL for availability
	 * 
	 * Heavily inspired by https://stackoverflow.com/questions/3584210/preferred-java-way-to-ping-an-http-url-for-availability
	 * 
	 * Customised to allow for odd site responses as sites are wierd sometimes
	 * 
	 * @param url URL to ping
	 * @param timeout Max Time for site to respond
	 * @param method HTTP method to use
	 * @param acceptedCodes acceptable reponse codes
	 * @return if a head returns accepted code within time
	 */
	public static boolean pingURL(String url, int timeout, String method, int[] acceptedCodes) {
	    try {
	        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	        connection.setConnectTimeout(timeout);
	        connection.setReadTimeout(timeout);
	        connection.setRequestMethod(method);
	        int responseCode = connection.getResponseCode();
	        return Arrays.stream(acceptedCodes).anyMatch(i -> i == responseCode);
	    } catch (IOException exception) {
	        return false;
	    }
	}
	
	/**
	 * Gets the body of a request to a url
	 * 
	 * @param url URL to request
	 * @return Body of response of a GET to url.
	 */
	public static String getUrl(String url) {
		return httpBody(url, "GET", 200, 60000, false);
	}
	
	/**
	 * 
	 * A wrapper for a given http response
	 * 
	 * Alows for saving to disk
	 * 
	 * @author btfs2
	 *
	 */
	private static class NetworkRepsonse implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 7423219539011542887L;
		String body;
		long time;
		
		
	}
	
	//private static Map<String, String> responseCache   = new ConcurrentHashMap<>();
	//private static Map<String, Long> responseCacheTime = new ConcurrentHashMap<>();
	
	private static Map<String, NetworkRepsonse> responseCache   = new ConcurrentHashMap<>();
	
	/**
	 * Sends a http request to a site with the given method, 
	 * and then caches it for the given time, 
	 * unless cache flushing is requested.
	 * 
	 * Then returns the response body.
	 * 
	 * Note that this only returns if the code is 200;
	 * 
	 * Supports parsing of both raw and GZIP'd data.
	 * 
	 * @param url URL to request; must be safely encoded
	 * @param method Method to use for request; must be valid
	 * @param requestTimeout HTTP request timeout in ms
	 * @param cacheTimeout Cache timeout in ms
	 * @param flushCache Force request new web request if true 
	 * @return Body of http request, or null if failed
	 */
	public static String httpBody(String url, String method, int requestTimeout, long cacheTimeout, boolean flushCache) {
		//Check cache
		if (!flushCache && responseCache.containsKey(url)) {
			if (responseCache.get(url).time < System.currentTimeMillis() + cacheTimeout) {
				return responseCache.get(url).body;
			} else {
				responseCache.remove(url);
			}
		}
		//Attempt networking
		try {
	        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	        connection.setConnectTimeout(requestTimeout);
	        connection.setReadTimeout(requestTimeout);
	        connection.setRequestMethod(method);
	        connection.setRequestProperty("Accept-Encoding", "identity, gzip"); //Specify decodable encodings
	        if (connection.getResponseCode() != 200) { //Do a read fail
	        	log.log(Level.WARNING, "HTTP Read failed response code: " + connection.getResponseCode());
	        	return null; //Read fail
	        }
	        else {
	        	String body = "";
	        	if (connection.getContentEncoding() == null || connection.getContentEncoding().equalsIgnoreCase("identity")) { // Handle RAW
		        	body = new BufferedReader(new InputStreamReader(connection.getInputStream())).lines().reduce((a, b) -> a + "\n" + b).get();
	        	} else if (connection.getContentEncoding().equalsIgnoreCase("gzip")) { // Handle GZIP
	        		body = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream()))).lines().reduce((a, b) -> a + "\n" + b).get();
	        	} else { //Handle invalid; not expected as not passed in accepted
	        		log.log(Level.WARNING, "INVALID ENCODING: " + connection.getContentEncoding());
	        	}
	        	//Cache
	        	NetworkRepsonse toAdd = new NetworkRepsonse();
	        	toAdd.body = body;
	        	toAdd.time = System.currentTimeMillis();
	        	responseCache.put(url, toAdd);
	        	saveNetCache();
	        	return body;
	        }
	    } catch (IOException e) {
	    	log.log(Level.WARNING, "HTTP Read failed; IO error", e);
	        return null;
	    }
	}
	
	// Serialisation
	////////////////
	
	private static String netcachePath = "network.bin";
	
	static {
		//Load on instantiation
		loadNetCache();
	}
	
	public static void saveNetCache() {
		File configFile = new File(netcachePath);
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
				return;
			} catch (IOException e) {
				log.log(Level.WARNING, "Failed to create network cache", e);
			}
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(configFile)))) {
			oos.writeObject(responseCache);
			oos.flush();
		} catch (IOException e) {
			log.log(Level.WARNING, "Failed to save network cache", e);
		}
	}
	
	@SuppressWarnings("unchecked") // cannot validate casting
	public static void loadNetCache() {
		File configFile = new File(netcachePath);
		if (configFile.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(configFile)))) {
				Object o = ois.readObject();
				if (o instanceof Map<?, ?>) {
					responseCache = (Map<String, NetworkRepsonse>) o;
				}
			} catch (FileNotFoundException e) {
				log.log(Level.WARNING, "Failed to load network cache", e);
			} catch (IOException e) {
				log.log(Level.WARNING, "Failed to load network cache", e);
			} catch (ClassNotFoundException e) {
				log.log(Level.SEVERE, "Failed to load network cache due to class err you TWODLE", e);
			}
		}
	}
	
}
