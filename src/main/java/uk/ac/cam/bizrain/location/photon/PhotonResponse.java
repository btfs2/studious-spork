package uk.ac.cam.bizrain.location.photon;

import java.util.Arrays;

/**
 * The standard Photon response to a query
 * 
 * @author btfs2
 *
 */
public class PhotonResponse {

	public PhotonFeature[] features;
	
	@Override
	public String toString() {
		return Arrays.deepToString(features);
	}
}
