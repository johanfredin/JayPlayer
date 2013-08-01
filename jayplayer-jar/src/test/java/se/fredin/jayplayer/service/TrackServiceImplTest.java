package se.fredin.jayplayer.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import se.fredin.jayplayer.App;
import se.fredin.jayplayer.domain.Track;

public class TrackServiceImplTest {

	private TrackService trackService;
	
	@Before
	public void init() {
		trackService.addTrack(new Track(App.TEST_DIR + "1.mp3"));
		trackService.addTrack(new Track(App.TEST_DIR + "2.mp3"));
		trackService.addTrack(new Track(App.TEST_DIR + "3.mp3"));
	}
	
	@Test
	public void testPlay() {
		assertEquals("Name is: ", "1", trackService.getTrack(0).getTitle());
		
	}

}
