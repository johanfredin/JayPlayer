package se.fredin.jayplayer.service;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import se.fredin.jayplayer.domain.Track;
import se.fredin.jayplayer.utils.PlayerSettings;

public class PlaylistService {

	private File JSONFile;
	private JSONObject playLists;
	private DefaultListModel<String> playListTitles, trackTitles;
	private PlayerSettings playerSettings = new PlayerSettings();
	private int id;
	
	public PlaylistService() {
		JSONFile = new File(System.getProperty("user.home") + "/playlists.JSON");
		playLists = new JSONObject();
		playListTitles = new DefaultListModel<String>();
		trackTitles = new DefaultListModel<String>();
	}
	
	@SuppressWarnings("unchecked")
	public void getPlaylists() {
		try {
			if(playerSettings.loadIsBack().equals(" ")) {
				createNewPlaylist("New Playlist", new ArrayList<Track>());
				return;
			}
			if(JSONFile.exists()) {
						
				JSONParser parser = new JSONParser();
				playLists = (JSONObject) parser.parse(new FileReader(JSONFile));
				System.out.println(playLists.size());
				
				// 1: Give titles to playlist display
				Set<String> names = playLists.keySet();
				for(String name : names) {
					playListTitles.addElement(name);
					List<String> paths = new ArrayList<String>();
					paths.addAll((Collection<? extends String>) playLists.get(name));
					playLists.put(name, paths);
				}
				 
			}
			else {
				JSONFile.createNewFile();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Track> getPlaylist(String id) {
		List<String> paths = (List<String>) playLists.get(id);
		List<Track> tracks = new ArrayList<Track>();
		trackTitles.removeAllElements();
		for(String path : paths) {
			Track t = new Track(path);
			tracks.add(t);
			trackTitles.addElement(t.getTitle());
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
	
	@SuppressWarnings({ "unchecked"})
	public void createNewPlaylist(String nameOfPlaylist, List<Track> tracks) {
		playListTitles.addElement(nameOfPlaylist);
		List<String> paths = new ArrayList<String>();
		for(Track t : tracks) 
			paths.add(t.getPath().toString());
		playLists.put(nameOfPlaylist, paths);
		writeToFile();
	}
	
	@SuppressWarnings("unchecked")
	public void addToPlaylist(String nameOfPlaylist, List<Track> tracks) {
		List<String> paths = new ArrayList<String>();
		for(Track t : tracks) 
			paths.add(t.getPath());
		playLists.put(nameOfPlaylist, paths);
		writeToFile();
	}

	public void deletePlaylist(int id) {
		playLists.remove(id);
		writeToFile();
	}

	public int nextId() {
		return this.id++;
	}
	
	public DefaultListModel<String> getPlayListNames() {
		return this.playListTitles;
	}
	@SuppressWarnings("unchecked")
	public DefaultListModel<String> getTrackNames(String id) {
		List<String> tracks = (List<String>) playLists.get(id);
		for(String title : tracks) {
			Track t = new Track(title);
			trackTitles.addElement(t.getTitle());
		}
		return this.trackTitles;
	}
	

	
	
	
	
	
}
