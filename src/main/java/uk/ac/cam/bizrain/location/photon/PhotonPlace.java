package uk.ac.cam.bizrain.location.photon;

import uk.ac.cam.bizrain.location.IPlaceLocated;
import uk.ac.cam.bizrain.location.IPlaceSpecific;
import uk.ac.cam.bizrain.location.Location;

/**
 * A place aquired by Photon
 * 
 * Is located and has detail
 * 
 * @author btfs2
 *
 */
public class PhotonPlace implements IPlaceLocated, IPlaceSpecific {

	String name;
	String country;
	String city;
	String postcode;
	String housenumber;
	String street;
	String state;
	
	Location location;
	
	/**
	 * Creates a Photon Place from a photon feature
	 * 
	 * @see PhotonFeature
	 * 
	 * @param pf Photon feature to generate more data
	 */
	public PhotonPlace(PhotonFeature pf) {
		this.name = pf.properties.name;
		this.country = pf.properties.country;
		this.city = pf.properties.city;
		this.postcode = pf.properties.postcode;
		this.housenumber = pf.properties.housenumber;
		this.street = pf.properties.street;
		this.state = pf.properties.state;
		this.location = pf.getPlaceLocation();
	}

	@Override
	public String getDisplayName() {
		StringBuilder sb = new StringBuilder(name);
		if (housenumber != null) {
			sb.append(", ");
			sb.append(housenumber);
		}
		if (street != null) {
			sb.append(", ");
			sb.append(street);
		}
		if (street != null) {
			sb.append(", ");
			sb.append(street);
		}
		if (city != null) {
			sb.append(", ");
			sb.append(city);
		}
		if (state != null) {
			sb.append(", ");
			sb.append(state);
		}
		if (postcode != null) {
			sb.append(", ");
			sb.append(postcode);
		}
		if (country != null) {
			sb.append(", ");
			sb.append(country);
		}
		return sb.toString();
	}

	@Override
	public Location getPlaceLocation() {
		return location;
	}
	
	@Override
	public String toString() {
		return getDisplayName();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public String getCity() {
		return city;
	}
	
}
