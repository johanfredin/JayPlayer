package se.fredin.jayplayer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * A class that saves user preferences. It saves and reads data from a JSON file.
 * By default this file will be added to users home directory. The user will never
 * explicitly have to handle file I/O, the class will do that automatically. File I/O
 * is handled using a {@link JSONObject} and a {@link JSONArray}.
 * @author johan
 *
 */
public class PlayerSettings {

	private File settingsFile;
	private JSONArray settingsList;
	private JSONObject settingsObject;
	private JSONParser parser;
	private PrintWriter pw;
	private BufferedReader br;

	/**
	 * Default constructor. Creates the settings file in users home directory.
	 */
	public PlayerSettings() {
		settingsFile = new File(System.getProperty("user.home") + "/jayplayer-settings.JSON");
		settingsList = new JSONArray();
		settingsObject = new JSONObject();
		parser = new JSONParser();
	}
	
	/**
	 * Same as default constructor but here user gets to select a directory in which to create
	 * the settings file
	 * @param customPath preferred directory of settings file.
	 */
	public PlayerSettings(String customPath) {
		settingsFile = new File(customPath + "/jayplayer-settings.JSON");
		settingsList = new JSONArray();
		settingsObject = new JSONObject();
		parser = new JSONParser();
	}
		
	/**
	 * Save shuffle settings
	 * @param shuffleIcon the current value of shuffle
	 */
	@SuppressWarnings("unchecked")
	public void saveShuffleSettings(String shuffleIcon) {
		settingsObject.put("shuffleIcon", shuffleIcon);
		writeToFile();
	}

	/**
	 * Save repeat settings
	 * @param repeatIcon the current value of repeat
	 */
	@SuppressWarnings("unchecked")
	public void saveRepeatSettings(String repeatIcon) {
		settingsObject.put("repeatIcon", repeatIcon);
		writeToFile();
	}

	/**
	 * Save the current index in tracks list
	 * @param index current track index
	 */
	@SuppressWarnings("unchecked")
	public void saveSelectedTrackIndex(String index) {
		settingsObject.put("currentIndex", index);
		writeToFile();
	}
	
	/**
	 * Save the current selected playlist
	 * @param index current playlist index
	 */
	@SuppressWarnings("unchecked")
	public void saveSelectedPlaylistIndex(String index) {
		settingsObject.put("currentPlaylistIndex", index);
		writeToFile();
	}
	
	/**
	 * Save current volume
	 * @param volume current volume
	 */
	@SuppressWarnings("unchecked")
	public void saveVolumeSettings(String volume) {
		double d = Double.parseDouble(volume) * 100;
		int i = (int) d;
		settingsObject.put("volume", "" + i);
		writeToFile();
	}
	
	/**
	 * Remember if its a first time user or a returning user
	 */
	@SuppressWarnings("unchecked")
	public void rememberUser() {
		settingsObject.put("returnUser", " back ");
		writeToFile();
	}
	
	/**
	 * Retrieve the last saved shuffle settings
	 * @return the last saved shuffle settings
	 */
	public String loadShuffleSettings() {
		String settings = null;
		try {
			settings = getFromFile("shuffleIcon").toString();
			return settings;
		} catch(NullPointerException ex) {}
		return "shuffle";
	}

	/**
	 * Retrieve the last saved repeat settings
	 * @return the last saved repeat settings
	 */
	public String loadRepeatSettings() {
		String settings = null;
		try {
			settings = getFromFile("repeatIcon").toString();
			return settings;
		} catch(NullPointerException ex) {}
		System.out.println("Settings is " + settings);
		return "repeat";
	}

	/**
	 * Retrieve the last saved track index
	 * @return the last saved track index
	 */
	public int loadSelectedTrackIndex() {
		String settings = null;
		try {
			settings = getFromFile("currentIndex").toString();
			return Integer.parseInt(settings);
		} catch(NullPointerException ex) {}
		return 0;
	}
	
	/**
	 * Retrieve the last saved playlist index
	 * @return the last saved playlist index
	 */
	public int loadSelectedPlaylistIndex() {
		String settings = null;
		try {
			settings = getFromFile("currentPlaylistIndex").toString();
			return Integer.parseInt(settings);
		} catch(NullPointerException ex) {}
		return 0;
	}

	/**
	 * Retrieve the last saved volume
	 * @return the last saved volume
	 */
	public int loadVolume() {
		String settings = null;
		try {
			settings = getFromFile("volume").toString();
			return Integer.parseInt(settings);
		} catch(NullPointerException ex) {}
		return 100;
	}
	
	/**
	 * Retrieve whether or not the user is a returning user or a first time user
	 * @return
	 */
	public String loadIsBack() {
		String settings = null;
		try {
			settings = getFromFile("returnUser").toString();
			return settings;
		} catch(NullPointerException ex) {}
		return " ";
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
	private void writeToFile() {
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
	
	private Object getFromFile(String key) {
		try {
			if(!settingsFile.exists())
				settingsFile.createNewFile();
			
			br = new BufferedReader(new FileReader(settingsFile));
			settingsList = (JSONArray) parser.parse(br);
			settingsObject = (JSONObject) settingsList.get(0);
			br.close();
		} catch (Exception e) {}
		return settingsObject.get(key);
	}
	
	
}
