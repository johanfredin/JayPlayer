package se.fredin.jayplayer.domain;

public class MockTrack extends Track {

	private final String MOCK_PATH = "C:/MockDir/";
	private final String MOCK_SUFFIX = ".mp3";
	private String path;
	private String title;
	
		
	public MockTrack(String path) {
		path = MOCK_PATH + path + MOCK_SUFFIX;
		this.path = path;
		// We need to fix the path setup of the track so it matches the syntax of javafx
		if(!path.startsWith("file:///"))
			path = "file:///" + path;
		if(path.contains("\\"))
			path = path.replace('\\', '/');
		if(path.contains(" "))
			path = path.replace(" ", "%20");
	}
	
	public void play() {
		setWasPlayed(true);
	}
	
	@Override
	public void stop() {}
	
	@Override
	public String getTitle() {
		title = path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('.'));
		if(title.contains("%20"))
			title = title.replace("%20", " ");
		return title;
	}
		
}
