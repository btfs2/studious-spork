package uk.ac.cam.bizrain.location;

/**
 * A place that is just a display string
 * 
 * @author btfs2
 *
 */
public class StringPlace implements IPlace {

	String place;
	
	public StringPlace(String place) {
		this.place = place;
	}
	
	@Override
	public String getDisplayName() {
		return place;
	}

}
