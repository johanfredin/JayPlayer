package se.fredin.jayplayer.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import se.fredin.jayplayer.domain.Track;

@Service
public class TrackServiceImpl implements TrackService {

	private Map<Long, Track> tracks = new HashMap<Long, Track>();
	private long id;
		
	@Override
	public Track addTrack(Track track) {
		track.setId(nextId());
		tracks.put(track.getId(), track);
		return track;
	}

	@Override
	public Track getTrack(long id) {
		return tracks.get(id);
	}
	
	@Override
	public void playTrack(long id) {
		stop();
		tracks.get(id).play();
	}

	@Override
	public void stop() {
		for(Track t : tracks.values())
			t.stop();
	}

	@Override
	public void nextTrack() {
		playTrack(nextId());
	}

	@Override
	public void previousTrack() {
		playTrack(previousId());
	}

	@Override
	public void shuffle() {
		playTrack((int) Math.random() * tracks.size());
	}
	
	@Override
	public void setVolume(float volume) {
		for(Track t : tracks.values())
			t.setVolume(volume);
	}

	@Override
	public void deleteTrack(long id) {
		tracks.remove(id);
	}

	@Override
	public void clearTrackList() {
		tracks.clear();
	}
	
	public long nextId() {
		return this.id++;
	}
	public long previousId() {
		return this.id--;
	}

	@Override
	public void repeat(boolean repeat) {
		// TODO Auto-generated method stub
		
	}

}
