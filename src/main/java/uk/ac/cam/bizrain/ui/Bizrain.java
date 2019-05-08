package uk.ac.cam.bizrain.ui;

import java.awt.EventQueue;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import uk.ac.cam.bizrain.location.photon.PhotonGeocoder;

import java.awt.BorderLayout;

public class Bizrain {

	private JFrame frame;
	private JPanel mainPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Bizrain window = new Bizrain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Bizrain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 574);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new PanelLocationSearch(this, new PhotonGeocoder(), (place, loc, startTime, endTime) -> {
			System.out.println("Location entered: " + place.getDisplayName() + 
					"\nAt: " + loc.toString() + 
					"\nFrom:" + startTime.format(DateTimeFormatter.ofPattern("HH:mm")) + 
					"\nTo: " + endTime.format(DateTimeFormatter.ofPattern("HH:mm")));
			JOptionPane.showMessageDialog(null, "Location entered: " + place.getDisplayName() + 
					"\nAt: " + loc.toString() + 
					"\nFrom:" + startTime.format(DateTimeFormatter.ofPattern("HH:mm")) + 
					"\nTo: " + endTime.format(DateTimeFormatter.ofPattern("HH:mm")));
		});
		//mainPanel = new PanelAddSchedule();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	public void setMainPanel(JPanel newPanel) {
		frame.getContentPane().remove(mainPanel);
		frame.getContentPane().add(newPanel, BorderLayout.CENTER);
		mainPanel = newPanel;
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}
}
