package uk.ac.cam.bizrain.ui.comp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComboBox;
import javax.swing.border.AbstractBorder;

public class RoundedBorder extends AbstractBorder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8423612016554532492L;
	
	int rad = 10;
	private RenderingHints hints;
	Insets moreInset = new Insets(0, 0, 0, 0);
	
	public RoundedBorder() {
		init();
	}
	
	public RoundedBorder(int rad) {
		this.rad = rad;
		init();
	}
	
	public RoundedBorder(int rad, Insets moreInset) {
		this.rad = rad;
		this.moreInset = moreInset;
		init();
	}
	
	private void init() {
		hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	@Override
    public Insets getBorderInsets(Component c) {
        return new Insets(rad/2, rad/2, rad/2, rad/2);
    }
	
	@Override
    public Insets getBorderInsets(Component c, Insets insets) {
        return getBorderInsets(c);
    }
	
	@Override
    public void paintBorder(
            Component c,
            Graphics g,
            int x, int y,
            int width, int height) {

        Graphics2D g2 = (Graphics2D) g;
                
        RoundRectangle2D.Double rrect = new RoundRectangle2D.Double(
                0, 0,
                c.getWidth(),
                c.getHeight(),
                rad,
                rad);
        
        g2.setRenderingHints(hints);
        
        Component parent  = c.getParent();
        if (parent!=null) {
            Color bg = parent.getBackground();
            Rectangle rect = new Rectangle(0,0,width, height);
            Area borderRegion = new Area(rect);
            borderRegion.subtract(new Area(rrect));
            g2.setClip(borderRegion);
            g2.setColor(bg);
            g2.fillRect(0, 0, width, height);
            g2.setClip(null);
        }
        
        g2.setColor(c.getBackground());
        g2.draw(rrect);
        
        Area fill = new Area(rrect);
        if (c instanceof JComboBox<?>) {
        	fill.subtract(new Area(new Rectangle(2+rad/2, 2+rad/2, width-rad-20, height-rad-4)));
        } else {
        	fill.subtract(new Area(new Rectangle(rad/2-moreInset.left, 
        			rad/2-moreInset.top, width-rad-moreInset.right, 
        			height-rad-moreInset.bottom)));
        }
        
        g2.setColor(c.getBackground());
        g2.fill(fill);
	}
}
