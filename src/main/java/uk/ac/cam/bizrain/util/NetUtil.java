package uk.ac.cam.bizrain.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

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
	
	private static Map<String, String> responseCache   = new ConcurrentHashMap<>();
	private static Map<String, Long> responseCacheTime = new ConcurrentHashMap<>();
	
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
		if (!flushCache && responseCache.containsKey(url)) {
			if (responseCacheTime.get(url) < System.currentTimeMillis() + cacheTimeout) {
				return responseCache.get(url);
			} else {
				responseCacheTime.remove(url);
				responseCache.remove(url);
			}
		}
		try {
	        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	        connection.setConnectTimeout(requestTimeout);
	        connection.setReadTimeout(requestTimeout);
	        connection.setRequestMethod(method);
	        connection.setRequestProperty("Accept-Encoding", "identity, gzip");
	        if (connection.getResponseCode() != 200) {
	        	log.log(Level.WARNING, "HTTP Read failed response code: " + connection.getResponseCode());
	        	return null; //Read fail
	        }
	        else {
	        	String body = "";
	        	if (connection.getContentEncoding() == null || connection.getContentEncoding().equalsIgnoreCase("identity")) {
		        	body = new BufferedReader(new InputStreamReader(connection.getInputStream())).lines().reduce((a, b) -> a + "\n" + b).get();
		        	responseCache.put(url, body);
		        	responseCacheTime.put(url, System.currentTimeMillis());
	        	} else if (connection.getContentEncoding().equalsIgnoreCase("gzip")) {
	        		body = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream()))).lines().reduce((a, b) -> a + "\n" + b).get();
		        	responseCache.put(url, body);
		        	responseCacheTime.put(url, System.currentTimeMillis());
	        	} else {
	        		log.log(Level.WARNING, "INVALID ENCODING: " + connection.getContentEncoding());
	        	}
	        	return body;
	        }
	    } catch (IOException e) {
	    	log.log(Level.WARNING, "HTTP Read failed; IO error", e);
	        return null;
	    }
	}
}
