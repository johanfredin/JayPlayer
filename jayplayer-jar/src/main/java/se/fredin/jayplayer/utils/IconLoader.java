package se.fredin.jayplayer.utils;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class IconLoader {
	
	private final String PATH = "/res/";
	private Map<String, ImageIcon> iconsMap;
	
		
	public final String BACKWARDS = "backwards";
	public final String FORWARD = "forward";
	public final String PAUSE = "pause";
	public final String PLAY = "play";
	public final String REPEAT = "repeat";
	public final String REPEAT_ENABLED = "repeatEnabled";
	public final String SHUFFLE = "shuffle";
	public final String SHUFFLE_ENABLED = "shuffleEnabled";
	
	public IconLoader() {
		iconsMap = new HashMap<String, ImageIcon>();
		iconsMap.put(BACKWARDS, new ImageIcon(IconLoader.class.getResource(PATH + "backwards.png")));
		iconsMap.put(FORWARD, new ImageIcon(IconLoader.class.getResource(PATH + "forward.png")));
		iconsMap.put(PAUSE, new ImageIcon(IconLoader.class.getResource(PATH + "pause.png")));
		iconsMap.put(PLAY, new ImageIcon(IconLoader.class.getResource(PATH + "play.png")));
		iconsMap.put(REPEAT, new ImageIcon(IconLoader.class.getResource(PATH + "repeat.png")));
		iconsMap.put(REPEAT_ENABLED, new ImageIcon(IconLoader.class.getResource(PATH + "repeatEnabled.png")));
		iconsMap.put(SHUFFLE, new ImageIcon(IconLoader.class.getResource(PATH + "shuffle.png")));
		iconsMap.put(SHUFFLE_ENABLED, new ImageIcon(IconLoader.class.getResource(PATH + "shuffleEnabled.png")));
	}
	
	public String getPath(String icon) {
		return PATH + icon + ".png";
	}
	
	public ImageIcon getIcon(String key) {
		return iconsMap.get(key); 
	}

}
