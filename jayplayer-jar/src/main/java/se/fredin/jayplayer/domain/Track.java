package se.fredin.jayplayer.domain;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Creates a Media object and assings it to a MediaPlayer object. 
 * Contains methods for playing, stopping, volume as well as manipulating playback rate and balance.
 * @author johan
 *
 */
public class Track {
	
	private String title;
	private String path;
	private MediaPlayer player;
	private Media media;
	private boolean isPlaying, wasPlayed;
	
	/**
	 * Used for inheritance only
	 */
	protected Track() {}
	
	/**
	 * Default constructor. Takes a path and assigns it to a Media object.
	 * If necessary the path is manipulated to fit the syntax javafx uses. 
	 * This is required when loading a local music file.
	 * @param path The absolute path to where the music file is located.
	 */
	public Track(String path) {
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
		
	/**
	 * Gets the path to the media file
	 * @return The path to the media file
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Retrieves the title from the path by cutting it out.
	 * It's pretty low level but it works!
	 * @return The title of the media file
	 */
	public String getTitle() {
		title = path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('.'));
		if(title.contains("%20"))
			title = title.replace("%20", " ");
		return title;
	}
	
	/**
	 * Plays the music file.	
	 * @param r A runnable responsible for what to do when the track is finished playing.
	 */
	public void play(Runnable r) {
		if(media != null) {
			player.play();
			player.onEndOfMediaProperty().setValue(r);
			wasPlayed = true;
			isPlaying = true;
		}
	}
	
	/**
	 * Stops playing the music file.
	 */
	public void stop() {
		player.stop();
		isPlaying = false;
	}
	
	/**
	 * Sets the balance of the music file.
	 * Ranges from -1.0 to 1.0. Default is 0.0 e.g stereo
	 * @param balance the balance
	 */
	public void setBalance(float balance) {
		if(balance < -1 || balance > 1)
			player.setBalance(0.0);
		else
			player.setBalance(balance);
	}
	
	/**
	 * Sets the playback rate for the media file e.g speeds it up or slows it down.
	 * Ranges from 0.0 to 8.0. Default is 1.0
	 * @param playBackRate the playback rate
	 */
	public void setPlayBackRate(float playBackRate) {
		if(playBackRate < 0.0 || playBackRate > 8.0)
			player.setRate(1.0);
		else
			player.setRate(playBackRate);
	}
	
	/**	
	 * Sets the volume for the media file.
	 * ranges from 0.0 to 1.0. Default is 1.0.
	 * @param volume the volume
	 */
	public void setVolume(float volume) {
		if(media != null) {
			if(volume < 0.0 || volume > 1.0)
				player.setVolume(1.0);
			else
				player.setVolume(volume);
		}
	}
	
	/**
	 * Gets the volume for the media file.
	 * @return the volume
	 */
	public double getVolume() {
		if(media != null)
			return player.getVolume();
		return 1;
	}
	
	/**
	 * Gets the current time in minutes.
	 * @return current time in minutes
	 */
	public double getCurrentTime() {
		return player.getCurrentTime().toMinutes();
	}
	
	/**
	 * Gets the total time of the media file in minutes
	 * @return the total time in minutes
	 */
	public double getTotalTime() {
		return player.getTotalDuration().toMinutes();
	}
	
	/**
	 * Gets if the media file was played or not
	 * @return was media played
	 */
	public boolean wasPlayed() {
		return this.wasPlayed;
	}
	
	/**
	 * Sets if the media was played or not.
	 * @param wasPlayed was the media played
	 */
	public void setWasPlayed(boolean wasPlayed) {
		if(media != null)
			this.wasPlayed = wasPlayed;
	}
	
	/**
	 * Gets the status on the media file playing
	 * @return is the media file playing
	 */
	public boolean isPlaying() {
		return this.isPlaying;
	}
	
	
	
}
