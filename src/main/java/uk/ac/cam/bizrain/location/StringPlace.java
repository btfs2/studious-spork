package uk.ac.cam.bizrain.location;

/**
 * A place that is just a display string
 * 
 * @author btfs2
 *
 */
public class StringPlace implements IPlace {

	String place;
	
	/**
	 * Creates a string place
	 * 
	 * @param place String that is used as display string
	 * @see IPlace#getDisplayName()
	 */
	public StringPlace(String place) {
		this.place = place;
	}
	
	@Override
	public String getDisplayName() {
		return place;
	}
	
	@Override
	public String toString() {
		return place;
	}

}
