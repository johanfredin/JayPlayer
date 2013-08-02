package se.fredin.jayplayer.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class TestTrack {

	private Track track;
	
	@Before
	public void init() {
		
	}
	
	@Test
	public void testTitle() {
		assertFalse("The title should not have an ending suffix ", track.getTitle().contains(".mp3"));
		assertEquals("The title should now be: ", "1", track.getTitle());
	}

}
