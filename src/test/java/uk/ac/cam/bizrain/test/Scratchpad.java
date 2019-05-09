package uk.ac.cam.bizrain.test;

import java.time.ZoneOffset;
import java.util.Set;

public class Scratchpad {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Set<String> ids = ZoneOffset.getAvailableZoneIds();
		for (String i : ids) {
			System.out.println(i);
		}
	}

}
