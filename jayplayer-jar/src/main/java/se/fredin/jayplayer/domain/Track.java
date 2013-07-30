package se.fredin.jayplayer.domain;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Track {
	
	private String title;
	private String path;
	private MediaPlayer player;
	private Media media;
	private long id;
	
	protected Track() {}
	
	public Track(String path) {
		// We need to fix the path setup of the track so it matches the syntax of javafx
		if(!path.startsWith("file:///"))
			path = "file:///" + path;
		if(path.contains("\\"))
			path = path.replace('\\', '/');
		if(path.contains(" "))
			path = path.replace(" ", "%20");
		this.path = path;
		
		new JFXPanel();
		this.media = new Media(path);
		this.player = new MediaPlayer(media);
	}
	
	
	public String getPath() {
		return this.path;
	}
	
	public String getTitle() {
		title = path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('.'));
		if(title.contains("%20"))
			title = title.replace("%20", " ");
		return title;
	}
	
	public MediaPlayer getPlayer() {
		return this.player;
	}
	
	public void play() {
		if(media != null)
			player.play();
	}
	
	public void stop() {
		player.stop();
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setVolume(float volume) {
		player.setVolume(volume);
	}
	

	
}
