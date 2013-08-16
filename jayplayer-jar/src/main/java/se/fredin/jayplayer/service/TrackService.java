package se.fredin.jayplayer.service;

import java.util.ArrayList;
import java.util.List;

import se.fredin.jayplayer.domain.Track;

public class TrackService {

	protected List<Track> tracks = new ArrayList<Track>();
	private int id;
	private boolean repeat;
	private boolean shuffle;
	private boolean stopTimer;
	private String status;
	private int trackCount;
	
	public Track addTrack(Track track) {
		track.setId(nextId());
		tracks.add(track);
		return track;
	}

	public Track getTrack(int id) {
		return tracks.get(id);
	}
	
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
	
	public void setPlayBackRate(float playBackRate) {
		for(Track track : tracks)
			track.setPlayBackRate(playBackRate);
	}
	
	public void setBalance(float balance) {
		for(Track track : tracks)
			track.setBalance(balance);
	}
	
	

	public void stop() {
		if(!tracks.isEmpty()) {
			for(Track t : tracks)
				t.stop();
			stopTimer = true;
			status = "Stopped All Music";
		}
	}

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
	
	public void setWasPlayed(boolean wasPlayed) {
		for(Track t : tracks)
			t.setWasPlayed(wasPlayed);
	}

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
	
	public void setVolume(float volume) {
		for(Track t : tracks)
			t.setVolume(volume);
	}

	public void deleteTrack(int id) {
		if(!tracks.isEmpty()) {
			status = "Removed " + tracks.get(id).getTitle();
			tracks.remove(id);
		}
	}

	public void clearTrackList() {
		if(!tracks.isEmpty()) {
			tracks.clear();
			status = "Removed All Tracks";
		}
	}
	
	public int nextId() {
		return this.id++;
	}
	
	public int previousId() {
		return this.id--;
	}
	
	public void repeat(boolean repeat) {
		if(repeat) 
			status = "Repeat Enabled";
		else
			status = "Repeat Disabled";
		this.repeat = repeat;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStatus() {
		return this.status;
	}

	public double getCurrentTime(int id) {
		return tracks.get(id).getCurrentTime();
	}
	
	public double getTotalTime(int id) {
		return tracks.get(id).getTotalTime();
	}
	
	public int getTrackAmount() {
		return tracks.size();
	}
	
	public boolean isEmpty() {
		return tracks.isEmpty();
	}
	
	public List<Track> getTracks() {
		return this.tracks;
	}
	
	public void setTrackList(List<Track> tracks) {
		this.tracks = tracks;
	}
	
	public double getTimeRemaining(int id) {
		return tracks.get(id).getTimeRemaining();
	}
	
	public boolean isStopTimer() {
		return this.stopTimer;
	}
	

	

}
