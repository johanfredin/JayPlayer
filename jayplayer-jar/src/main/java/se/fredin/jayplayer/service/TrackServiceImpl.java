package se.fredin.jayplayer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.fredin.jayplayer.database.TrackRepository;
import se.fredin.jayplayer.domain.Track;

@Service
public class TrackServiceImpl implements TrackService {

	@Autowired
	private TrackRepository trackRepository;
	
	@Override
	public Track addTrack(Track track) {
		return trackRepository.addTrack(track);
	}
	
	@Override
	public Track getTrack(long id) {
		return trackRepository.getTrack(id);
	}

	@Override
	public void playTrack(long id) {
		trackRepository.playTrack(id);
	}

	@Override
	public void stop() {
		trackRepository.stop();
	}

	@Override
	public void nextTrack() {
		trackRepository.nextTrack();
	}

	@Override
	public void previousTrack() {
		trackRepository.previousTrack();
	}

	@Override
	public void shuffle() {
		trackRepository.shuffle();
	}
	
	@Override
	public void setVolume(float volume) {
		trackRepository.setVolume(volume);
	}

	@Override
	public void deleteTrack(long id) {
		trackRepository.deleteTrack(id);
	}

	@Override
	public void clearTrackList() {
		trackRepository.clearTrackList();
	}

}
