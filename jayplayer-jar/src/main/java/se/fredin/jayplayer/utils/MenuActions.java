package se.fredin.jayplayer.utils;

public enum MenuActions {
	
	NEW_PLAYLIST("New Playlist"),
	LOAD_PLAYLIST("Load Playlist"),
	LOAD_MUSIC("Load Music"),
	QUIT("Quit"),
	CLEAR_TRACKS("Clear Tracks"),
	CLEAR_PLAYLISTS("Clear Playlists"),
	ABOUT("About"),
	EQUALIZER("Equalizer");
		
	private String actionName;
	
	MenuActions(String actionName) {
		this.actionName = actionName;
	}
	
	public String getActionName() {
		return this.actionName;
	}

}
