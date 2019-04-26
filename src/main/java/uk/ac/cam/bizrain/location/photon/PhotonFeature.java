package uk.ac.cam.bizrain.location.photon;

import uk.ac.cam.bizrain.location.Location;

/**
 * Describes a feature from the photon JSON
 * 
 * Used for GSON autoparsing
 * 
 * @author btfs2
 *
 */
public class PhotonFeature {

	public class Geometry {
		public float[] coordinates;
		public String type;
	}
	
	
	
	public class Properties {
		//OSM STUFF
		public long osm_id;
		public String osm_type;
		public String osm_key;
		public String osm_value;
		public float[] extent;
		//PLACE STUFF
		public String name;
		public String country;
		public String city;
		public String postcode;
		public String housenumber;
		public String street;
		public String state;
	}
	
	public String type;
	public Geometry geometry;
	public Properties properties;
	
	public Location getPlaceLocation() {
		return new Location(geometry.coordinates[1], geometry.coordinates[0]);
	}
	
	/**
	 * Gets the string in the standard photon format
	 * 
	 * @return String of place name
	 */
	public String getPlaceString() {
		StringBuilder sb = new StringBuilder(properties.name);
		if (properties.housenumber != null) {
			sb.append(", ");
			sb.append(properties.housenumber);
		}
		if (properties.street != null) {
			sb.append(", ");
			sb.append(properties.street);
		}
		if (properties.street != null) {
			sb.append(", ");
			sb.append(properties.street);
		}
		if (properties.city != null) {
			sb.append(", ");
			sb.append(properties.city);
		}
		if (properties.state != null) {
			sb.append(", ");
			sb.append(properties.state);
		}
		if (properties.postcode != null) {
			sb.append(", ");
			sb.append(properties.postcode);
		}
		if (properties.country != null) {
			sb.append(", ");
			sb.append(properties.country);
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "Place(" + getPlaceString() + ")";
	}
}
