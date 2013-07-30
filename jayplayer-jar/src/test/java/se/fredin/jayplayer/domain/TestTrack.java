package se.fredin.jayplayer.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.fredin.jayplayer.App;

public class TestTrack {

	private Track track;
	
	@Before
	public void init() {
		track = new Track(App.TEST_DIR + "1.mp3");
	}
	
	@Test
	public void testTitle() {
		assertFalse("The title should not have an ending suffix ", track.getTitle().contains(".mp3"));
		assertEquals("The title should now be: ", "1", track.getTitle());
	}

}
