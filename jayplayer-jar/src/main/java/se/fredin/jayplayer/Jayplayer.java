package se.fredin.jayplayer;

import se.fredin.jayplayer.service.TrackService;
import se.fredin.jayplayer.ui.Display;

/**
 * Starts the application
 * @author johan
 *
 */
public class Jayplayer {
	
	public static final String JAYPLAYER_VERSION = "0.9";
	
	public static void main(String[] args) {
		TrackService service = new TrackService();
		new Display(service);
	}
}
