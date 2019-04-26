package uk.ac.cam.bizrain.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Networking utilities
 * 
 * @author btfs2
 *
 */
public class NetUtil {
	
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
	 * TODO: IMPLEMENT
	 * 
	 * @param url URL to request
	 * @return Body of response of a GET to url.
	 */
	public static String getUrl(String url) {
		return "BYE";
	}
	
	private static Map<String, String> responseCache   = new ConcurrentHashMap<>();
	private static Map<String, Long> responseCacheTime = new ConcurrentHashMap<>();
	
	/**
	 * Does stuff
	 * 
	 * TODO Implement
	 * 
	 * TODO HANDLE GZIP
	 * 
	 * @param url
	 * @param method
	 * @param requestTimeout
	 * @param cacheTimeout
	 * @param flushCache
	 * @return
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
	        connection.setRequestProperty("Accept-Encoding", "identity");
	        if (connection.getResponseCode() != 200) return null; //Read fail
	        else {
	        	String body = new BufferedReader(new InputStreamReader(connection.getInputStream())).lines().reduce((a, b) -> a + "\n" + b).get();
	        	responseCache.put(url, body);
	        	responseCacheTime.put(url, System.currentTimeMillis());
	        	return body;
	        }
	    } catch (IOException exception) {
	        return null;
	    }
	}
}
