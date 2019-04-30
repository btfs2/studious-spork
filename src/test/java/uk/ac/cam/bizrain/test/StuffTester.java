package uk.ac.cam.bizrain.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import uk.ac.cam.bizrain.location.IGeocoder;
import uk.ac.cam.bizrain.location.IPlace;
import uk.ac.cam.bizrain.location.IPlaceLocated;
import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.location.StringPlace;
import uk.ac.cam.bizrain.location.photon.PhotonGeocoder;
import uk.ac.cam.bizrain.location.photon.PhotonResponse;
import uk.ac.cam.bizrain.util.NetUtil;
import uk.ac.cam.bizrain.weather.darksky.DarkskyResponse;

/**
 * Edit this when you want to test some generic thing
 * 
 * @author btfs2
 *
 */
public class StuffTester {

	public static void main(String[] args) {
		//PhotonTest1();
		//PhotonTest2();
		DarkskyFileTest();
	}
	
	public static void PhotonTest1() {
		System.out.println(NetUtil.httpBody("https://photon.komoot.de/api/?q=berlin", "GET", 15000, 60000, false).substring(0, 100));
		System.out.println(NetUtil.httpBody("https://photon.komoot.de/api/?q=berlin", "GET", 15000, 60000, false).substring(100, 200));
		String body = NetUtil.httpBody("https://photon.komoot.de/api/?q=berlin", "GET", 15000, 60000, false);
		Gson g = new Gson();
		System.out.println(g.fromJson(body, PhotonResponse.class).toString());
	}
	
	public static void DarkskyFileTest() {
		System.out.println("Powered By Darksky");
		InputStream is = (new StuffTester()).getClass()
				.getClassLoader()
				.getResourceAsStream("uk/ac/cam/bizrain/test/DarkSkyTest2.json");
		String file = new BufferedReader(new InputStreamReader(is)).lines().reduce((a, b) -> a + "\n" + b).get();;
		Gson g = new Gson();
		DarkskyResponse res = g.fromJson(file, DarkskyResponse.class);
		System.out.println(res.hourly.summary);
	}

}
 