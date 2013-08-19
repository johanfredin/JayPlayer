package se.fredin.jayplayer.service;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import se.fredin.jayplayer.domain.Track;

/**
 * PlaylistService manages playlists for users. It will first create a JSON file 
 * in users home folder and will later on send and retrieve information from that file.
 * The playlist file will be created and managed by the class itself so user never needs
 * to specifically call the file. 
 * Playlists are managed with a JSONObject e.g Map<String, List<Track>> where the key is the 
 * title of the playlist and the value is a list containing paths to all the tracks.
 * @author johan
 *
 */
public class PlaylistService {

	private File JSONFile;
	private JSONObject playLists;
	private DefaultListModel<String> playListTitles, trackTitles;
	private String status;
	
	/**
	 * Default constructor. Creates a JSON file in users home folder containing
	 * information about the playlists. 
	 */
	public PlaylistService() {
		JSONFile = new File(System.getProperty("user.home") + "/playlists.JSON");
		playLists = new JSONObject();
		playListTitles = new DefaultListModel<String>();
		trackTitles = new DefaultListModel<String>();
	}
	
	/**
	 * Creates a JSON file at specified file location containing information about 
	 * the playlists.
	 * @param fileLocation preferred location of playlist.
	 */
	public PlaylistService(String fileLocation) {
		JSONFile = new File(fileLocation + "/playlists.JSON");
		playLists = new JSONObject();
		playListTitles = new DefaultListModel<String>();
		trackTitles = new DefaultListModel<String>();
	}
	
	/**
	 * Returns the current status.
	 * @return current status
	 */
	public String getStatus() {
		return this.status;
	}
	
	/**
	 * Get the list containing all the names of the playlists
	 * @return the names of the playlists
	 */
	public DefaultListModel<String> getPlayListNames() {
		return this.playListTitles;
	}
	
	/**
	 * Retrieves the list containing all the names of the tracks
	 * associated with the selected playlist
	 * @param key selected playlist
	 * @return the names of the tracks associated with selected playlist
	 */
	public DefaultListModel<String> getTrackNames(String key) {
		return this.trackTitles;
	}
	
	/**
	 * Retrieves all the keys from JSON file e.g the playlist titles and puts them in a List
	 * Also creates the playlist file if it doesn't exist yet.
	 */
	@SuppressWarnings("unchecked")
	public void getPlaylists() {
		try {
			if(JSONFile.exists()) {
				JSONParser parser = new JSONParser();
				playLists = (JSONObject) parser.parse(new FileReader(JSONFile));
								
				if(!playLists.isEmpty()) {
					Set<String> names = playLists.keySet();
					for(String name : names) 
						playListTitles.addElement(name);
				}
			}
			else {
				JSONFile.createNewFile();
				writeToFile("{}");
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Get the size of the current playlist
	 * @return the size of current playlist
	 */
	public int getSize() {
		return playLists.size();
	}
	
	/**
	 * Gets the tracks associated with selected playlist
	 * and puts them in a List. Will return an empty
	 * list if there are no tracks for the selected playlist or if it
	 * doesn't exist.
	 * @param key The key in the list e.g the title of the playlist
	 * @return the list associated with selected playlist
	 */
	@SuppressWarnings("unchecked")
	public List<Track> getPlaylist(String key) {
		List<String> paths = (List<String>) playLists.get(key);	// Get all the track paths from the selected list
		List<Track> tracks = new ArrayList<Track>();
		if(!(paths == null)) {
			trackTitles.removeAllElements();	// Refresh track list
			for(String path : paths) {
				Track track = new Track(path);
				tracks.add(track);
				trackTitles.addElement(track.getTitle());
			}
		}
		status = "Current Playlist: " + key;
		return tracks;
	}

	/**
	 * Check if the playlists file contains the selected playlist
	 * @param key selected playlist
	 * @return whether or not the file contains the playlist.
	 */
	public boolean containsKey(String key) {
		return playLists.containsKey(key);
	}
	
	
	/**
	 * Creates a new playlist and puts it in the playlist file.
	 * The tracks list will take out each individual track path and 
	 * put to the playlist instead of track objects.
	 * @param nameOfPlaylist the name user wants for the playlist
	 * @param tracks the media files to put into the playlist
	 */
	@SuppressWarnings({ "unchecked"})
	public void createNewPlaylist(String nameOfPlaylist, List<Track> tracks) {
		playListTitles.addElement(nameOfPlaylist);
		List<String> paths = new ArrayList<String>();
		if(!tracks.isEmpty()) {
			for(Track track : tracks) 
				paths.add(track.getPath());
		}
		playLists.put(nameOfPlaylist, paths);
		status = "New Playlist: " + nameOfPlaylist;
		writeToFile();
	}
	
	/**
	 * Update an alreadyexisting playlist.
	 * The tracks list will take out each individual track path and 
	 * put to the playlist instead of track objects.
	 * @param nameOfPlaylist name of the playlist user wishes to update
	 * @param tracks the media files to update the playlist with
	 */
	@SuppressWarnings("unchecked")
	public void updatePlaylist(String nameOfPlaylist, List<Track> tracks) {
		List<String> paths = new ArrayList<String>();
		for(Track t : tracks) 
			paths.add(t.getPath());
		playLists.put(nameOfPlaylist, paths);
		status = "Updated Playlist: " + nameOfPlaylist;
		writeToFile();
	}
	
	/**
	 * Removes the playlist and all tracks associated with it from playlist file.
	 * @param key playlist to remove
	 */
	public void deletePlaylist(String key) {
		if(playLists.containsKey(key)) {
			trackTitles.removeElement(key);
			playLists.remove(key);
			status = "Removed Playlist: " + key;
			writeToFile();
		}
	}

	/**
	 * Removes all playlists
	 */
	public void clearPlaylists() {
		playLists.clear();
		playListTitles.removeAllElements();
		trackTitles.removeAllElements();
		status = "Removed all playlists";
		writeToFile();
	}
	
	private void writeToFile() {
		try {
			PrintWriter pw = new PrintWriter(JSONFile);
			playLists.writeJSONString(pw);
			pw.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void writeToFile(String text) {
		try {
			PrintWriter pw = new PrintWriter(JSONFile);
			pw.write(text);
			pw.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	
	
	
	
	
}
