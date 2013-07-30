package se.fredin.jayplayer.database;

import se.fredin.jayplayer.domain.Track;

public interface TrackRepository {
	
	Track addTrack(Track track);
	
	Track getTrack(long id);
	
	void playTrack(long id);
	
	void stop();
	
	void nextTrack();
	
	void previousTrack();
	
	void shuffle();
	
	void setVolume(float volume);
	
	void deleteTrack(long id);
	
	void clearTrackList();
	
}