package se.fredin.jayplayer.utils;

import javax.swing.ImageIcon;

public class IconLoader {
	
	private final String PATH = "icons/";
	private ImageIcon[] icons;
	
	public final byte BACKWARDS = 0;
	public final byte FORWARD = 1;
	public final byte PAUSE = 2;
	public final byte PLAY = 3;
	public final byte REPEAT = 4;
	public final byte REPEAT_ENABLED = 5;
	public final byte SHUFFLE = 6;
	public final byte SHUFFLE_ENABLED = 7;
	
	public IconLoader() {
		icons = new ImageIcon[] {
			new ImageIcon(PATH + "backwards.png"),
			new ImageIcon(PATH + "forward.png"),
			new ImageIcon(PATH + "pause.png"),
			new ImageIcon(PATH + "play.png"),
			new ImageIcon(PATH + "repeat.png"),
			new ImageIcon(PATH + "repeatEnabled.png"),
			new ImageIcon(PATH + "shuffle.png"),
			new ImageIcon(PATH + "shuffleEnabled.png"),
		};
	}
	
	public ImageIcon getIcon(byte index) {
		return icons[index];
	}

}
