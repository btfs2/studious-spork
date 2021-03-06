package uk.ac.cam.bizrain.location;

import java.io.Serializable;

/**
 * Represents some place in a user displayable format
 * 
 * @author btfs2
 *
 */
public interface IPlace extends Serializable {

	/**
	 * Gets the user friendly display name of the place
	 * 
	 * @return User friendly place
	 */
	public String getDisplayName();
	
}
