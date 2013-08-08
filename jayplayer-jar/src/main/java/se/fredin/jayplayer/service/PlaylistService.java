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

public class PlaylistService {

	private File JSONFile;
	private JSONObject playLists;
	private DefaultListModel<String> playListTitles, trackTitles;
			
	public PlaylistService() {
		JSONFile = new File(System.getProperty("user.home") + "/playlists.JSON");
		playLists = new JSONObject();
		playListTitles = new DefaultListModel<String>();
		trackTitles = new DefaultListModel<String>();
	}
	
	/**
	 * Load all the playlist names into the display.
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
	
	@SuppressWarnings("unchecked")
	public List<Track> getPlaylist(String id) {
		List<String> paths = (List<String>) playLists.get(id);	// Get all the track paths from the selected list
		List<Track> tracks = new ArrayList<Track>();
		if(!(paths == null)) {
			trackTitles.removeAllElements();	// Refresh track list
			for(String path : paths) {
				Track track = new Track(path);
				tracks.add(track);
				trackTitles.addElement(track.getTitle());
			}
		}
		return tracks;
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
	
	@SuppressWarnings({ "unchecked"})
	public void createNewPlaylist(String nameOfPlaylist, List<Track> tracks) {
		playListTitles.addElement(nameOfPlaylist);
		List<String> paths = new ArrayList<String>();
		playLists.put(nameOfPlaylist, paths);
		writeToFile();
	}
	
	@SuppressWarnings("unchecked")
	public void updatePlaylist(String nameOfPlaylist, List<Track> tracks) {
		List<String> paths = new ArrayList<String>();
		for(Track t : tracks) 
			paths.add(t.getPath());
		playLists.put(nameOfPlaylist, paths);
		writeToFile();
	}
	
	public void deletePlaylist(String id) {
		trackTitles.removeElement(id);
		playLists.remove(id);
		writeToFile();
	}

		
	public DefaultListModel<String> getPlayListNames() {
		return this.playListTitles;
	}
	
	public DefaultListModel<String> getTrackNames(String id) {
		return this.trackTitles;
	}
	

	
	
	
	
	
}
