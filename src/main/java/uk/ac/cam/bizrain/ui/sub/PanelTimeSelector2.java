package uk.ac.cam.bizrain.ui.sub;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.time.LocalTime;

import javax.swing.JPanel;

import uk.ac.cam.bizrain.ui.sub.PanelTimeSelector.TimeSelectorReturn;

public class PanelTimeSelector2 extends JPanel {

	/**
	 * Return function for this thing
	 * 
	 * @author btfs2
	 */
	public interface TimeSelectorReturn {
		/**
		 * Called when returning
		 * @param time
		 */
		public void returnData(LocalTime time);
	}
	
	/**
	 * Create the panel.
	 */
	public PanelTimeSelector2(TimeSelectorReturn ret) {
		
	}
	
	int pad = 5;
	RenderingHints hints = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform at = g2.getTransform();
		g2.setRenderingHints(hints);
		
		int diam = Math.min(getWidth()-pad, getHeight()-pad)/2;
		int rad = diam/2;
		int inset = 10;
		
		
		g2.translate(getWidth()/2, getHeight()/2);
		
		g2.drawOval(Math.floorDiv(pad, 2), Math.floorDiv(pad, 2), diam, diam);
	}
	
}
