package se.fredin.jayplayer.utils;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class IconLoader {
	
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
		iconsMap.put(BACKWARDS, get("backwards.png"));
		iconsMap.put(FORWARD, get("forward.png"));
		iconsMap.put(PAUSE, get("pause.png"));
		iconsMap.put(PLAY, get("play.png"));
		iconsMap.put(REPEAT, get("repeat.png"));
		iconsMap.put(REPEAT_ENABLED, get("repeatEnabled.png"));
		iconsMap.put(SHUFFLE, get("shuffle.png"));
		iconsMap.put(SHUFFLE_ENABLED, get("shuffleEnabled.png"));
	}
	
	private ImageIcon get(String path) {
		return new ImageIcon(IconLoader.class.getResource("/res/" + path));
	}
	
	public ImageIcon getIcon(String key) {
		return iconsMap.get(key); 
	}

}
