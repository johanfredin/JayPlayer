package se.fredin.jayplayer.frontend;

public enum MenuActions {
	
	NEW_PLAYLIST("New Playlist"),
	LOAD_PLAYLIST("Load Playlist"),
	LOAD_MUSIC("Load Music"),
	QUIT("Quit"),
	CLEAR_TRACKS("Clear Tracks"),
	CLEAR_PLAYLISTS("Clear Playlists");
	
	private String actionName;
	
	MenuActions(String actionName) {
		this.actionName = actionName;
	}
	
	public String getActionName() {
		return this.actionName;
	}

}
