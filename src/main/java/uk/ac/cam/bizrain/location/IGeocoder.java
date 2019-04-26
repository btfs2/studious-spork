package uk.ac.cam.bizrain.location;

import java.util.List;

/**
 * A geocoding service
 * 
 * Used to map from places to lat,long and vice versa
 * 
 * @author btfs2
 *
 */
public interface IGeocoder {

	/**
	 * Verify that the geocoder is up
	 * 
	 * @return true if will work
	 */
	public boolean isGeocoderAvaliable();
	
	/**
	 * Lookup place and find it's lat long
	 * 
	 * @param place Location to look up
	 * @return Location of place
	 */
	public Location placeToLoc(IPlace place);
	
	/**
	 * Lookup place and find it's lat long, prioritising searching around a location
	 * 
	 * Services not implementing this should fall back to placeToLoc;
	 * 
	 * @param place Location to look up
	 * @return Location of place
	 */
	public Location placeToLocAround(IPlace place, Location around);
	
	/**
	 * Get place at location
	 * 
	 * @param loc location to find place
	 * @return Place at location
	 */
	public IPlace locToPlace(Location loc);
	
	/**
	 * Attempt to find location from partial query
	 * 
	 * @param partialQuery user input string
	 * @return List of probable predictions by likelihood
	 */
	public List<IPlace> predict(IPlace partialQuery);
}
