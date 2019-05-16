package uk.ac.cam.bizrain.ui.comp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JPanel;

public class JTimeSelect extends JPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3531443187017576138L;

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
	
	/**
	 * Create the panel.
	 */
	public JTimeSelect(DateTimeFormatter dtf) {
		if (dtf != null) {
			this.dtf = dtf;
		}
	}
	
	int pad = 3;
	RenderingHints hints = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
	
	private float originX = 0f;
	private float originY = 0f;
	
	private float mouseX = 0f;
	private float mouseY = 0f;
	
	private int stage = 0;
	
	private int rad = 0;
	
	private int hour = 0;
	private int min = 0;
	
	private Color numBG = Color.decode("0xDDDDDD");
	private Color hilight = Color.decode("0xCAE5ED");
	
	{
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (mouseX*mouseX + mouseY*mouseY < rad*rad) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						stage = Math.min(Math.max(stage+1, 0), 2);
					} else if (e.getButton() == MouseEvent.BUTTON3) {
						stage = Math.min(Math.max(stage-1, 0), 2);
					}
				}
			}
		});
			
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX()-originX;
				mouseY = e.getY()-originY;
				if (mouseX*mouseX + mouseY*mouseY < rad*rad) {
					repaint();
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	private double getMouseAngle() {
		return Math.atan(mouseY/mouseX) + (mouseX<0 ? Math.PI : 0);
	}
	
	{
		this.setFont(SwingUtil.getFontNum().deriveFont(15f).deriveFont(Font.BOLD));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		//g2.setFont();
		AffineTransform at = g2.getTransform();
		g2.setRenderingHints(hints);
		
		int y, x = y = 0;
		int diam = Math.min(getWidth()-pad, getHeight()-pad);
		rad = diam/2;
		
		originX = getWidth()/2;
		originY = getHeight()/2;
		
		g2.translate(originX, originY);
		
		Stroke stroke = g2.getStroke();
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		g2.drawOval(-rad, -rad, rad*2, rad*2);
		g2.setStroke(stroke);
		
		g.setColor(getForeground());
		
		double ang = getMouseAngle();
		
		int circleRad = 14;
		
		if (stage == 0) {
			//Hour select
			g2.fillOval(-2, -2, 4, 4);
			double arc6 = Math.PI/6f;
			for (int i = 1; i<=12; i++) {
				String s = String.format("%02d", i);
				double ang2 = arc6*(i)-Math.PI/2;
				double thisRad = (4*rad/5);
				x = (int) (thisRad*Math.cos(ang2));
				y = (int) (thisRad*Math.sin(ang2));
				
				g2.setColor(numBG);
				g2.fillOval(x-circleRad, y-circleRad, circleRad*2, circleRad*2);
				g2.setColor(getForeground());
				
				g2.drawString(s, 
						x-g2.getFontMetrics().stringWidth(s)/2, 
						y+(g2.getFontMetrics().getHeight()*3)/7);
				
				if ((Math.abs(ang-ang2)<arc6/2 || Math.abs(ang-ang2-Math.PI*2)<arc6/2 || Math.abs(ang-ang2+Math.PI*2)<arc6/2)  && Math.abs(mouseX*mouseX + mouseY*mouseY)>(rad/2)*(rad/2)) {
					g2.setColor(hilight);
					g2.fillOval(x-circleRad, y-circleRad, circleRad*2, circleRad*2);
					g2.setColor(getForeground());
					
					g2.drawString(s, 
							x-g2.getFontMetrics().stringWidth(s)/2, 
							y+g2.getFontMetrics().getHeight()/4);
					g2.drawOval(x-circleRad, y-circleRad, circleRad*2, circleRad*2);
					x = (int) ((thisRad-circleRad)*Math.cos(ang2));
					y = (int) ((thisRad-circleRad)*Math.sin(ang2));
					g2.drawLine(0, 0, x, y);
					hour = i;
				}
			}
			for (int i = 0; i<=11; i++) {
				String s = String.format("%02d", i!=0 ? i+12 : 0);
				double ang2 = arc6*(i)-Math.PI/2;
				double thisRad = (2*rad/5);
				x = (int) (thisRad*Math.cos(ang2));
				y = (int) (thisRad*Math.sin(ang2));
				
				
				g2.setColor(numBG);
				g2.fillOval(x-circleRad, y-circleRad, circleRad*2, circleRad*2);
				g2.setColor(getForeground());
				
				g2.drawString(s, 
						x-g2.getFontMetrics().stringWidth(s)/2, 
						y+g2.getFontMetrics().getHeight()/4);
				
				if ((Math.abs(ang-ang2)<arc6/2 || Math.abs(ang-ang2-Math.PI*2)<arc6/2 || Math.abs(ang-ang2+Math.PI*2)<arc6/2) && Math.abs(mouseX*mouseX + mouseY*mouseY)<(rad/2)*(rad/2)) {
					g2.setColor(hilight);
					g2.fillOval(x-circleRad, y-circleRad, circleRad*2, circleRad*2);
					g2.setColor(getForeground());
					
					g2.drawString(s, 
							x-g2.getFontMetrics().stringWidth(s)/2, 
							y+g2.getFontMetrics().getHeight()/4);
					g2.drawOval(x-circleRad, y-circleRad, circleRad*2, circleRad*2);
					x = (int) ((thisRad-circleRad)*Math.cos(ang2));
					y = (int) ((thisRad-circleRad)*Math.sin(ang2));
					g2.drawLine(0, 0, x, y);
					hour = i!=0 ? i+12 : 0;
				}
			}
		} else if (stage == 1) {
			//min select
			g2.fillOval(-2, -2, 4, 4);
			double arc6 = Math.PI/6f;
			for (int i = 0; i<=11; i++) {
				String s = String.format("%02d", i*5);
				double ang2 = arc6*(i+1)-Math.PI/2;
				double thisRad = (3*rad/5);
				x = (int) (thisRad*Math.cos(ang2));
				y = (int) (thisRad*Math.sin(ang2));
								
				g2.setColor(numBG);
				g2.fillOval(x-circleRad, y-circleRad, circleRad*2, circleRad*2);
				g2.setColor(getForeground());
				
				g2.drawString(s, 
						x-g2.getFontMetrics().stringWidth(s)/2, 
						y+g2.getFontMetrics().getHeight()/4);
				
				if (Math.abs(ang-ang2)<arc6/2) {
					g2.setColor(hilight);
					g2.fillOval(x-circleRad, y-circleRad, circleRad*2, circleRad*2);
					g2.setColor(getForeground());
					
					g2.drawString(s, 
							x-g2.getFontMetrics().stringWidth(s)/2, 
							y+g2.getFontMetrics().getHeight()/4);
					g2.drawOval(x-circleRad, y-circleRad, circleRad*2, circleRad*2);
					x = (int) ((thisRad-circleRad)*Math.cos(ang2));
					y = (int) ((thisRad-circleRad)*Math.sin(ang2));
					g2.drawLine(0, 0, x, y);
					min = i*5;
				}
				
				
			} 
		} else {
			String s = LocalTime.of(hour, min).format(dtf);
			g2.drawString(s, 
					-g2.getFontMetrics().stringWidth(s)/2, 
					+g2.getFontMetrics().getHeight()/4);
		}
		
		
		
		//Shape ct = new Rectangle((int)mouseX-5,(int)mouseY-5,10,10);
		
		//g2.fill(ct);
		
		g2.setTransform(at);
	}
	
	public boolean isDone() {
		return stage == 2;
	}
	
	public LocalTime getTime() {
		return LocalTime.of(hour, min);
	}
	
}
