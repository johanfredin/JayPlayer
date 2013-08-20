package se.fredin.jayplayer.utils;

/**
 * Helper {@link Enum} for the GUI file menu
 * @author johan
 *
 */
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
	
	private MenuActions(String actionName) {
		this.actionName = actionName;
	}
	
	/**
	 * Retrieves the name associated with current value 
	 * @return the name associated with current value
	 */
	public String getActionName() {
		return this.actionName;
	}

}
