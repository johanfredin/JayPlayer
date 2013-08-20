package se.fredin.jayplayer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import se.fredin.jayplayer.domain.Track;
import se.fredin.jayplayer.service.PlaylistService;
import se.fredin.jayplayer.service.TrackService;
import se.fredin.jayplayer.utils.Formatter;
import se.fredin.jayplayer.utils.IconLoader;
import se.fredin.jayplayer.utils.MenuActions;
import se.fredin.jayplayer.utils.PlayerSettings;

/**
 * The GUI 
 * @author johan
 *
 */
public class Display extends JFrame implements Runnable {
	
	private TrackService trackService;
	private PlayerSettings playerSettings;
	private PlaylistService playlistService;
	private IconLoader iconLoader;
	private Formatter formatter;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<String> playListDlm, tracksListDlm;
	private JList<String> tracksDisplay, playListDisplay;
	private JSlider volumeSlider;
	private JProgressBar progressBar;
	private JTextField statusField;
	private JButton previousButton, playPauseButton, nextButton;
	private JButton shuffleButton, repeatButton;
	private JLabel timeLabel, totalDuration;
		
	/**
	 * Create the GUI
	 * @param trackService the {@link TrackService} this GUI will be working on.
	 */
	public Display(final TrackService trackService) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Display.class.getResource("/images/desktop_small.png")));
		this.trackService = trackService;
		this.playerSettings = new PlayerSettings();
		this.iconLoader = new IconLoader();
		this.playlistService = new PlaylistService();
		this.formatter = new Formatter();
		
		setTitle("JayPlayer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		
		playlistService.getPlaylists();
				
		initMenuBar();
				
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		JPanel buttonsAndVolumePanel = new JPanel();
		bottomPanel.add(buttonsAndVolumePanel);
		buttonsAndVolumePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		Dimension buttonDimension = new Dimension(50, 30);
		
		previousButton = new JButton(iconLoader.getIcon(iconLoader.BACKWARDS));
		previousButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playNextOrPreviousTrack("previous");
			}
		});
		previousButton.setPreferredSize(buttonDimension);
		buttonsAndVolumePanel.add(previousButton);
				
		playPauseButton = new JButton(iconLoader.getIcon(iconLoader.PLAY));
		playPauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playOrStopTrack();
			}
		});
		playPauseButton.setPreferredSize(buttonDimension);
		buttonsAndVolumePanel.add(playPauseButton);
		
		nextButton = new JButton(iconLoader.getIcon(iconLoader.FORWARD));
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playNextOrPreviousTrack("next");
			}
		});
		nextButton.setPreferredSize(buttonDimension);
		buttonsAndVolumePanel.add(nextButton);
		
		volumeSlider = new JSlider(0, 100, (int) playerSettings.loadVolume());
		volumeSlider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVolume();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				playerSettings.saveVolumeSettings("" + volumeSlider.getValue() * 0.01f);
			}
		});
		volumeSlider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				setVolume();
			}
		});
		volumeSlider.setPreferredSize(new Dimension(100, 30));
		buttonsAndVolumePanel.add(volumeSlider);
		
		JPanel trackProgressPanel = new JPanel();
		bottomPanel.add(trackProgressPanel);
		trackProgressPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		timeLabel = new JLabel("0:00");
		timeLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		trackProgressPanel.add(timeLabel);
		
		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(300, 30));
		trackProgressPanel.add(progressBar);
		
		totalDuration = new JLabel("3:25");
		trackProgressPanel.add(totalDuration);
		
		JPanel shuffleRepeatPanel = new JPanel();
		bottomPanel.add(shuffleRepeatPanel);
		shuffleRepeatPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		shuffleButton = new JButton(iconLoader.getIcon(playerSettings.loadShuffleSettings()));
		if(shuffleButton.getIcon() == iconLoader.getIcon(iconLoader.SHUFFLE_ENABLED))
			trackService.shuffle(true);
		
		shuffleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setShuffle();
			}
		});
		shuffleButton.setPreferredSize(buttonDimension);
		shuffleRepeatPanel.add(shuffleButton);
		
		repeatButton = new JButton(iconLoader.getIcon(playerSettings.loadRepeatSettings()));
		if(repeatButton.getIcon() == iconLoader.getIcon(iconLoader.REPEAT_ENABLED))
			trackService.repeat(true);
		
		repeatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setRepeat();
			}
		});
		repeatButton.setPreferredSize(buttonDimension);
		shuffleRepeatPanel.add(repeatButton);
		
		JPanel playListPanel = new JPanel();
		playListPanel.setBackground(new Color(105, 105, 105));
		playListPanel.setBorder(new EmptyBorder(10, 20, 20, 10));
		contentPane.add(playListPanel, BorderLayout.WEST);
		
		playListDlm = playlistService.getPlayListNames();
		playListPanel.setLayout(new BoxLayout(playListPanel, BoxLayout.PAGE_AXIS));
		
		JLabel playListsLabel = new JLabel("Playlists");
		playListsLabel.setForeground(new Color(211, 211, 211));
		playListsLabel.setFont(new Font("Impact", Font.PLAIN, 14));
		playListsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		playListPanel.add(playListsLabel);
		playListDisplay = new JList<String>(playListDlm);
		playListDisplay.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(!playListDlm.isEmpty()) {
					resetTimer();
					trackService.stop();
					switch(e.getKeyCode()) {
					case KeyEvent.VK_DELETE : case KeyEvent.VK_BACK_SPACE:
						deletePlaylist();
						break;
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(!playListDlm.isEmpty()) {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_UP : case KeyEvent.VK_DOWN:
						trackService.setTrackList(playlistService.getPlaylist(playListDisplay.getSelectedValue()));
						playerSettings.saveSelectedPlaylistIndex("" + playListDisplay.getSelectedIndex());
						break;
					}
				}
			}
		});
		playListDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playListDlm.isEmpty()) {
					trackService.stop();
					resetTimer();
					playListDisplay.setSelectedIndex(playListDisplay.locationToIndex(e.getPoint()));
					trackService.setTrackList(playlistService.getPlaylist(playListDisplay.getSelectedValue()));
					switch(e.getButton()) {
					case MouseEvent.BUTTON1:
						playerSettings.saveSelectedPlaylistIndex("" + playListDisplay.getSelectedIndex());
						break;
					case MouseEvent.BUTTON2 : case MouseEvent.BUTTON3:
						showEditPopup(playListDisplay, e.getX(), e.getY(), "Playlist");
						break;
					}
				}
			}
		});
		playListDisplay.setSelectedIndex(playerSettings.loadSelectedPlaylistIndex());
		playListDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playListDisplay.setFont(new Font("Dialog", Font.PLAIN, 13));
		playListDisplay.setForeground(Color.RED);
		playListDisplay.setBackground(new Color(0, 0, 0));
		playListDisplay.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JScrollPane playListScroller = new JScrollPane(playListDisplay);
		playListScroller.setPreferredSize(new Dimension(150, 400));
		playListScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		playListPanel.add(playListScroller);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(105, 105, 105));
		mainPanel.setBorder(new EmptyBorder(10, 20, 20, 10));
		contentPane.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		JLabel tracksLabel = new JLabel("Tracks");
		tracksLabel.setForeground(new Color(211, 211, 211));
		tracksLabel.setFont(new Font("Impact", Font.PLAIN, 14));
		tracksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(tracksLabel);
		
		tracksListDlm = playlistService.getTrackNames(playListDisplay.getSelectedValue());
		tracksDisplay = new JList<String>(tracksListDlm);
		tracksDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tracksDisplay.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(!trackService.isEmpty()) {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_ENTER:
						playPauseButton.setIcon(iconLoader.getIcon(iconLoader.PAUSE));
						trackService.playTrack(trackService.getId(), Display.this);
						updateTimeLabels();
						statusField.setText(trackService.getStatus());
						break;
					case KeyEvent.VK_DELETE:
						deleteTrack();
						break;
					case KeyEvent.VK_UP:
						tracksDisplay.setSelectedIndex(trackService.previousId());
						playerSettings.saveSelectedTrackIndex("" + trackService.getId());
						break;
					case KeyEvent.VK_DOWN:
						tracksDisplay.setSelectedIndex(trackService.nextId()); 
						playerSettings.saveSelectedTrackIndex("" + trackService.getId());
						break;
					}
				}
			}
		});
		tracksDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!trackService.isEmpty()) {
					trackService.setId(tracksDisplay.getSelectedIndex());
					if(e.getClickCount() >= 2) {
						playPauseButton.setIcon(iconLoader.getIcon(iconLoader.PAUSE));
						trackService.playTrack(trackService.getId(), Display.this);
						updateTimeLabels();
						statusField.setText(trackService.getStatus());
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!trackService.isEmpty()) {
					switch(e.getButton()) {
					case MouseEvent.BUTTON2 : case MouseEvent.BUTTON3:
						showEditPopup(tracksDisplay, e.getX(), e.getY(), "track");
						tracksDisplay.setSelectedIndex(tracksDisplay.locationToIndex(e.getPoint()));
						trackService.setId(tracksDisplay.getSelectedIndex());
						break;
					}
					playerSettings.saveSelectedTrackIndex("" + trackService.getId());
				}
			}
		});
		tracksDisplay.setDropTarget(new DropTarget(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("unchecked")
			@Override
			public synchronized void drop(DropTargetDropEvent ev) {
				try {
					ev.acceptDrop(DnDConstants.ACTION_LINK);
					List<File> droppedFiles = (List<File>) ev.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					loadMusic(droppedFiles);
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		tracksDisplay.setFont(new Font("Dialog", Font.PLAIN, 13));
		tracksDisplay.setBackground(new Color(0, 0, 0));
		tracksDisplay.setForeground(new Color(0, 255, 0));
		tracksDisplay.setBorder(new EmptyBorder(5, 5, 5, 5));
		JScrollPane tracksScroller = new JScrollPane(tracksDisplay);
		tracksScroller.setPreferredSize(new Dimension(mainPanel.getWidth(), mainPanel.getHeight() - tracksLabel.getHeight()));
		mainPanel.add(tracksScroller);
		
		JPanel topPanel = new JPanel();
		contentPane.add(topPanel, BorderLayout.NORTH);
		
		statusField = new JTextField();
		statusField.setHorizontalAlignment(SwingConstants.CENTER);
		statusField.setFont(new Font("Dialog", Font.PLAIN, 13));
		statusField.setEditable(false);
		statusField.setText("Welcome" + playerSettings.loadIsBack() + System.getProperty("user.name").toUpperCase() + "!");
		topPanel.add(statusField);
		statusField.setColumns(69);
			
		playerSettings.rememberUser();
		trackService.setTrackList(playlistService.getPlaylist(playListDisplay.getSelectedValue()));
		tracksDisplay.setSelectedIndex(playerSettings.loadSelectedTrackIndex());
		setVisible(true);
			
	}
	
	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		JMenu editFileMenu = new JMenu("Edit");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem newPlayListItem = addMenuItemListener(MenuActions.NEW_PLAYLIST);
		JMenuItem	loadMusicItem = addMenuItemListener(MenuActions.LOAD_MUSIC); 
		JMenuItem quitItem = addMenuItemListener(MenuActions.QUIT);
		JMenuItem clearTracksItem = addMenuItemListener(MenuActions.CLEAR_TRACKS);
		JMenuItem clearPlayListsItem = addMenuItemListener(MenuActions.CLEAR_PLAYLISTS);
		JMenuItem equalizerItem = addMenuItemListener(MenuActions.EQUALIZER);
		fileMenu.add(newPlayListItem);
		fileMenu.add(loadMusicItem);
		fileMenu.add(quitItem);
		menuBar.add(fileMenu);
		
		editFileMenu.add(equalizerItem);
		editFileMenu.add(clearTracksItem);
		editFileMenu.add(clearPlayListsItem);
		menuBar.add(editFileMenu);
		
		JMenuItem aboutItem = addMenuItemListener(MenuActions.ABOUT);
		helpMenu.add(aboutItem);
		menuBar.add(helpMenu);
	}
	
	
	
	private void playOrStopTrack() {
		if(!tracksListDlm.isEmpty()) {
			if(playPauseButton.getIcon() == iconLoader.getIcon(iconLoader.PLAY)) {
				trackService.playTrack(tracksDisplay.getSelectedIndex(), Display.this);
				playPauseButton.setIcon(iconLoader.getIcon(iconLoader.PAUSE));
				updateTimeLabels();
			} else {
				trackService.stop();
				resetTimer();
				playPauseButton.setIcon(iconLoader.getIcon(iconLoader.PLAY));
			}
			statusField.setText(trackService.getStatus());
		}
	}
	
	private void resetTimer() {
		timeLabel.setText("0.00");
		totalDuration.setText("0.00");
		progressBar.setValue(0);
	}

	private void playNextOrPreviousTrack(String direction) {
		trackService.stop();
		try { Thread.sleep(100); } catch(Exception ex) {}
		if(!tracksListDlm.isEmpty()) {
			switch(direction) {
			case "next":
				trackService.nextTrack(Display.this);
				break;
			case "previous":
				trackService.previousTrack(Display.this);
				break;
			}
			playPauseButton.setIcon(iconLoader.getIcon(iconLoader.PAUSE));
			tracksDisplay.setSelectedIndex(trackService.getId());
			updateTimeLabels();
			statusField.setText(trackService.getStatus());
		}
	}
	
	private void deleteTrack() {
		tracksListDlm.removeElement(trackService.getTrack(trackService.getId()).getTitle());
		trackService.deleteTrack(trackService.getId());
		statusField.setText(trackService.getStatus());
		playlistService.updatePlaylist(playListDisplay.getSelectedValue(), trackService.getTracks());
		tracksDisplay.setSelectedIndex(trackService.getId());
		playerSettings.saveSelectedTrackIndex("" + playListDisplay.getSelectedIndex());
		if(trackService.isEmpty())
			resetTimer();
	}
	
	private void deletePlaylist() {
		playlistService.deletePlaylist(playListDisplay.getSelectedValue());
		trackService.clearTrackList();
		tracksListDlm.removeAllElements();
		playListDlm.removeElement(playListDisplay.getSelectedValue());
		statusField.setText(playlistService.getStatus());
		resetTimer();
	}
	
	private void setVolume() {
		trackService.setVolume(volumeSlider.getValue() * 0.01f);
		statusField.setText("Volume: " + volumeSlider.getValue());
	}
	

	private void setShuffle() {
		if(shuffleButton.getIcon() == iconLoader.getIcon(iconLoader.SHUFFLE)) {
			shuffleButton.setIcon(iconLoader.getIcon(iconLoader.SHUFFLE_ENABLED));
			trackService.shuffle(true);
			playerSettings.saveShuffleSettings(iconLoader.SHUFFLE_ENABLED);
		}
		else {
			shuffleButton.setIcon(iconLoader.getIcon(iconLoader.SHUFFLE));
			trackService.shuffle(false);
			playerSettings.saveShuffleSettings(iconLoader.SHUFFLE);
		}
		statusField.setText(trackService.getStatus());
	}
	

	private void setRepeat() {
		if(repeatButton.getIcon() == iconLoader.getIcon(iconLoader.REPEAT)) {
			trackService.repeat(true);
			repeatButton.setIcon(iconLoader.getIcon(iconLoader.REPEAT_ENABLED));
			playerSettings.saveRepeatSettings(iconLoader.REPEAT_ENABLED);
		} else {
			trackService.repeat(false);
			repeatButton.setIcon(iconLoader.getIcon(iconLoader.REPEAT));
			playerSettings.saveRepeatSettings(iconLoader.REPEAT);
		}
		statusField.setText(trackService.getStatus());
	}
		
	private void showEditPopup(Component component, int x, int y, final String typeToEdit) {
		JPopupMenu playlistPopupMenu = new JPopupMenu();
		JMenuItem addOrPlayItem = new JMenuItem();
		JMenuItem removeItem = new JMenuItem("Remove " + typeToEdit);
		
		if(typeToEdit.equals("Playlist"))
			addOrPlayItem.setText("Add tracks");
		else
			addOrPlayItem.setText("Play " + typeToEdit);
				
		addOrPlayItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(typeToEdit.equals("Playlist")) {
					loadMusicFromMenu();
				} else {
					trackService.playTrack(tracksDisplay.getSelectedIndex(), Display.this);
					playPauseButton.setIcon(iconLoader.getIcon(iconLoader.PAUSE));
					updateTimeLabels();
					statusField.setText(trackService.getStatus());
				}
			}
		}); 
				
		removeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(typeToEdit.equals("Playlist"))
					deletePlaylist();
				else 
					deleteTrack();
			}
		}); 
		playlistPopupMenu.add(addOrPlayItem);
		playlistPopupMenu.add(removeItem);
		playlistPopupMenu.show(component, x, y);
	}

	private JMenuItem addMenuItemListener(final MenuActions action) {
		JMenuItem item = new JMenuItem(action.getActionName());
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(action) {
				case LOAD_MUSIC:
					loadMusicFromMenu();
					break;
				case QUIT:
					trackService.stop();
					System.exit(0);
					break;
				case CLEAR_TRACKS:
					clearTracks();
					break;
				case ABOUT:
					new AboutWindow();
					break;
				case NEW_PLAYLIST:
					createNewPlaylist();
					break;
				case CLEAR_PLAYLISTS:
					clearPlaylists();
					break;
				case EQUALIZER:
					new Equalizer(trackService);
					break;
				default:
					break;
				}
			}
		});
		return item;
	}
	
	private void loadMusicFromMenu() {
		JFileChooser openFile = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Music", "mp3", "wav", "MP3", "WAV", "wave", "WAVE");
		openFile.setFileFilter(filter);
		openFile.setMultiSelectionEnabled(true);
		int returnType = openFile.showOpenDialog(this);
		File[] selectedFiles = openFile.getSelectedFiles();
		if(returnType == JFileChooser.APPROVE_OPTION) {
			List<File> files = new ArrayList<File>();
			for(int i = 0; i < selectedFiles.length; i++)
				files.add(selectedFiles[i]);
			loadMusic(files);
		}
	}
	
	private void loadMusic(final List<File> selectedFiles) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				byte numberOfFilesAdded = 0;
				for(File file : selectedFiles) {
					if(file.getAbsolutePath().toLowerCase().endsWith("wav") || file.getAbsolutePath().toLowerCase().endsWith("mp3")  || file.getAbsolutePath().toLowerCase().endsWith("wave")) {
						Track track = new Track(file.getAbsolutePath());
						track.setVolume(volumeSlider.getValue() * 0.01f);
						trackService.addTrack(track);
						tracksListDlm.addElement(track.getTitle());
						numberOfFilesAdded++;
					}
				}
				trackService.setId(0);
				tracksDisplay.setSelectedIndex(trackService.getId());
				if(!playListDlm.isEmpty()) {
					
					playlistService.updatePlaylist(playListDisplay.getSelectedValue(), trackService.getTracks());
					statusField.setText("Added " + numberOfFilesAdded + ((numberOfFilesAdded > 1) ? " tracks" : " track ") + "to playlist: " + playListDisplay.getSelectedValue());
				} else
					statusField.setText("Added " + numberOfFilesAdded + ((numberOfFilesAdded > 1) ? " tracks" : " track"));
			}
		}).start();
		
	}

	private void clearTracks() {
		tracksListDlm.removeAllElements();
		trackService.clearTrackList();
		statusField.setText(trackService.getStatus());
		playlistService.updatePlaylist(playListDisplay.getSelectedValue(), trackService.getTracks());
		resetTimer();
	}

	private void createNewPlaylist() {
		String playlistName = "";
		try { 
			playlistName = JOptionPane.showInputDialog("Name of playlist?");
		} catch(NullPointerException ex) { return; }
		
		if(playlistName != "" && playlistName != null) {
			if(playlistService.getSize() < 1 && !tracksListDlm.isEmpty()) {
				// If there are tracks in the display when you create your first playlist we want to add those to the track
				playlistService.createNewPlaylist(playlistName, trackService.getTracks());
			} else {
				if(playlistService.containsKey(playlistName)) {
					JOptionPane.showMessageDialog(tracksDisplay, "Playlist " + playlistName + " already exists!", "Already Exists", JOptionPane.ERROR_MESSAGE);
					return;
				}
				playlistService.createNewPlaylist(playlistName, new ArrayList<Track>());
			}
			playListDisplay.setSelectedIndex(playListDlm.getSize() - 1);
			trackService.setTrackList(playlistService.getPlaylist(playListDisplay.getSelectedValue()));
			statusField.setText(playlistService.getStatus());
		}
	}
	
	private void clearPlaylists() {
		playlistService.clearPlaylists();
		statusField.setText(playlistService.getStatus());
	}

	/**
	 * Set the {@link TrackService} to play the next track in the list once playback of current {@link Track} has ended.
	 */
	@Override
	public void run() {
		playNextOrPreviousTrack("next");
	}
	
	/*
	 * Will tick every second and update the current time until track is stopped or finished
	 */
	private void updateTimeLabels() {
		final Track track = trackService.getTrack(tracksDisplay.getSelectedIndex());
		totalDuration.setText(formatter.format(track.getTotalTime(), 2));
		progressBar.setMaximum((int) (track.getTotalTime() * 10000));
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(!trackService.isStopTimer()) {
						timeLabel.setText(formatter.format(track.getCurrentTime(), 2));
						progressBar.setValue((int) (track.getCurrentTime() * 10000));
						Thread.sleep(100);
					}
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}).start();
	}
	
}
