package se.fredin.jayplayer.service;

import java.util.HashMap;
import java.util.Map;

import javafx.util.Duration;

import org.springframework.stereotype.Service;

import se.fredin.jayplayer.domain.Track;

@Service
public class TrackServiceImpl implements TrackService {

	private Map<Integer, Track> tracks = new HashMap<Integer, Track>();
	private int id;
	private boolean repeat;
	private boolean shuffle;
	private String status;
	private int trackCount;
	
		
	@Override
	public Track addTrack(Track track) {
		track.setId(nextId());
		tracks.put(track.getId(), track);
		return track;
	}

	@Override
	public Track getTrack(int id) {
		return tracks.get(id);
	}
	
	@Override
	public void playTrack(int id) {
		this.id = id;
		stop();
		tracks.get(id).play();
		status = "Now playing " + tracks.get(id).getTitle();
	}

	@Override
	public void stop() {
		for(Track t : tracks.values())
			t.stop();
		status = "Stopped All Music";
	}

	@Override
	public void nextTrack() {
		if(shuffle) {
			playRandomTrack();
			return;
		}
		
		if(!repeat && id >= tracks.size() - 1) 
			id = tracks.size() - 1;
		else if(repeat && id >= tracks.size() - 1) 
			id = 0;
		else
			id++;
		
		playTrack(id);
	}

	@Override
	public void previousTrack() {
		if(shuffle) {
			playRandomTrack();
			return;
		}
		
		if(!repeat && id <= 0)
			id = 0;
		else if(repeat && id <= 0) 
			id = tracks.size() - 1;
		else
			id--;
		
		playTrack(id);
	}

	private void playRandomTrack() {
		int randomTrack = (int)(Math.random() * tracks.size());
		for(Track t : tracks.values()) {
			if(!t.wasPlayed()) {
				trackCount++;
				playTrack(randomTrack);
				break;
			} else if(trackCount >= tracks.size() && repeat) {
				setWasPlayed(false);
				playTrack(randomTrack);
				trackCount = 0;
			} else
				stop();
		}
	}
	
	@Override
	public void setWasPlayed(boolean wasPlayed) {
		for(Track t : tracks.values())
			t.setWasPlayed(wasPlayed);
	}

	@Override
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
	
	@Override
	public void setVolume(float volume) {
		for(Track t : tracks.values())
			t.setVolume(volume);
	}

	@Override
	public void deleteTrack(int id) {
		status = "Removed " + tracks.get(id).getTitle();
		tracks.remove(id);
	}

	@Override
	public void clearTrackList() {
		tracks.clear();
		status = "Removed All Tracks";
	}
	
	@Override
	public int nextId() {
		return this.id++;
	}
	
	@Override
	public int previousId() {
		return this.id--;
	}
	
	@Override
	public void repeat(boolean repeat) {
		if(repeat) 
			status = "Repeat Enabled";
		else
			status = "Repeat Disabled";
		this.repeat = repeat;
	}

	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStatus() {
		return this.status;
	}

	@Override
	public Duration getCurrentTime(int id) {
		return tracks.get(id).getCurrentTime();
	}
	
	@Override
	public Duration getTotalTime(int id) {
		return tracks.get(id).getTotalTime();
	}
	
	@Override
	public int getTrackAmount() {
		return tracks.size();
	}
	
	@Override
	public boolean isEmpty() {
		return tracks.isEmpty();
	}

}
