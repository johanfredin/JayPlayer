package se.fredin.jayplayer.service;

import java.io.File;

import javax.swing.DefaultListModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PlaylistService {

	private File JSONFile = new File(System.getProperty("user.home") + "/playlists.JSON");
	private JSONArray playLists = new JSONArray();
	private JSONObject playList = new JSONObject();
	private JSONParser parser = new JSONParser();
	private DefaultListModel<String> playListTitles = new DefaultListModel<String>();
	private int id;
	
	public JSONArray getPlaylists() {
		try {
			if(JSONFile.exists() && !playLists.isEmpty()) {
				
			}
			else {
				JSONFile.createNewFile();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public JSONObject getPlaylist(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addPlaylist() {
		// TODO Auto-generated method stub
		
	}

	public void deletePlaylist(int id) {
		// TODO Auto-generated method stub
		
	}

	public void clearPlaylists() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
}
