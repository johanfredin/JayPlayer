package se.fredin.jayplayer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PlayerSettings {

	private File settingsFile = new File(System.getProperty("user.home") + "/jayplayer-settings.JSON");
	private JSONArray settingsList = new JSONArray();
	private JSONObject settingsObject = new JSONObject();
	private JSONParser parser = new JSONParser();
	private PrintWriter pw;
	private BufferedReader br;

	
	private BufferedReader getReader() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(settingsFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return br;
	}
	
	private PrintWriter getWriter() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(settingsFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pw;
	}
	
	@SuppressWarnings("unchecked")
	public void saveShuffleSettings(ImageIcon shuffleIcon) {
		settingsObject.put("shuffleIcon", shuffleIcon);
		writeToFile();
	}

	@SuppressWarnings("unchecked")
	public void saveRepeatSettings(ImageIcon repeatIcon) {
		settingsObject.put("repeatIcon", repeatIcon);
		writeToFile();
	}

	@SuppressWarnings("unchecked")
	public void saveSelectedIndex(int index) {
		settingsObject.put("currentIndex", index);
		writeToFile();
	}
	
	@SuppressWarnings("unchecked")
	public void saveVolumeSettings(float volume) {
		settingsObject.put("volume", volume);
		writeToFile();
	}
	
	public void writeToFile() {
		pw = getWriter();
		try {
			settingsObject.writeJSONString(pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pw.close();
	}
	
	public void readFromFile() {
		br = getReader();
		try {
			settingsList = (JSONArray) parser.parse(br);
			settingsObject = (JSONObject) settingsList.get(0);
			br.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	public ImageIcon loadShuffleSettings() {
		readFromFile();
		return (ImageIcon) settingsObject.get("shuffleIcon");
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
