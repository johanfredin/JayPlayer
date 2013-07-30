package se.fredin.jayplayer.service;

import se.fredin.jayplayer.domain.Track;

public interface TrackService {

	Track addTrack(Track track);
	
	Track getTrack(int id);
	
	void playTrack(int id);
	
	void stop();
	
	void nextTrack();
	
	void previousTrack();
	
	void shuffle(boolean shuffle);
	
	void repeat(boolean repeat);
	
	void setVolume(float volume);
	
	void deleteTrack(int id);
	
	void clearTrackList();
	
	int getId();
	
	int nextId();
	
	int previousId();
	
	void setId(int id);
	
	String getStatus();
}
