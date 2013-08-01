package se.fredin.jayplayer.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface PlayistService {
	
	JSONArray getPlaylists();
	
	JSONObject getPlaylist(int id);
	
	void addPlaylist();
	
	void deletePlaylist(int id);
	
	void clearPlaylists();
}
