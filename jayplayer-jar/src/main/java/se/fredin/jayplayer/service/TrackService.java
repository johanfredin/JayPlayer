package se.fredin.jayplayer.service;

import java.util.ArrayList;
import java.util.List;

import se.fredin.jayplayer.domain.Track;

/**
 * TrackService manages all users {@link Track} objects. 
 *  @author johan
 *
 */
public class TrackService {

	private List<Track> tracks = new ArrayList<Track>();
	private int id;
	private boolean repeat;
	private boolean shuffle;
	private boolean stopTimer;
	private String status;
	private int trackCount;
	
	/**
	 * Retrieves the list with the tracks
	 * @return the list with the tracks
	 */
	public List<Track> getTracks() {
		return this.tracks;
	}
	/**
	 * Sets the track list 
	 * @param tracks the track list
	 */
	public void setTrackList(List<Track> tracks) {
		this.tracks = tracks;
	}
	
	/**
	 * Check if the track timer is started or not
	 * @return <b>true</b> if the timer was stopped
	 */
	public boolean isStopTimer() {
		return this.stopTimer;
	}
	/**
	 * Set the timer to stop or not
	 * @param stopTimer stop the timer or not
	 */
	public void setStopTimer(boolean stopTimer) {
		this.stopTimer = stopTimer;
	}
	
	/**
	 * Get the current id e.g index in the list
	 * @return the current index of the list
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * Set the current id e.g index in the list
	 * @param id specified index
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Get the current status.
	 * @return current status
	 */
	public String getStatus() {
		return this.status;
	}
	
	/**
	 * Add a new Track object to the tracks list
	 * @param track the Track user wishes to add
	 */
	public void addTrack(Track track) {
		tracks.add(track);
	}

	/**
	 * Retrieves the Track object at specified index
	 * @param id the specified index
	 * @return Track object associated with specified index
	 */
	public Track getTrack(int id) {
		return tracks.get(id);
	}
	
	/**
	 * Play the selected track
	 * @param id selected index
	 * @param r needed for track see play in {@link Track}
	 */
	public void playTrack(int id, Runnable r) {
		if(!tracks.isEmpty()) {
			this.id = id;
			stop();
			try { 
				Thread.sleep(100); 
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			tracks.get(id).play(r);
			stopTimer = false;
			status = "Now playing " + tracks.get(id).getTitle();
		}
	}
	
	/**
	 * Set the playback rate for selected track
	 * @param playBackRate playback rate for selected track
	 */
	public void setPlayBackRate(float playBackRate) {
		for(Track track : tracks)
			track.setPlayBackRate(playBackRate);
	}
	
	/**
	 * Set the balance for selected track
	 * @param balance balance for selected track
	 */
	public void setBalance(float balance) {
		for(Track track : tracks)
			track.setBalance(balance);
	}
	
	/**
	 * Stops playback of all tracks
	 */
	public void stop() {
		if(!tracks.isEmpty()) {
			for(Track t : tracks)
				t.stop();
			stopTimer = true;
			status = "Stopped All Music";
		}
	}

	/**
	 * Plays next track in the list.
	 * Will stop at last index if repeat is not enabled.
	 * @param r needed to play track. see {@link Track}
	 */
	public void nextTrack(Runnable r) {
		if(!tracks.isEmpty()) {
			if(shuffle) {
				playRandomTrack(r);
				return;
			}
			
			if(!repeat && id >= tracks.size() - 1) 
				id = tracks.size() - 1;
			else if(repeat && id >= tracks.size() - 1) 
				id = 0;
			else
				id++;
			
			playTrack(id, r);
		}
	}
	
	/**
	 * Plays previous track in the list.
	 * Will stop at first index if repeat is not enabled.
	 * @param r needed to play track. see {@link Track}
	 */
	public void previousTrack(Runnable r) {
		if(!tracks.isEmpty()) {
			if(shuffle) {
				playRandomTrack(r);
				return;
			}
			
			if(!repeat && id <= 0)
				id = 0;
			else if(repeat && id <= 0) 
				id = tracks.size() - 1;
			else
				id--;
			
			playTrack(id, r);
		}
	}

	/** 
	 * Set all tracks to played	
	 * @param wasPlayed if the tracks were played or not
	 */
	public void setWasPlayed(boolean wasPlayed) {
		for(Track t : tracks)
			t.setWasPlayed(wasPlayed);
	}

	/**
	 * Set whether to shuffle tracks or not
	 * @param shuffle will shuffle playlist if true
	 */
	public void shuffle(boolean shuffle) {
		this.shuffle = shuffle;
		if(shuffle) 
			status = "Shuffle Enabled";
		else {
			status = "Shuffle Disabled";
			setWasPlayed(false);
			trackCount = 0;
		}
	}
	
	/**
	 * Sets the volume for all tracks
	 * @param volume volume for tracks
	 */
	public void setVolume(float volume) {
		for(Track t : tracks)
			t.setVolume(volume);
	}

	/**
	 * Removes selected track from list
	 * @param id index of Track object
	 */
	public void deleteTrack(int id) {
		if(!tracks.isEmpty()) {
			if(tracks.get(id).isPlaying())
				stopTimer = true;
			tracks.get(id).stop();
			status = "Removed " + tracks.get(id).getTitle();
			tracks.remove(id);
			setId(nextId());
		}
	}

	/**
	 * Remove all {@link Track} objects from list
	 */
	public void clearTrackList() {
		if(!tracks.isEmpty()) {
			stop();
			tracks.clear();
			status = "Removed All Tracks";
		}
	}
	
	/**
	 * Increments the current index by one assuming its not the last index of the tracks list.
	 * @return the next index or the last index if current index is the last.
	 */
	public int nextId() {
		return (this.id >= tracks.size() - 1 ?  tracks.size() - 1 : this.id++);
	}
	
	/**
	 * Decrements the current index by one assuming its not the first index of the tracks list.
	 * @return the previous index or the first if current index is the first.
	 */
	public int previousId() {
		return (this.id <= 0 ? 0 : this.id--);
	}
	
	/**
	 * Sets whether or not to repeat the tracks list
	 * @param repeat if <b>true</b> list will be repeated
	 */
	public void repeat(boolean repeat) {
		if(repeat) 
			status = "Repeat Enabled";
		else
			status = "Repeat Disabled";
		this.repeat = repeat;
	}

	/**
	 * Retrieves the current time in minutes of selected track
	 * @param id track associated with selected index  
	 * @return the current time in minutes of selected track
	 */
	public double getCurrentTime(int id) {
		return tracks.get(id).getCurrentTime();
	}
	
	/**
	 * Retrieves the total length in minutes of selected track
	 * @param id track associated with selected index  
	 * @return the total length in minutes of selected track
	 */
	public double getTotalTime(int id) {
		return tracks.get(id).getTotalTime();
	}
	
	/**
	 * Retrieves the amount of tracks in the list
	 * @return the amount of tracks in the list
	 */
	public int getTrackAmount() {
		return tracks.size();
	}
	
	/**
	 * Retrieves wheter or not the tracks list is empty
	 * @return <b>true</b> if tracks list is empty
	 */
	public boolean isEmpty() {
		return tracks.isEmpty();
	}
	
	private void playRandomTrack(Runnable r) {
		int randomTrack = (int)(Math.random() * tracks.size());
		for(Track t : tracks) {
			if(!t.wasPlayed()) {
				trackCount++;
				playTrack(randomTrack, r);
				break;
			} else if(trackCount >= tracks.size() && repeat) {
				setWasPlayed(false);
				playTrack(randomTrack, r);
				trackCount = 0;
			} else
				stop();
		}
	}
	

	

}
