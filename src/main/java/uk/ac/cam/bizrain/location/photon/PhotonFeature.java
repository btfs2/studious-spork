package uk.ac.cam.bizrain.location.photon;

/**
 * Describes a feature from the photon JSON
 * 
 * Used for GSON autoparsing
 * 
 * @author btfs2
 *
 */
public class PhotonFeature {

	class Geometry {
		public float[] coordinates;
		public String type;
	}
	
	
	
	class Properties {
		//OSM STUFF
		public long osm_id;
		public String osm_type;
		public String osm_key;
		public String osm_value;
		//PLACE STUFF
		public String name;
		public String country;
		public String city;
		public String postcode;
		public String housenumber;
		public String street;
	}
	
	public String type;
	public Geometry geometry;
	public Properties properties;
	
	@Override
	public String toString() {
		return "Place(" + properties.name + ", " + properties.city + ", " + properties.country + ")";
	}
}
