package se.fredin.jayplayer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.swing.ImageIcon;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PlayerSettings {

	private File settingsFile;
	private JSONArray settingsList;
	private JSONObject settingsObject;
	private JSONParser parser;
	private PrintWriter pw;
	private BufferedReader br;

	public PlayerSettings() {
		settingsFile = new File(System.getProperty("user.home") + "/jayplayer-settings.JSON");
		settingsList = new JSONArray();
		settingsObject = new JSONObject();
		parser = new JSONParser();
	}
	
	private PrintWriter getWriter() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(settingsFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pw;
	}
	
	@SuppressWarnings("unchecked")
	public void saveShuffleSettings(String iconName) {
		settingsObject.put("shuffleIcon", iconName);
		writeToFile();
	}

	@SuppressWarnings("unchecked")
	public void saveRepeatSettings(ImageIcon repeatIcon) {
		settingsObject.put("repeatIcon", repeatIcon);
		writeToFile();
	}

	@SuppressWarnings("unchecked")
	public void saveSelectedIndex(Integer index) {
		settingsObject.put("currentIndex", index);
		writeToFile();
	}
	
	@SuppressWarnings("unchecked")
	public void saveVolumeSettings(Float volume) {
		settingsObject.put("volume", volume);
		writeToFile();
	}
	
	@SuppressWarnings("unchecked")
	public void writeToFile() {
		pw = getWriter();
		try {
			if(settingsList.contains(settingsObject)) {
				settingsList.set(0, settingsObject);
			} else
				settingsList.add(settingsObject);
			settingsList.writeJSONString(pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pw.close();
	}
	
	public Object getFromFile(String key) {
		Object o = null;
		try {
			if(!settingsFile.exists())
				settingsFile.createNewFile();
			
			br = new BufferedReader(new FileReader(settingsFile));
			settingsList = (JSONArray) parser.parse(br);
			settingsObject = (JSONObject) settingsList.get(0);
			o = settingsObject.get(key);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	public String loadShuffleSettings() {
		System.out.println("In loafShuffleSettings() " + getFromFile("shuffleIcon"));
		return getFromFile("shuffleIcon").toString();
	}

	public ImageIcon loadSaveRepeatSettings() {
		// TODO Auto-generated method stub
		return null;
	}

	public int loadSelectedIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int loadVolume() {
		// TODO Auto-generated method stub
		return 0;
	}

}
