package uk.ac.cam.bizrain.test;

import com.google.gson.Gson;

import uk.ac.cam.bizrain.location.photon.PhotonResponse;
import uk.ac.cam.bizrain.util.NetUtil;

/**
 * Edit this when you want to test some generic thing
 * 
 * @author btfs2
 *
 */
public class StuffTester {

	public static void main(String[] args) {
		System.out.println(NetUtil.httpBody("https://photon.komoot.de/api/?q=berlin", "GET", 500, 60000, false).substring(0, 100));
		System.out.println(NetUtil.httpBody("https://photon.komoot.de/api/?q=berlin", "GET", 500, 60000, false).substring(100, 200));
		String body = NetUtil.httpBody("https://photon.komoot.de/api/?q=berlin", "GET", 500, 60000, false);
		Gson g = new Gson();
		System.out.println(g.fromJson(body, PhotonResponse.class).toString());
		
	}

}
