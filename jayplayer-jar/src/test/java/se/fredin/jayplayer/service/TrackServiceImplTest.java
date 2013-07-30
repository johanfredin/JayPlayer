package se.fredin.jayplayer.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import se.fredin.jayplayer.App;
import se.fredin.jayplayer.domain.Track;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationcontext_mock.xml"})
public class TrackServiceImplTest {

	@Autowired
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
