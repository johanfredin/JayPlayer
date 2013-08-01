package se.fredin.jayplayer.service;

import javax.swing.ImageIcon;

public interface SettingsService {
	
	void saveShuffleSettings(ImageIcon shuffleIcon);
	
	void saveRepeatSettings(ImageIcon repeatIcon);
	
	void saveSelectedIndex(int index);
	
	void saveVolumeSettings(float volume);
	
	void writeToFile();
	
	void readFromFile();
	
	ImageIcon loadShuffleSettings();
	
	ImageIcon loadSaveRepeatSettings();
	
	int loadSelectedIndex();
	
	int loadVolume();

}
