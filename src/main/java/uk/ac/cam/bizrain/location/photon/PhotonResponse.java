package uk.ac.cam.bizrain.location.photon;

import java.util.List;

/**
 * The standard Photon response to a query
 * 
 * @author btfs2
 *
 */
public class PhotonResponse {

	public List<PhotonFeature> features;
	public String type;
	
	@Override
	public String toString() {
		return features.toString();
	}
}
