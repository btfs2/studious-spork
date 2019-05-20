package uk.ac.cam.bizrain.test;

import java.awt.BorderLayout;
import java.time.LocalTime;

import javax.swing.JFrame;

import uk.ac.cam.bizrain.ui.comp.JClock;

public class Scratchpad {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		Set<String> ids = ZoneId.getAvailableZoneIds();
		for (String i : ids) {
			System.out.println(i);
		}
		*/
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JClock(LocalTime.of(2, 45), LocalTime.of(13, 25)), BorderLayout.CENTER);
		frame.setBounds(100, 100, 200, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
