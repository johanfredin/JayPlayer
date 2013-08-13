package se.fredin.jayplayer.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import se.fredin.jayplayer.domain.MockTrack;

public class TrackServiceImplTest {

	private TrackService trackService;
	
	@Before
	public void init() {
		trackService = new TrackService();
		trackService.addTrack(new MockTrack("Hey Jude"));
		trackService.addTrack(new MockTrack("Get back"));
		trackService.addTrack(new MockTrack("Yesterday"));
	}
	
	@Test
	public void testPlay() {
		trackService.playTrack(0, new Thread());
		assertEquals("Name is: ", "Hey Jude", trackService.getTrack(0).getTitle());
	}
	
	@Test
	public void testRemove() {
		trackService.deleteTrack(1);
		assertEquals(2, trackService.getTrackAmount());
	}

}
