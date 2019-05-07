package uk.ac.cam.bizrain.ui.comp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.time.LocalTime;

import javax.swing.JPanel;

public class JClock extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8509356390449412164L;

	int pad = 5;
	RenderingHints hints = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
	
	LocalTime from = LocalTime.now();
	LocalTime to = LocalTime.now().plusHours(3);
	
	public JClock() {
	}
	
	public JClock(LocalTime from, LocalTime to) {
		this.from = from;
		this.to = to;
	}
	
	public void setFrom(LocalTime from) {
		this.from = from;
	}
	
	public void setTo(LocalTime to) {
		this.to = to;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHints(hints);
		
		int diam = Math.min(getWidth()-pad, getHeight()-pad);
		int rad = diam/2;
		double arc = Math.PI/6f;
		
		g2.drawOval(Math.floorDiv(pad, 2), Math.floorDiv(pad, 2), diam, diam);
		
		int inset = 10;
		for (int i = 1; i<=12; i++) {
			int x = (int) ((rad-inset)*Math.cos(arc*(i-1)-Math.PI/2)+rad+Math.floorDiv(pad, 2));
			int y = (int) ((rad-inset)*Math.sin(arc*(i-1)-Math.PI/2)+rad+Math.floorDiv(pad, 2));
			//g2.drawLine(rad+Math.floorDiv(pad, 2), rad+Math.floorDiv(pad, 2), x, y);
			g2.drawString(Integer.toString(i), x-pad, y+pad);
		}
		
		arc = Math.PI/144f;
		if (from != null && to != null) {
			int startPos = 12*from.getHour()+from.getMinute()/5;
			int endPos = 12*to.getHour()+to.getMinute()/5;
			int xs = (int) ((rad-inset)*Math.cos(arc*(startPos)-Math.PI/2)+rad+Math.floorDiv(pad, 2));
			int ys = (int) ((rad-inset)*Math.sin(arc*(startPos)-Math.PI/2)+rad+Math.floorDiv(pad, 2));
			int xe = (int) ((rad-inset)*Math.cos(arc*(endPos)-Math.PI/2)+rad+Math.floorDiv(pad, 2));
			int ye = (int) ((rad-inset)*Math.sin(arc*(endPos)-Math.PI/2)+rad+Math.floorDiv(pad, 2));
			Polygon p = new Polygon();
			p.addPoint(pad/2+rad, pad/2+rad);
			p.addPoint(xs, ys);
			for (int i = startPos; i < endPos; i+=2) {
				int xi = (int) ((rad-inset)*Math.cos(arc*(i)-Math.PI/2)+rad+Math.floorDiv(pad, 2));
				int yi = (int) ((rad-inset)*Math.sin(arc*(i)-Math.PI/2)+rad+Math.floorDiv(pad, 2));
				p.addPoint(xi, yi);
			}
			p.addPoint(xe, ye);
			g2.setColor(Color.ORANGE);
			g2.fill(p);
		}
		if (from != null) {
			int x = (int) ((rad-inset)*Math.cos(arc*(12*from.getHour()+from.getMinute()/5)-Math.PI/2)+rad+Math.floorDiv(pad, 2));
			int y = (int) ((rad-inset)*Math.sin(arc*(12*from.getHour()+from.getMinute()/5)-Math.PI/2)+rad+Math.floorDiv(pad, 2));
			g2.setColor(Color.GREEN);
			g2.drawLine(pad/2+rad, pad/2+rad, x, y);
		}
		if (to != null) {
			int x = (int) ((rad-inset)*Math.cos(arc*(12*to.getHour()+to.getMinute()/5)-Math.PI/2)+rad+Math.floorDiv(pad, 2));
			int y = (int) ((rad-inset)*Math.sin(arc*(12*to.getHour()+to.getMinute()/5)-Math.PI/2)+rad+Math.floorDiv(pad, 2));
			g2.setColor(Color.RED);
			g2.drawLine(pad/2+rad, pad/2+rad, x, y);
		}
	}
}
