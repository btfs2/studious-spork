package uk.ac.cam.bizrain.location;

/**
 * A place with pre-determined sub information
 * 
 * I.e. city, counrtry, placename, etc.
 * 
 * TODO: Add more things
 * 
 * @author btfs2
 *
 */
public interface IPlaceSpecific extends IPlace {

	public String getName();
	
	public String getCountry();
	
	public String getCity();
}
