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
		PhotonTest2();
		//DarkskyFileTest();
	}
	
	public static void PhotonTest1() {
		System.out.println(NetUtil.httpBody("https://photon.komoot.de/api/?q=berlin", "GET", 15000, 60000, false).substring(0, 100));
		System.out.println(NetUtil.httpBody("https://photon.komoot.de/api/?q=berlin", "GET", 15000, 60000, false).substring(100, 200));
		String body = NetUtil.httpBody("https://photon.komoot.de/api/?q=berlin", "GET", 15000, 60000, false);
		Gson g = new Gson();
		System.out.println(g.fromJson(body, PhotonResponse.class).toString());
	}
	
	public static void PhotonTest2() {
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.println();
			System.out.println();
			System.out.print("Enter location to search for (or q to quit): ");
			String line = in.nextLine();
			if (line.equalsIgnoreCase("q")) {
				break;
			} else if (line.equalsIgnoreCase("")) {
				continue;
			}
			IGeocoder pgc = new PhotonGeocoder();
			List<IPlace> predictions = pgc.predict(new StringPlace(line));
			if (predictions.size() > 0) {
				System.out.println("Places like that are: ");
				int i = 0;
				for (IPlace s : predictions) {
					System.out.println("\t " + i++ + ":"+s.getDisplayName());
				}
				int tgt = 0;
				do {
					System.out.print("Enter valid target id: ");
					try {
						tgt = in.nextInt();
					} catch (Exception e) { 
						System.err.println("Invalid input"); 
					}
				} while (!(0<=tgt && tgt < i));
				Location l;
				IPlace ip = predictions.get(tgt);
				if (ip instanceof IPlaceLocated) {
					l = ((IPlaceLocated) ip).getPlaceLocation();
				} else {
					l = pgc.placeToLoc(predictions.get(tgt));
				}
				System.out.println(predictions.get(tgt).getDisplayName() + " is at " + l.toString());
			} else {
				System.out.println("No matches found");
			}
		}
		in.close();
	}
	
	public static void DarkskyFileTest() {
		System.out.println("Powered By Darksky");
		InputStream is = (new StuffTester()).getClass()
				.getClassLoader()
				.getResourceAsStream("uk/ac/cam/bizrain/test/DarkSkyTest1.json");
		String file = new BufferedReader(new InputStreamReader(is)).lines().reduce((a, b) -> a + "\n" + b).get();;
		Gson g = new Gson();
		DarkskyResponse res = g.fromJson(file, DarkskyResponse.class);
		System.out.println(res.hourly.summary);
	}

}
 