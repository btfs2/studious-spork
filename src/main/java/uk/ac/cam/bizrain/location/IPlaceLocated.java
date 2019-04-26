package uk.ac.cam.bizrain.location;

/**
 * A place which has locational metadata attached to it
 * 
 * @author btfs2
 *
 */
public interface IPlaceLocated extends IPlace {

	/**
	 * Get the location of this place
	 * 
	 * @return Location
	 */
	public Location getPlaceLocation();
}
