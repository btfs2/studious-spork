package uk.ac.cam.bizrain.location;

/**
 * Stores a location as lat/long
 * 
 * @author btfs2
 *
 */
public class Location {

	float lat, lng;
	
	/**
	 * Create a location for a given position
	 * 
	 * @param lat
	 * @param lng
	 */
	public Location(float lat, float lng) {
		this.lat = lat;
		this.lng = lng;
	}
	
	/**
	 * Get latitude of location
	 * 
	 * @return Latitude of location
	 */
	public float getLat() {
		return lat;
	}
	
	/**
	 * Get longitude of location
	 * 
	 * @return Longitude
	 */
	public float getLng() {
		return lng;
	}
}
