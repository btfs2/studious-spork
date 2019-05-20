package uk.ac.cam.bizrain.ui.sub;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;

import uk.ac.cam.bizrain.config.BizrainConfig;
import uk.ac.cam.bizrain.location.IPlace;
import uk.ac.cam.bizrain.location.IPlaceSpecific;
import uk.ac.cam.bizrain.schedule.LocalTimeToEpoch;
import uk.ac.cam.bizrain.schedule.ScheduleItem;
import uk.ac.cam.bizrain.ui.comp.SwingUtil;
import uk.ac.cam.bizrain.weather.IWeatherData;
import uk.ac.cam.bizrain.weather.block.IWeatherBlockWorst;

/**
 * The sub panel that displays a Schedule Item
 * 
 * This contains code from the rounded border, which was needed to ensure the rain gif would paint successfully
 * 
 * @see ScheduleItem
 * 
 * @author btfs2, Paulina (docs)
 *
 */
public class PanelLocation extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5858012430000486336L;

	/**
	 * Transcluding from roundend border as need to draw image on border as well as internals
	 */
	int rad = 30;
	private static RenderingHints hints;
	
	//Setup render hints to alias cornes so they don't look shit
	static {
		hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	boolean showRain = false;
	
	/**
	 * Create the panel.
	 */
	public PanelLocation(ScheduleItem schi, IWeatherData locWeather, LocalTimeToEpoch lt2e) {
		// Creates correct insets
		// Does nothing else
		setBorder(new AbstractBorder() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6380746484099845093L;
			
			@Override
		    public Insets getBorderInsets(Component c) {
		        return new Insets(rad/2, rad/2, rad/2, rad/2);
		    }
			
			@Override
		    public void paintBorder(
		            Component c,
		            Graphics g,
		            int x, int y,
		            int width, int height) {
				//DO NOTHING
				//Ensures this does nothing
			}
		});
		//Default background
		setBackground(Color.decode("0xDDDDDD"));
		
		String line1, line2, line3;
		IPlace place = schi.getPlace();
		if (place instanceof IPlaceSpecific) {
			line1 = ((IPlaceSpecific) place).getName();
			line2 = ((IPlaceSpecific) place).getCity() == null ? ((IPlaceSpecific) place).getCountry() : ((IPlaceSpecific) place).getCity();
			line3 = ((IPlaceSpecific) place).getCity() == null ?  null : ((IPlaceSpecific) place).getCountry();
		} else {
			String[] stuff = place.getDisplayName().split(",");
			line1 = stuff[0];
			line2 = stuff.length > 1 ? stuff[1] : "";
			line3 = stuff.length > 2 ? stuff[2] : "";
		}
		
		int l1clip = 14;
		if (line1 != null && line1.length() > l1clip) {
			line1 = line1.substring(0, l1clip-3) + "...";
		}
		
		int l2clip = 14;
		if (line2 != null && line2.length() > l2clip) {
			line2 = line2.substring(0, l2clip-3) + "...";
		}
		
		int l3clip = 14;
		if (line3 != null && line3.length() > l3clip) {
			line3 = line3.substring(0, l3clip-3) + "...";
		}
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 17, 7, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 110));
		GridBagConstraints gbc_rigidArea = new GridBagConstraints();
		gbc_rigidArea.gridheight = 5;
		gbc_rigidArea.insets = new Insets(0, 0, 0, 5);
		gbc_rigidArea.gridx = 1;
		gbc_rigidArea.gridy = 0;
		add(rigidArea, gbc_rigidArea);
		
		JLabel lbllocationName = new JLabel(line1);
		lbllocationName.setFont(SwingUtil.getFontTitle().deriveFont(22f));
		GridBagConstraints gbc_lbllocationName = new GridBagConstraints();
		gbc_lbllocationName.anchor = GridBagConstraints.WEST;
		gbc_lbllocationName.insets = new Insets(0, 0, 5, 5);
		gbc_lbllocationName.gridx = 0;
		gbc_lbllocationName.gridy = 0;
		add(lbllocationName, gbc_lbllocationName);
		
		JLabel lbllocationCity = new JLabel(line2);
		lbllocationCity.setFont(SwingUtil.getFontSub().deriveFont(14f));
		GridBagConstraints gbc_lbllocationCity = new GridBagConstraints();
		gbc_lbllocationCity.anchor = GridBagConstraints.WEST;
		gbc_lbllocationCity.insets = new Insets(0, 0, 5, 5);
		gbc_lbllocationCity.gridx = 0;
		gbc_lbllocationCity.gridy = 1;
		add(lbllocationCity, gbc_lbllocationCity);
		
		JLabel lbllocationCountry = new JLabel(line3);
		lbllocationCountry.setFont(SwingUtil.getFontSub().deriveFont(14f));
		GridBagConstraints gbc_lbllocationCountry = new GridBagConstraints();
		gbc_lbllocationCountry.anchor = GridBagConstraints.WEST;
		gbc_lbllocationCountry.insets = new Insets(0, 0, 5, 5);
		gbc_lbllocationCountry.gridx = 0;
		gbc_lbllocationCountry.gridy = 2;
		add(lbllocationCountry, gbc_lbllocationCountry);
		
		JLabel lblfromto = new JLabel(String.format("%s-%s", 
				schi.getStart().format(DateTimeFormatter.ofPattern("HH:mm")),
				schi.getEnd().format(DateTimeFormatter.ofPattern("HH:mm"))));
		lblfromto.setFont(SwingUtil.getFontNum().deriveFont(15f).deriveFont(Font.BOLD));
		GridBagConstraints gbc_lblfromto = new GridBagConstraints();
		gbc_lblfromto.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblfromto.insets = new Insets(0, 0, 0, 5);
		gbc_lblfromto.gridx = 0;
		gbc_lblfromto.gridy = 4;
		add(lblfromto, gbc_lblfromto);
		
		long start = lt2e.toEpoch(schi.getStart());
		long end = lt2e.toEpoch(schi.getEnd());
		IWeatherBlockWorst worst = locWeather.getWeatherWorstIn(start, end);
		JLabel lblmax = new JLabel(String.format("%.0f\u00B0/%.0f\u00B0",
				worst.getWeatherMaxTemperature(), worst.getWeatherMinTemperature()));
		if (worst.getWeatherMaxTemperature() == -1*Float.MAX_VALUE) {
			lblmax.setText("No Data");
		} else {
			if (schi.getEnd().isAfter(LocalTime.now(ZoneId.of(BizrainConfig.INSTANCE.timeZoneId)))) {
				setBackground(SwingUtil.tempToColor((worst.getWeatherMaxTemperature() + worst.getWeatherMinTemperature())/2));
			}
		}
		if (schi.getEnd().isBefore(LocalTime.now(ZoneId.of(BizrainConfig.INSTANCE.timeZoneId)))) {
			lblmax.setText("Complete");
		}
		lblmax.setFont(SwingUtil.getFontNum().deriveFont(18f).deriveFont(Font.BOLD));
		GridBagConstraints gbc_lblmax = new GridBagConstraints();
		gbc_lblmax.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblmax.gridx = 2;
		gbc_lblmax.gridy = 4;
		add(lblmax, gbc_lblmax);
		
		
		
		JLabel lblIco = new JLabel("");
		lblIco.setIcon(SwingUtil.getIconOfWeather(worst.getWeatherWorstIcon()));
		GridBagConstraints gbc_lblIco = new GridBagConstraints();
		gbc_lblIco.anchor = GridBagConstraints.EAST;
		gbc_lblIco.gridheight = 4;
		gbc_lblIco.insets = new Insets(0, 0, 5, 0);
		gbc_lblIco.gridx = 2;
		gbc_lblIco.gridy = 0;
		add(lblIco, gbc_lblIco);

		showRain = worst.getWeatherMaxPrecipProb() > 0.65;
	}
	
	// Easiest way to load gif as found by Agni and Elanor
	// through digging through stack overflow
	Image i = new ImageIcon(PanelLocation.class.getResource("/uk/ac/cam/bizrain/ui/weather/rain-fixed.gif")).getImage();
	
	@Override
	protected void paintComponent(Graphics g) {
		// 90% of this code is from Rounded Border
        Graphics2D g2 = (Graphics2D) g;
        Shape baseclip = g2.getClip();
        
        RoundRectangle2D.Double rrect = new RoundRectangle2D.Double(
                0, 0,
                this.getWidth(),
                this.getHeight(),
                rad,
                rad);
        
        g2.setRenderingHints(hints);
        
		Component parent  = this.getParent();
        if (parent!=null) {
            Color bg = parent.getBackground();
            Rectangle rect = new Rectangle(0,0,this.getWidth(), this.getHeight());
            Area borderRegion = new Area(rect);
            borderRegion.subtract(new Area(rrect));
            g2.setClip(borderRegion);
            g2.setColor(bg);
            //g2.setColor(Color.PINK);
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2.setClip(baseclip);
        }
        
        g2.setColor(this.getBackground());
        g2.draw(rrect);
        g2.fill(rrect);
        
        // Only unique thing about this
        if (showRain) {
	        g2.setClip(rrect);
	        g2.drawImage(i, 0, 0, this);
	        g2.setClip(baseclip);
        }
        
        //not needed as children are drawn by different function
		//super.paintComponent(g);
	}
	

}
