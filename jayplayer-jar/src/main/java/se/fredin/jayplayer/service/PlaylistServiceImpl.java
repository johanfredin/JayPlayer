package se.fredin.jayplayer.service;

import java.io.File;

import javax.swing.DefaultListModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistServiceImpl implements PlayistService {

	private File JSONFile = new File(System.getProperty("user.home") + "/playlists.JSON");
	private JSONArray playLists = new JSONArray();
	private JSONObject playList = new JSONObject();
	private JSONParser parser = new JSONParser();
	private DefaultListModel<String> playListTitles = new DefaultListModel<String>();
	private int id;
	
	@Autowired
	private TrackService trackService;
	
	@Override
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
	
	@Override
	public JSONObject getPlaylist(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPlaylist() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePlaylist(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearPlaylists() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
}
