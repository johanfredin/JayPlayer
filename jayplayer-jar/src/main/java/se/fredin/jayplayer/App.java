package se.fredin.jayplayer;

import se.fredin.jayplayer.frontend.Display;
import se.fredin.jayplayer.service.TrackService;


public class App {
	
	public static final String JAYPLAYER_VERSION = "0.1";
	
	public static void main(String[] args) {
		TrackService service = new TrackService();
		new Display(service);
	}
}
