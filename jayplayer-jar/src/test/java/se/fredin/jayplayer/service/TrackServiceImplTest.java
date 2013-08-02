package se.fredin.jayplayer.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TrackServiceImplTest {

	private TrackService trackService;
	
	@Before
	public void init() {
		
	}
	
	@Test
	public void testPlay() {
		assertEquals("Name is: ", "1", trackService.getTrack(0).getTitle());
		
	}

}
