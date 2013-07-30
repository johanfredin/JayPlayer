package se.fredin.jayplayer.database.mock;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import se.fredin.jayplayer.database.TrackRepository;
import se.fredin.jayplayer.domain.Track;

@Repository
public class MockTrackRepository implements TrackRepository {

	private Map<Long, Track> tracks = new HashMap<Long, Track>();
	private long id;
	private long currentTrackIndex;
	
	@Override
	public Track addTrack(Track track) {
		track.setId(nextId());
		tracks.put(track.getId(), track);
		return track;
	}

	@Override
	public Track getTrack(long id) {
		currentTrackIndex = id;
		return tracks.get(id);
	}
	
	@Override
	public void playTrack(long id) {
		stop();
		tracks.get(id).play();
		currentTrackIndex = id;
	}

	@Override
	public void stop() {
		for(Track t : tracks.values())
			t.stop();
	}

	@Override
	public void nextTrack() {
		currentTrackIndex++;
		playTrack(currentTrackIndex);
	}

	@Override
	public void previousTrack() {
		currentTrackIndex--;
		playTrack(currentTrackIndex);
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
	
	private long nextId() {
		return this.id++;
	}

}
