package uk.ac.cam.bizrain.test;

import java.util.List;
import java.util.Scanner;

import uk.ac.cam.bizrain.location.IGeocoder;
import uk.ac.cam.bizrain.location.IPlace;
import uk.ac.cam.bizrain.location.IPlaceLocated;
import uk.ac.cam.bizrain.location.Location;
import uk.ac.cam.bizrain.location.StringPlace;
import uk.ac.cam.bizrain.location.photon.PhotonGeocoder;

/**
 * 
 * A console app to demonstrate the effectiveness of Photon
 * 
 * @author btfs2
 *
 */
public class PhotonTest {

	public static void main(String[] args) {
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
}
