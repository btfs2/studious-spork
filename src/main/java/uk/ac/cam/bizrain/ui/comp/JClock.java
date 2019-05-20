package uk.ac.cam.bizrain.ui.comp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
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
	LocalTime to = LocalTime.now().plusHours(1);
	
	public JClock() {
		
	}
	
	public JClock(LocalTime from, LocalTime to) {
		this.from = from;
		this.to = to;
	}
	
	public void setFrom(LocalTime from) {
		this.from = from;
		repaint();
	}
	
	public void setTo(LocalTime to) {
		this.to = to;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		//Base transform to restore to
		AffineTransform at = g2.getTransform();
		//Enable AA
		g2.setRenderingHints(hints);
		
		int y, x = y = 0;
		int diam = Math.min(getWidth()-pad, getHeight()-pad);
		int rad = diam/2;
		
		float originX = getWidth()/2;
		float originY = getHeight()/2;
		
		g2.translate(originX, originY);
		
		//Base stroke to restore to
		Stroke stroke = g2.getStroke();
		
		g.setColor(getForeground());
		
		g2.fillOval(-2, -2, 4, 4);
		
		g.setColor(Color.decode("0xDDDDDD"));
		int pad = 2;
		g2.fillOval(-(3*rad/5)+pad, -(3*rad/5)+pad, 2*(3*rad/5)-2*pad, 2*(3*rad/5)-2*pad);
				
		double arc144 = Math.PI/72f;
		if (from != null && to != null) {
			int startPos = 12*from.getHour()+from.getMinute()/5;
			int endPos = 12*to.getHour()+to.getMinute()/5;
			double ang2 = arc144*(startPos)-Math.PI/2;
			if (startPos < 144) {
				double thisRad = (5*rad/5);
				x = (int) (thisRad*Math.cos(ang2));
				y = (int) (thisRad*Math.sin(ang2));
				Polygon p = new Polygon();
				//p.addPoint(0, 0);
				p.addPoint(x, y);
				for (int i = startPos; i <= Math.min(144, endPos); i+=1) {
					ang2 = arc144*(i)-Math.PI/2;
					x = (int) (thisRad*Math.cos(ang2));
					y = (int) (thisRad*Math.sin(ang2));
					p.addPoint(x, y);
				}
				thisRad = (3*rad/5);
				for (int i = Math.min(144, endPos); i >= startPos; i-=1) {
					ang2 = arc144*(i)-Math.PI/2;
					x = (int) (thisRad*Math.cos(ang2));
					y = (int) (thisRad*Math.sin(ang2));
					p.addPoint(x, y);
				}
				g2.setColor(Color.ORANGE);
				g2.fill(p);
				if (from != null) {
					startPos = 12*from.getHour()+from.getMinute()/5;
					ang2 = arc144*(startPos)-Math.PI/2;
					thisRad = (5*rad/5);
					x = (int) (thisRad*Math.cos(ang2));
					y = (int) (thisRad*Math.sin(ang2));
					thisRad = (3*rad/5);
					int x2 = (int) (thisRad*Math.cos(ang2));
					int y2 = (int) (thisRad*Math.sin(ang2));
					stroke = g2.getStroke();
					g2.setStroke(new BasicStroke(3));
					g2.setColor(Color.GREEN);
					g2.drawLine(x2, y2, x, y);
					g2.setStroke(stroke);
				}
				p = new Polygon();
				if (endPos > 144) {
					thisRad = (3*rad/5);
					p.addPoint(0, 0);
					for (int i = 0; i <= endPos-144; i+=1) {
						ang2 = arc144*(i)-Math.PI/2;
						x = (int) (thisRad*Math.cos(ang2));
						y = (int) (thisRad*Math.sin(ang2));
						p.addPoint(x, y);
					}
					g2.setColor(Color.ORANGE);
					g2.fill(p);
				}
			} else {
				System.out.println("TEST");
				double thisRad = (3*rad/5);
				x = (int) (thisRad*Math.cos(ang2));
				y = (int) (thisRad*Math.sin(ang2));
				Polygon p = new Polygon();
				p.addPoint(0, 0);
				p.addPoint(x, y);
				for (int i = startPos-144; i <= endPos-144; i+=1) {
					ang2 = arc144*(i)-Math.PI/2;
					x = (int) (thisRad*Math.cos(ang2));
					y = (int) (thisRad*Math.sin(ang2));
					p.addPoint(x, y);
				}
				g2.setColor(Color.ORANGE);
				g2.fill(p);
				if (from != null) {
					startPos = 12*from.getHour()+from.getMinute()/5;
					ang2 = arc144*(startPos)-Math.PI/2;
					thisRad = ((startPos < 144 ? 5 : 3)*rad/5);
					x = (int) (thisRad*Math.cos(ang2));
					y = (int) (thisRad*Math.sin(ang2));
					stroke = g2.getStroke();
					g2.setStroke(new BasicStroke(3));
					g2.setColor(Color.GREEN);
					g2.drawLine(0, 0, x, y);
					g2.setStroke(stroke);
				}
			}
		} 
		
		if (to != null) {
			int startPos = 12*to.getHour()+to.getMinute()/5;
			double ang2 = arc144*(startPos)-Math.PI/2;
			double thisRad = ((startPos <= 144 ? 5 : 3)*rad/5);
			x = (int) (thisRad*Math.cos(ang2));
			y = (int) (thisRad*Math.sin(ang2));
			double thatRad = (3*rad)/5;
			int x2 = (int) (thatRad*Math.cos(ang2));
			int y2 = (int) (thatRad*Math.sin(ang2));
			stroke = g2.getStroke();
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.RED);
			if (startPos > 144) {
				g2.drawLine(0, 0, x, y);
			} else {
				g2.drawLine(x2, y2, x, y);
			}
			g2.setStroke(stroke);
		}
		
		g.setColor(getForeground());
		double arc6 = Math.PI/6f;
		for (int i = 1; i<=12; i++) {
			String s = String.format("%02d", i);
			double ang2 = arc6*(i)-Math.PI/2;
			double thisRad = (4*rad/5);
			x = (int) (thisRad*Math.cos(ang2));
			y = (int) (thisRad*Math.sin(ang2));
			
			g2.drawString(s, 
					x-g2.getFontMetrics().stringWidth(s)/2, 
					y+(g2.getFontMetrics().getHeight()*3)/7);
		}
		for (int i = 0; i<=11; i++) {
			String s = String.format("%02d", i!=0 ? i+12 : 0);
			double ang2 = arc6*(i)-Math.PI/2;
			double thisRad = (2*rad/5);
			x = (int) (thisRad*Math.cos(ang2));
			y = (int) (thisRad*Math.sin(ang2));
			
			g2.drawString(s, 
					x-g2.getFontMetrics().stringWidth(s)/2, 
					y+g2.getFontMetrics().getHeight()/4);
		}
		
		stroke = g2.getStroke();
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		g2.drawOval(-rad, -rad, rad*2, rad*2);
		g2.setStroke(stroke);
		
		g2.setTransform(at);
	}
}
