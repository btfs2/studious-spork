package uk.ac.cam.bizrain.location.photon;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.stream.Collectors;
import java.util.List;

import com.google.gson.Gson;

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

	Gson g = new Gson();
	
	/**
	 * Searches photon for a valid location
	 * 
	 * @param place Place to search for
	 * @param around Location to search around
	 * @return Response from query
	 */
	private PhotonResponse search(String place, Location around) {
		StringBuilder query = null;
		try {
			query = new StringBuilder(String.format("https://photon.komoot.de/api/?q=%s", URLEncoder.encode(place, "UTF-8")));
			if (around != null) {
				query.append(String.format("&lat=%f&lon=%f", around.getLat(), around.getLng()));
			}
		} catch (UnsupportedEncodingException e) {
		}
		if (query == null) {
			System.err.println("Probably unsuported encoding");
			return null;
		}
		String body = NetUtil.httpBody(query.toString(), "GET", 200, 600000, false);
		return g.fromJson(body, PhotonResponse.class);
	}
	
	/**
	 * Reverse lookup from given location
	 * 
	 * @param place Location to search from
	 * @return Place at location
	 */
	private PhotonResponse reverse(Location place) {
		String query = null;
		query = String.format("https://photon.komoot.de/reverse?lon=%f&lat=%f", place.getLng(), place.getLat());
		String body = NetUtil.httpBody(query.toString(), "GET", 200, 600000, false);
		return g.fromJson(body, PhotonResponse.class);
	}
	
	@Override
	public Location placeToLoc(String place) {
		return placeToLocAround(place, null);
	}

	@Override
	public Location placeToLocAround(String place, Location around) {
		PhotonResponse pr = search(place, around);
		return pr.features.get(0).getPlaceLocation();
	}

	@Override
	public String locToPlace(Location loc) {
		PhotonResponse pr = reverse(loc);
		return pr.features.get(0).getPlaceString();
	}

	@Override
	public List<String> predict(String partialQuery) {
		PhotonResponse pr = search(partialQuery, null);
		return pr.features.stream().map(PhotonFeature::getPlaceString).collect(Collectors.toList());
	}

}
