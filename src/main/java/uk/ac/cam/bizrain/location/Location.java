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
	
	@Override
	public String toString() {
		return  Math.abs(getLat()) + "° " + (getLat() >= 0 ? "N " : "S ") + Math.abs(getLng()) + "° " + (getLng() >= 0 ? "E" : "W");
	}
	
	/**
	 * To floating point location string
	 * 
	 * @return float string of loc
	 */
	public String toFloatString() {
		return getLat() + "," + getLng();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Location) {
			//Solves float problems
			return (Float.floatToRawIntBits(lat) == Float.floatToRawIntBits(((Location) obj).lat))
					&& (Float.floatToRawIntBits(lng) == Float.floatToRawIntBits(((Location) obj).lng));
		}
		return false;
	}
}
