package uk.ac.cam.bizrain.location.photon;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import uk.ac.cam.bizrain.location.IGeocoder;
import uk.ac.cam.bizrain.location.IPlace;
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
		//Timeout is long as remote is slow
		String body = NetUtil.httpBody(query.toString(), "GET", 60000, 6000000, false);
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
		//Timeout is long as remote is slow
		query = String.format("https://photon.komoot.de/reverse?lon=%f&lat=%f", place.getLng(), place.getLat());
		String body = NetUtil.httpBody(query.toString(), "GET", 20000, 6000000, false);
		return g.fromJson(body, PhotonResponse.class);
	}
	
	@Override
	public Location placeToLoc(IPlace place) {
		return placeToLocAround(place, null);
	}

	@Override
	public Location placeToLocAround(IPlace place, Location around) {
		String query;
		if (place instanceof PhotonPlace) {
			query = ((PhotonPlace) place).name;
		} else {
			query = place.getDisplayName();
		}
		PhotonResponse pr = search(query, around);
		if (pr.features.size() == 0) {
			return null;
		}
		return pr.features.get(0).getPlaceLocation();
	}

	@Override
	public IPlace locToPlace(Location loc) {
		PhotonResponse pr = reverse(loc);
		if (pr.features.size() == 0) {
			return null;
		}
		return new PhotonPlace(pr.features.get(0));
	}

	@Override
	public List<IPlace> predict(IPlace partialQuery) {
		String query;
		if (partialQuery instanceof PhotonPlace) {
			query = ((PhotonPlace) partialQuery).name;
		} else {
			query = partialQuery.getDisplayName();
		}
		PhotonResponse pr = search(query, null);
		return pr.features.stream().map(PhotonPlace::new).collect(Collectors.toList());
	}

}
