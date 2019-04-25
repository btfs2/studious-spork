package uk.ac.cam.bizrain.location.photon;

import uk.ac.cam.bizrain.location.IGeocoder;
import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.util.NetUtil;

/**
 * A geocoder based on using Photon from OSM Data
 * 
 * URL: http://photon.komoot.de/
 * 
 * @author btfs2
 *
 */
public class PhotonGeocoder implements IGeocoder {

	@Override
	public boolean isGeocoderAvaliable() {
		return NetUtil.pingURL("http://photon.komoot.de/");
	}

	@Override
	public Location placeToLoc(String place) {
		
		return null;
	}

	@Override
	public Location placeToLocAround(String place, Location around) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String locToPlace(Location loc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] predict(String partialQuery) {
		return null;
	}

}
